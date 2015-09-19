package com.ullarah.urocket.task;

import com.ullarah.ulib.function.RomanNumeralToInteger;
import com.ullarah.ulib.function.TitleSubtitle;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;

public class StationRepair {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketRepair.isEmpty())
                        for (Map.Entry<UUID, Location> repairStation : rocketRepair.entrySet()) {

                            Player player = Bukkit.getPlayer(repairStation.getKey());

                            ItemStack playerBoots = player.getInventory().getBoots();

                            if (player.getWorld().getBlockAt(
                                    player.getLocation().getBlockX(),
                                    player.getLocation().getBlockY() - 2,
                                    player.getLocation().getBlockZ())
                                    .getType() == Material.BURNING_FURNACE) {

                                if (playerBoots.hasItemMeta()) {

                                    String bootLore = playerBoots.getItemMeta().getLore().get(0);
                                    Integer bootRepair;

                                    Integer powerLevel = RomanNumeralToInteger.decode(
                                            bootLore.replaceFirst(
                                                    ChatColor.YELLOW + "Rocket Level ", ""));

                                    switch (powerLevel) {

                                        case 1:
                                            bootRepair = 5;
                                            break;

                                        case 2:
                                            bootRepair = 4;
                                            break;

                                        case 3:
                                            bootRepair = 3;
                                            break;

                                        case 4:
                                            bootRepair = 2;
                                            break;

                                        case 5:
                                            bootRepair = new Random().nextInt(9) + 1;
                                            break;

                                        default:
                                            bootRepair = 0;
                                            break;

                                    }

                                    short bootDurability = player.getInventory().getBoots().getDurability();
                                    int bootHealthOriginal = (195 - bootDurability);
                                    int bootHealthNew = ((195 - bootDurability) + bootRepair);

                                    int bootRepairEstimate = Math.round((bootDurability / bootRepair) / 2);
                                    String bootRepairMinute;

                                    switch (bootRepairEstimate) {
                                        case 1:
                                            bootRepairMinute = bootRepairEstimate + " Minute";
                                            break;
                                        case 0:
                                            bootRepairMinute = "Less than a minute!";
                                            break;
                                        default:
                                            bootRepairMinute = bootRepairEstimate + " Minutes";
                                            break;
                                    }

                                    if (bootHealthOriginal <= 194) {

                                        playerBoots.setDurability((short) (bootDurability - bootRepair));

                                        if (bootHealthNew > 195) {

                                            player.sendMessage(getMsgPrefix() + "Rocket Boots have been fully repaired!");
                                            TitleSubtitle.title(player, 5, "Rocket Boots have been fully repaired!");

                                            player.getWorld().playSound(player.getEyeLocation(), Sound.LEVEL_UP, 0.8f, 1.0f);

                                        } else {

                                            String bootDurabilityMessage = "" + ChatColor.YELLOW
                                                    + bootHealthNew + " / 195";

                                            String bootEstimationMessage = "Full Repair Estimate: " +
                                                    ChatColor.YELLOW + bootRepairMinute;

                                            player.sendMessage(getMsgPrefix() + "Rocket Boot Durability: " +
                                                    bootDurabilityMessage);

                                            player.sendMessage(getMsgPrefix() + "Full Repair Estimate: " +
                                                    bootEstimationMessage);

                                            TitleSubtitle.both(player, 5,
                                                    bootDurabilityMessage, bootEstimationMessage);

                                            float x = (float) player.getLocation().getX();
                                            float y = (float) player.getLocation().getY();
                                            float z = (float) player.getLocation().getZ();

                                            player.getWorld().playSound(player.getLocation(), Sound.PORTAL_TRIGGER, 0.5f, 1.5f);

                                            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                                                    EnumParticle.PORTAL, true, x, y, z, 0, 0, 0, 1, 500, null);

                                            for (Player serverPlayer : player.getWorld().getPlayers())
                                                ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);

                                        }

                                    } else {

                                        player.sendMessage(getMsgPrefix() + "Rocket Boots are already at full durability!");
                                        player.getWorld().playSound(player.getEyeLocation(), Sound.LEVEL_UP, 0.8f, 1.0f);

                            }

                                } else {

                                    player.sendMessage(getMsgPrefix() + ChatColor.RED + "Rocket Boots failed to repair!");
                                    player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_BLAST, 0.5f, 0.5f);
                                    rocketRepair.remove(player.getUniqueId());

                                }

                            } else {

                                player.sendMessage(getMsgPrefix() + ChatColor.RED + "Repair Tank ran out of fuel!");
                                player.getWorld().playSound(player.getEyeLocation(), Sound.ANVIL_BREAK, 0.6f, 1.0f);
                                rocketRepair.remove(player.getUniqueId());

                            }

                        }

                }), 0, 600);

    }

}
