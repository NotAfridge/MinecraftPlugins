package com.ullarah.uauction;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

import static com.ullarah.uauction.Init.*;
import static com.ullarah.uauction.command.Collection.hasWon;
import static org.bukkit.ChatColor.stripColor;

class Events implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void auctionBoxClick(final InventoryClickEvent event) {

        Inventory chestInventory = event.getInventory();
        Integer getRawSlot = event.getRawSlot();
        InventoryAction getAction = event.getAction();

        // Running Auction
        if (chestInventory.getName().matches("§1Auction: §3(.*)")) {

            String auctionBoxName = stripColor(event.getInventory().getTitle().replace("Auction: ", ""));
            assert auctionBoxName != null;

            File auctionDataPath = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName.toLowerCase() + ".yml");
            FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionDataPath);

            Boolean auctionRunning = (Boolean) auctionBox.get("running");

            if (auctionRunning) event.setCancelled(true);

        }

        // Collect Auction
        if (chestInventory.getName().matches("§1Auction: §2(.*)")) {

            String auctionBoxName = stripColor(event.getInventory().getTitle().replace("Auction: ", ""));
            assert auctionBoxName != null;

            File auctionDataPath = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName.toLowerCase() + ".yml");
            FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionDataPath);

            Boolean auctionRunning = (Boolean) auctionBox.get("running");

            if (!auctionRunning && getRawSlot <= 53 && (
                    getAction == InventoryAction.PLACE_ALL ||
                            getAction == InventoryAction.PLACE_SOME ||
                            getAction == InventoryAction.PLACE_ONE ||
                            getAction == InventoryAction.SWAP_WITH_CURSOR ||
                            getAction == InventoryAction.MOVE_TO_OTHER_INVENTORY)) event.setCancelled(true);

        }

        // Give Auction
        if (chestInventory.getName().matches("§1Auction: §6(.*)")) {

            String auctionBoxName = stripColor(event.getInventory().getTitle().replace("Auction: ", ""));
            assert auctionBoxName != null;

            File auctionDataPath = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName.toLowerCase() + ".yml");
            FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionDataPath);

            if (getRawSlot <= 53 && (
                    getAction == InventoryAction.PLACE_ALL ||
                            getAction == InventoryAction.PLACE_SOME ||
                            getAction == InventoryAction.PLACE_ONE ||
                            getAction == InventoryAction.SWAP_WITH_CURSOR ||
                            getAction == InventoryAction.MOVE_TO_OTHER_INVENTORY)) event.setCancelled(true);

        }

    }

    @SuppressWarnings("unused")
    @EventHandler
    public void auctionBoxDrag(final InventoryDragEvent event) {

        Inventory boxInventory = event.getInventory();

        if (boxInventory.getName().matches("§1Auction: §[236](.*)")) {

            event.setCancelled(true);

        }

    }

    @SuppressWarnings("unused")
    @EventHandler
    public void auctionBoxClose(final InventoryCloseEvent event) {

        Player boxPlayer = (Player) event.getPlayer();
        Inventory boxInventory = event.getInventory();

        if (boxInventory.getName().matches("§1Auction: §[23](.*)")) {

            String auctionBoxName = stripColor(event.getInventory().getTitle().replace("Auction: ", "").toLowerCase());
            assert auctionBoxName != null;

            File auctionDataPath = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".yml");
            FileConfiguration auctionBox = YamlConfiguration.loadConfiguration(auctionDataPath);

            Boolean auctionRunning = (Boolean) auctionBox.get("running");

            if (!auctionRunning) try {
                auctionBox.set("item", boxInventory.getContents());
                auctionBox.save(auctionDataPath);
            } catch (IOException e) {
                e.printStackTrace();
                boxPlayer.sendMessage(getMsgPrefix() + ChatColor.RED + "Auction Update Error!");
            }

            Boolean boxContent = false;

            for (ItemStack item : boxInventory.getContents())
                if (item != null) {
                    boxContent = true;
                    break;
                }

            if (!boxContent) {
                winnerAlerts.remove(auctionBoxName);
                Boolean delSuccess = auctionDataPath.delete();
                if (delSuccess) log.info("Deleted " + auctionBoxName + ": All items collected");
            }

        }

        if (boxInventory.getName().matches("§1Auction: §[6](.*)")) {

            String auctionBoxName = stripColor(event.getInventory().getTitle().replace("Auction: ", "").toLowerCase());
            File auctionBoxFileCopy = new File(getPlugin().getDataFolder() + File.separator + "auction", auctionBoxName + ".temp.yml");

            Boolean delSuccess = auctionBoxFileCopy.delete();

            if (!delSuccess)
                boxPlayer.sendMessage(getMsgPrefix() + ChatColor.RED + "Cannot delete temporary auction. Notify Staff.");

        }

    }

    @SuppressWarnings("unused")
    @EventHandler
    public void auctionWinnerAlert(final PlayerJoinEvent event) {

        hasWon(event.getPlayer());

    }

    @SuppressWarnings("unused")
    @EventHandler
    public void playerLeave(final PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (chatMembers.contains(player)) chatMembers.remove(player);

    }

}