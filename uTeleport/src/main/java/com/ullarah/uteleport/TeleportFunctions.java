package com.ullarah.uteleport;

import com.ullarah.uteleport.function.CommonString;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class TeleportFunctions {

    private final Plugin plugin;
    private final ConcurrentHashMap<UUID, ConcurrentHashMap<ArrayList<Location>, ArrayList<Date>>> historyMap;
    private final HashSet<UUID> historyBlock;

    TeleportFunctions(Plugin instance) {
        plugin = instance;
        historyMap = new ConcurrentHashMap<>();
        historyBlock = new HashSet<>();
    }

    private Plugin getPlugin() {
        return plugin;
    }

    ConcurrentHashMap<UUID, ConcurrentHashMap<ArrayList<Location>, ArrayList<Date>>> getHistoryMap() {
        return historyMap;
    }

    HashSet<UUID> getHistoryBlock() {
        return historyBlock;
    }

    private Location getLocation(String[] position) {

        World world = Bukkit.getWorld(position[0]);
        Double posX = Double.parseDouble(position[1]),
                posY = Double.parseDouble(position[2]),
                posZ = Double.parseDouble(position[3]);

        return new Location(world, posX, posY, posZ, 0, 0);

    }

    void addLocation(UUID uuid, Location location) {

        World posWorld = location.getWorld();
        double posX = (int) location.getX(), posY = (int) location.getY(), posZ = (int) location.getZ();

        ConcurrentHashMap<ArrayList<Location>, ArrayList<Date>> locationMap = new ConcurrentHashMap<>();

        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();

        locations.add(new Location(posWorld, posX, posY, posZ, 0, 0));
        dates.add(Calendar.getInstance().getTime());

        locationMap.put(locations, dates);

        getHistoryMap().put(uuid, locationMap);

    }

    void addLocation(UUID uuid, Location location, ConcurrentHashMap<ArrayList<Location>, ArrayList<Date>> map) {

        World posWorld = location.getWorld();
        double posX = (int) location.getX(), posY = (int) location.getY(), posZ = (int) location.getZ();

        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();

        for (Map.Entry<ArrayList<Location>, ArrayList<Date>> entry : map.entrySet()) {

            ArrayList<Location> historyLocation = entry.getKey();
            ArrayList<Date> historyDate = entry.getValue();

            if (historyLocation.size() >= getPlugin().getConfig().getInt("history")) {
                historyLocation.remove(0);
                historyDate.remove(0);
            }

            locations = historyLocation;
            dates = historyDate;

        }

        locations.add(new Location(posWorld, posX, posY, posZ, 0, 0));
        dates.add(Calendar.getInstance().getTime());

        map.put(locations, dates);

        getHistoryMap().put(uuid, map);

    }

    private Object[] getHistory(Player player) {

        ArrayList<Location> locations = new ArrayList<>();
        ArrayList<Date> dates = new ArrayList<>();

        ConcurrentHashMap<ArrayList<Location>, ArrayList<Date>> locationMap = getHistoryMap().get(player.getUniqueId());

        for (Map.Entry<ArrayList<Location>, ArrayList<Date>> entry : locationMap.entrySet()) {

            ArrayList<Location> historyLocation = entry.getKey();
            ArrayList<Date> historyDate = entry.getValue();

            locations = historyLocation;
            dates = historyDate;

        }

        return new Object[]{locations, dates};

    }

    void sendPlayer(String[] args, Player player) {

        CommonString commonString = new CommonString(getPlugin());

        Location location = getLocation(args[1].split(","));

        @SuppressWarnings("unchecked")
        ArrayList<Location> locations = (ArrayList<Location>) getHistory(player)[0];

        if (!locations.contains(location)) {
            commonString.messageSend(player, true, ChatColor.RED + "Teleport entry not found.");
            return;
        }

        if (getHistoryBlock().contains(player.getUniqueId())) {
            commonString.messageSend(player, true, ChatColor.RED + "Slow down there...");
            return;
        }

        getHistoryBlock().add(player.getUniqueId());

        new BukkitRunnable() {
            int b = getPlugin().getConfig().getInt("timeout");

            @Override
            public void run() {
                if (b <= 0) {
                    getHistoryBlock().remove(player.getUniqueId());
                    this.cancel();
                    return;
                }
                b--;
            }
        }.runTaskTimer(getPlugin(), 0, 20);

        commonString.messageSend(player, true, ChatColor.GREEN + "Teleporting...");
        player.teleport(location);

    }

    void displayHistory(Player player) {

        CommonString commonString = new CommonString(getPlugin());

        commonString.messageSend(player, true,
                "Teleport History - Click " +
                        ChatColor.LIGHT_PURPLE + "[" + ChatColor.DARK_PURPLE + "TP" + ChatColor.LIGHT_PURPLE + "]" +
                        ChatColor.RESET + " for teleportation.");

        @SuppressWarnings("unchecked")
        ArrayList<Location> locations = (ArrayList<Location>) getHistory(player)[0];

        @SuppressWarnings("unchecked")
        ArrayList<Location> dates = (ArrayList<Location>) getHistory(player)[1];

        for (Location location : locations) {

            String worldName;

            switch (location.getWorld().getName()) {

                case "world":
                    worldName = "Overworld";
                    break;

                case "world_nether":
                    worldName = "Nether";
                    break;

                case "world_the_end":
                    worldName = "End";
                    break;

                default:
                    worldName = "Unknown";
                    break;

            }

            TextComponent spacer = new TextComponent(" "), teleport = new TextComponent(
                    ChatColor.LIGHT_PURPLE + "[" + ChatColor.DARK_PURPLE + "TP" + ChatColor.LIGHT_PURPLE + "]");

            String dateTime = new SimpleDateFormat("dd-MM hh:mm").format(dates.get(locations.indexOf(location)));

            TextComponent position = new TextComponent(
                    ChatColor.YELLOW + " [" + ChatColor.GOLD + dateTime + ChatColor.YELLOW + "] " +
                            ChatColor.WHITE + "World: " + ChatColor.AQUA + worldName + ChatColor.GOLD + " " +
                            ChatColor.WHITE + "X: " + ChatColor.AQUA + (int) location.getX() + " " +
                            ChatColor.WHITE + "Y: " + ChatColor.AQUA + (int) location.getY() + " " +
                            ChatColor.WHITE + "Z: " + ChatColor.AQUA + (int) location.getZ()
            );

            int posX = (int) location.getX(), posY = (int) location.getY(), posZ = (int) location.getZ();

            String teleportCommand = "/utp go " + location.getWorld().getName() + "," + posX + "," + posY + "," + posZ;
            teleport.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, teleportCommand));

            teleport.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder(ChatColor.YELLOW + "Click to teleport to this location").create()));

            spacer.addExtra(teleport);
            spacer.addExtra(position);

            player.spigot().sendMessage(spacer);

        }

    }

}
