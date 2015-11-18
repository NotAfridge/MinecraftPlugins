package com.ullarah.uteleport;

import com.ullarah.ulib.function.CommonString;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ullarah.uteleport.TeleportInit.*;

public class TeleportCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) {
            new CommonString().messageNoConsole(getPlugin(), sender);
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("uteleport.use")) {
            new CommonString().messagePermDeny(getPlugin(), player);
            return true;
        }

        ConcurrentHashMap<ArrayList<Location>, ArrayList<Date>> locationMap = historyMap.get(player.getUniqueId());

        if (!historyMap.containsKey(player.getUniqueId())) {

            new CommonString().messageSend(getPlugin(), player, true, new String[]{"No teleport history found."});

        } else {

            ArrayList<Location> locations = new ArrayList<>();
            ArrayList<Date> dates = new ArrayList<>();

            for (Map.Entry<ArrayList<Location>, ArrayList<Date>> entry : locationMap.entrySet()) {

                ArrayList<Location> historyLocation = entry.getKey();
                ArrayList<Date> historyDate = entry.getValue();

                locations = historyLocation;
                dates = historyDate;

            }

            if (args.length == 2) {

                if (args[0].equals("go")) {

                    String[] pos = args[1].split(",");

                    World world = Bukkit.getWorld(pos[0]);
                    Double posX = Double.parseDouble(pos[1]);
                    Double posY = Double.parseDouble(pos[2]);
                    Double posZ = Double.parseDouble(pos[3]);

                    Location location = new Location(world, posX, posY, posZ, 0, 0);

                    if (locations.contains(location)) {
                        if (historyBlock.contains(player.getUniqueId()))
                            new CommonString().messageSend(getPlugin(), player, true, new String[]{ChatColor.RED + "Slow down there..."});
                        else {
                            historyBlock.add(player.getUniqueId());
                            new BukkitRunnable() {
                                int b = getPlugin().getConfig().getInt("timeout");

                                @Override
                                public void run() {
                                    if (b <= 0) {
                                        historyBlock.remove(player.getUniqueId());
                                        this.cancel();
                                        return;
                                    }
                                    b--;
                                }
                            }.runTaskTimer(getPlugin(), 0, 20);
                            new CommonString().messageSend(getPlugin(), player, true, new String[]{ChatColor.GREEN + "Teleporting..."});
                            player.teleport(location);
                        }
                    } else
                        new CommonString().messageSend(getPlugin(), player, true, new String[]{ChatColor.RED + "Teleport entry not found."});

                }

            } else {

                new CommonString().messageSend(getPlugin(), player, true, new String[]{
                        "Teleport History - Click " +
                                ChatColor.LIGHT_PURPLE + "[" + ChatColor.DARK_PURPLE + "TP" + ChatColor.LIGHT_PURPLE + "]" +
                                ChatColor.RESET + " for teleportation."});

                for (Location location : locations) {

                    String dateTime = new SimpleDateFormat("dd-MM hh:mm").format(dates.get(locations.indexOf(location)));
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

                    int posX = (int) location.getX();
                    int posY = (int) location.getY();
                    int posZ = (int) location.getZ();

                    TextComponent spacer = new TextComponent(" ");

                    TextComponent teleport = new TextComponent(
                            ChatColor.LIGHT_PURPLE + "[" + ChatColor.DARK_PURPLE + "TP" + ChatColor.LIGHT_PURPLE + "]");

                    TextComponent position = new TextComponent(
                            ChatColor.YELLOW + " [" + ChatColor.GOLD + dateTime + ChatColor.YELLOW + "] " +
                                    ChatColor.WHITE + "World: " + ChatColor.AQUA + worldName + ChatColor.GOLD + " " +
                                    ChatColor.WHITE + "X: " + ChatColor.AQUA + (int) location.getX() + " " +
                                    ChatColor.WHITE + "Y: " + ChatColor.AQUA + (int) location.getY() + " " +
                                    ChatColor.WHITE + "Z: " + ChatColor.AQUA + (int) location.getZ()
                    );

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

        return true;

    }

}