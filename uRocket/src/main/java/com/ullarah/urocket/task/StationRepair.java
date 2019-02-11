package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.TitleSubtitle;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Furnace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;

public class StationRepair {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);
        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (RocketInit.rocketRepair.isEmpty())
                        return;

                    for (Map.Entry<UUID, Location> repairStation : RocketInit.rocketRepair.entrySet()) {

                        Player player = Bukkit.getPlayer(repairStation.getKey());
                        ItemStack playerBoots = player.getInventory().getBoots();

                        BlockState block = player.getWorld().getBlockAt(
                                player.getLocation().getBlockX(),
                                player.getLocation().getBlockY() - 2,
                                player.getLocation().getBlockZ()).getState();

                        if (block instanceof Furnace && ((Furnace) block).getBurnTime() > 0) {

                            if (playerBoots.hasItemMeta()) {

                                Integer bootRepair = rocketFunctions.getBootRepairRate(playerBoots);

                                short bootDurability = playerBoots.getDurability();
                                int bootMaterialDurability = rocketFunctions.getBootDurability(playerBoots);

                                int bootHealthOriginal = (bootMaterialDurability - bootDurability);
                                int bootHealthNew = ((bootMaterialDurability - bootDurability) + bootRepair);

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

                                if (bootHealthOriginal <= (bootMaterialDurability - 1)) {

                                    playerBoots.setDurability((short) (bootDurability - bootRepair));

                                    if (bootHealthNew > bootMaterialDurability) {

                                        String repairDone = "Rocket Boots have been fully repaired!";
                                        commonString.messageSend(RocketInit.getPlugin(), player, true, repairDone);
                                        titleSubtitle.title(player, 5, repairDone);

                                        player.getWorld().playSound(player.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 1.0f);

                                    } else {

                                        String bootDurabilityMessage = "" + ChatColor.YELLOW + bootHealthNew + " / " + bootMaterialDurability;
                                        String bootEstimationMessage = "Full Repair Estimate: " + ChatColor.YELLOW + bootRepairMinute;

                                        commonString.messageSend(RocketInit.getPlugin(), player, true, new String[]{
                                                "Rocket Boot Durability: " + bootDurabilityMessage,
                                                "Full Repair Estimate: " + bootEstimationMessage
                                        });

                                        titleSubtitle.both(player, 5, bootDurabilityMessage, bootEstimationMessage);

                                        float x = (float) player.getLocation().getX();
                                        float y = (float) player.getLocation().getY();
                                        float z = (float) player.getLocation().getZ();

                                        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_PORTAL_TRIGGER, 0.5f, 1.5f);

                                        player.getWorld().spawnParticle(Particle.PORTAL, x, y, z, 500, 0, 0, 0, 1);

                                    }

                                } else {

                                    commonString.messageSend(RocketInit.getPlugin(), player, true, "Rocket Boots are already at full durability!");
                                    player.getWorld().playSound(player.getEyeLocation(), Sound.ENTITY_PLAYER_LEVELUP, 0.8f, 1.0f);

                                }

                            } else {

                                commonString.messageSend(RocketInit.getPlugin(), player, true, ChatColor.RED + "Rocket Boots failed to repair!");
                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.5f, 0.5f);
                                RocketInit.rocketRepair.remove(player.getUniqueId());

                            }

                        } else {

                            commonString.messageSend(RocketInit.getPlugin(), player, true, ChatColor.RED + "Repair Tank ran out of fuel!");
                            player.getWorld().playSound(player.getEyeLocation(), Sound.BLOCK_ANVIL_BREAK, 0.6f, 1.0f);
                            RocketInit.rocketRepair.remove(player.getUniqueId());

                        }

                    }

                }), 0, 600);

    }

}
