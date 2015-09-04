package com.ullarah.uauction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static com.ullarah.uauction.Init.*;

class Tasks {

    private static Integer randomTry = 3;

    public static void collectionReminder() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), new Runnable() {
            public void run() {
                log.info("Reminding players for auction collection...");
                File auctionBoxFolder = new File(getPlugin().getDataFolder() + File.separator + "auction");
                File[] listOfFiles = auctionBoxFolder.listFiles();

                if (listOfFiles != null) for (File listOfFile : listOfFiles)
                    if (listOfFile.isFile()) {
                        String auctionBoxName = listOfFile.getName().toLowerCase();

                        File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName);

                        if (auctionBoxFile.exists()) {

                            FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                            String auctionName = (String) auctionBox.get("name");
                            Boolean auctionRunning = (Boolean) auctionBox.get("running");
                            Boolean auctionEnded = (Boolean) auctionBox.get("ended");
                            UUID auctionWinnerUUID = UUID.fromString((String) auctionBox.get("winnerid"));
                            Boolean auctionCollected = (Boolean) auctionBox.get("collected");

                            if (!auctionRunning && !auctionCollected && auctionEnded
                                    && Bukkit.getOfflinePlayer(auctionWinnerUUID).isOnline())
                                Bukkit.getPlayer(auctionWinnerUUID).sendMessage(getMsgPrefix() +
                                        "Type " + ChatColor.YELLOW + "/acollect " + auctionName +
                                        ChatColor.RESET + " to collect your auction winnings!");

                        }

                    }
            }
        }, 600 * 20, 600 * 20);

    }

    public static void runHousekeeping() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), new Runnable() {
            public void run() {
                log.info("Updating active and cleaning old auctions...");
                File auctionBoxFolder = new File(getPlugin().getDataFolder() + File.separator + "auction");
                File[] listOfFiles = auctionBoxFolder.listFiles();

                if (listOfFiles != null) for (File listOfFile : listOfFiles)
                    if (listOfFile.isFile()) {
                        String auctionBoxName = listOfFile.getName().toLowerCase();
                        if (!activeAuctions.contains(auctionBoxName.replace(".yml", ""))) {

                            File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName);

                            if (auctionBoxFile.exists()) {

                                FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                                String auctionName = (String) auctionBox.get("name");
                                String auctionWinner = (String) auctionBox.get("winner");
                                UUID auctionWinnerUUID = UUID.fromString((String) auctionBox.get("winnerid"));
                                Boolean auctionRunning = (Boolean) auctionBox.get("running");
                                Boolean auctionCollected = (Boolean) auctionBox.get("collected");
                                Double auctionBid = (Double) auctionBox.get("bid");

                                if (auctionRunning) activeAuctions.add(auctionBoxName.replace(".yml", ""));
                                if (auctionBid == 0.0 && auctionWinner.matches("nobody")) {
                                    activeAuctions.remove(auctionBoxName.replace(".yml", ""));
                                    Boolean delSuccess = listOfFile.delete();
                                    if (delSuccess) log.info("Deleted " + auctionBoxName);
                                }
                                if (auctionCollected) {
                                    activeAuctions.remove(auctionBoxName.replace(".yml", ""));
                                    Boolean delSuccess = listOfFile.delete();
                                    if (delSuccess) log.info("Deleted " + auctionBoxName);
                                } else winnerAlerts.put(auctionName, auctionWinnerUUID);

                            }

                        }

                    }
            }
        }, 0, 3600 * 20);

    }

    public static void runAuctionTimers() {

        log.info("Starting auction timers...");

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), new Runnable() {
            public void run() {
                log.info("Updating auction timers...");
                File auctionBoxFolder = new File(getPlugin().getDataFolder() + File.separator + "auction");
                File[] listOfFiles = auctionBoxFolder.listFiles();

                if (listOfFiles != null) for (File listOfFile : listOfFiles)
                    if (listOfFile.isFile()) {
                        String auctionBoxName = listOfFile.getName().toLowerCase();

                        File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName);

                        if (auctionBoxFile.exists()) {

                            FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                            UUID auctionCreatorUUID = UUID.fromString((String) auctionBox.get("creatorid"));
                            Double auctionWinningBid = (Double) auctionBox.get("bid");
                            Boolean auctionRunning = (Boolean) auctionBox.get("running");
                            Boolean auctionEnded = (Boolean) auctionBox.get("ended");
                            Integer auctionTimer = (Integer) auctionBox.get("time");

                            if (auctionRunning && auctionTimer != 0) {
                                auctionBox.set("time", auctionTimer - 1);
                                try {
                                    auctionBox.save(auctionBoxFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (auctionTimer == 0 && !auctionEnded) {

                                String auctionName = (String) auctionBox.get("name");
                                String auctionWinner = (String) auctionBox.get("winner");
                                UUID auctionWinnerUUID = UUID.fromString((String) auctionBox.get("winnerid"));

                                if (!auctionWinnerUUID.equals(UUID.fromString("00000000-0000-0000-0000-000000000000"))) {
                                    economy.depositPlayer(Bukkit.getOfflinePlayer(auctionCreatorUUID), auctionWinningBid);
                                    Bukkit.broadcastMessage(getMsgPrefix() +
                                            ChatColor.AQUA + auctionWinner + ChatColor.RESET +
                                            " has won the " + ChatColor.GOLD + auctionName + ChatColor.RESET +
                                            " auction! Congratulations!");
                                    if (Bukkit.getOfflinePlayer(auctionWinnerUUID).isOnline())
                                        Bukkit.getPlayer(auctionWinnerUUID).sendMessage(getMsgPrefix() +
                                                "You have won! Type " + ChatColor.YELLOW + "/acollect " + auctionName +
                                                ChatColor.RESET + " to collect your winnings!");
                                }

                                winnerAlerts.put(auctionName, auctionWinnerUUID);

                                auctionBox.set("ended", true);
                                auctionBox.set("running", false);
                                try {
                                    auctionBox.save(auctionBoxFile);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                    }
            }
        }, 3600 * 20, 3600 * 20);

    }

    public static void runChatMessages() {

        log.info("Starting auction chat timers...");

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), new Runnable() {
            public void run() {
                log.info("Reminding players of running auctions...");

                if (!Init.chatMembers.isEmpty()) {

                    String winningAuction = getRandomAuction();

                    if (winningAuction != null)
                        for (Player player : Init.chatMembers) player.sendMessage(winningAuction);

                    randomTry = 3;

                }

            }
        }, 300 * 20, 300 * 20);

    }

    private static String getRandomAuction() {

        File auctionBoxFolder = new File(getPlugin().getDataFolder() + File.separator + "auction");
        File[] listOfFiles = auctionBoxFolder.listFiles();

        if (listOfFiles != null && randomTry != 0) {
            File randomFile = listOfFiles[new Random().nextInt(listOfFiles.length)];
            if (randomFile.isFile()) {
                String auctionBoxName = randomFile.getName().toLowerCase();

                File auctionBoxFile = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName);

                if (auctionBoxFile.exists()) {

                    FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionBoxFile);

                    String auctionName = (String) auctionBox.get("name");
                    String auctionCurrentWinner = (String) auctionBox.get("winner");
                    Boolean auctionEnded = (Boolean) auctionBox.get("ended");
                    Double auctionCurrentBid = (Double) auctionBox.get("bid");

                    if (!auctionEnded && auctionCurrentBid != 0.0)
                        return getMsgChatPrefix() + "Auction: " + ChatColor.YELLOW + auctionName + ChatColor.RESET + " ‣‣ Current Winner: " + ChatColor.AQUA +
                                auctionCurrentWinner + ChatColor.RESET + " ‣‣ Top Bid: " + ChatColor.GREEN + "$" + auctionCurrentBid;
                    else {
                        randomTry--;
                        getRandomAuction();
                    }

                }

            }

        }

        return null;

    }

}