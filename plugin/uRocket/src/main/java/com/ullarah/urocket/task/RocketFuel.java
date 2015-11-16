package com.ullarah.urocket.task;

import org.apache.commons.io.output.StringBuilderWriter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketEnhancement.Enhancement.FUEL;
import static com.ullarah.urocket.RocketEnhancement.Enhancement.SOLAR;
import static com.ullarah.urocket.RocketFunctions.*;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.*;
import static com.ullarah.urocket.RocketVariant.Variant;

public class RocketFuel {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!rocketUsage.isEmpty()) for (UUID uuid : rocketUsage) {

                        Player player = Bukkit.getPlayer(uuid);

                        if (player.isFlying()) {

                            boolean isFuelEfficient = false;
                            boolean isSolarPowered = false;

                            if (rocketEnhancement.containsKey(uuid)) {
                                isFuelEfficient = (rocketEnhancement.get(uuid).equals(FUEL));
                                isSolarPowered = (rocketEnhancement.get(uuid).equals(SOLAR));
                            }

                            if (rocketVariant.containsKey(player.getUniqueId())) {

                                Random random = new Random();

                                ItemStack rocketBoots = player.getInventory().getBoots();
                                Variant bootVariant = rocketVariant.get(player.getUniqueId());
                                Material bootMaterial = rocketBoots.getType();

                                double getHealthFromBoots = 0;
                                int getFoodLevelFromBoots = 0;
                                int itemFuelCost = 0;

                                switch (bootMaterial) {

                                    case LEATHER_BOOTS:
                                        itemFuelCost = 1 + getBootPowerLevel(rocketBoots);
                                        getHealthFromBoots = (player.getHealth() - 3.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 4);

                                        if (isFuelEfficient) itemFuelCost -= 1;
                                        if (isSolarPowered) itemFuelCost -= 1;
                                        break;

                                    case IRON_BOOTS:
                                        itemFuelCost = 2 + getBootPowerLevel(rocketBoots);
                                        getHealthFromBoots = (player.getHealth() - 2.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 3);

                                        if (isFuelEfficient) itemFuelCost -= 1;
                                        if (isSolarPowered) itemFuelCost -= 2;
                                        break;

                                    case GOLD_BOOTS:
                                        itemFuelCost = 3 + getBootPowerLevel(rocketBoots);
                                        getHealthFromBoots = (player.getHealth() - 1.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 2);

                                        if (isFuelEfficient) itemFuelCost -= 2;
                                        if (isSolarPowered) itemFuelCost -= 3;
                                        break;

                                    case DIAMOND_BOOTS:
                                        itemFuelCost = 4 + getBootPowerLevel(rocketBoots);
                                        getHealthFromBoots = (player.getHealth() - 0.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 1);

                                        if (isFuelEfficient) itemFuelCost -= 3;
                                        if (isSolarPowered) itemFuelCost -= 4;
                                        break;

                                }

                                switch (bootVariant) {

                                    case HEALTH:
                                        if (player.getHealth() <= 1.0 || player.getFoodLevel() <= 2) {

                                            messageSend(getPlugin(), player, true, RB_HUNGRY);
                                            disableRocketBoots(player, false, true, false, true, true, true);

                                        } else {

                                            player.setHealth(getHealthFromBoots);
                                            player.setFoodLevel(getFoodLevelFromBoots);

                                        }
                                        break;

                                    case MONEY:
                                        if (getVaultEconomy().getBalance(player) <= 10.0) {

                                            messageSend(getPlugin(), player, true, RB_MONEY);
                                            disableRocketBoots(player, false, true, false, true, true, true);

                                        } else {

                                            getVaultEconomy().withdrawPlayer(player, 5);

                                            for (int m = 0; m < 2; m++) {

                                                int r = new Random().nextInt(Bukkit.getOnlinePlayers().size());
                                                Player randomPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[r];

                                                getVaultEconomy().depositPlayer(randomPlayer, 1);

                                            }

                                        }
                                        break;

                                    case AGENDA:
                                        StringBuilderWriter agendaMessage = new StringBuilderWriter();

                                        String[] letterArray = {"G", "A", "Y", "A", "G", "E", "N", "D", "A"};
                                        ChatColor[] colourArray = {
                                                ChatColor.YELLOW, ChatColor.AQUA, ChatColor.BLUE,
                                                ChatColor.GOLD, ChatColor.GREEN, ChatColor.LIGHT_PURPLE,
                                                ChatColor.RED, ChatColor.WHITE
                                        };

                                        int i = 0;
                                        for (int x = 0; x < 2; x++) {
                                            while (i < 10) {
                                                for (String letterCurrent : letterArray) {
                                                    String m = "" + colourArray[new Random().nextInt(colourArray.length)]
                                                            + ChatColor.BOLD + ChatColor.MAGIC + letterCurrent;
                                                    agendaMessage.append(m);
                                                }
                                                i++;
                                            }
                                        }

                                        messageSend(getPlugin(), player, false, agendaMessage.toString());
                                        removeFuel(player, Material.COAL_BLOCK, Material.COAL, itemFuelCost);
                                        break;

                                    case DRUNK:
                                        int vectorSelect = random.nextInt(3);
                                        double v = (random.nextInt(41) - 30) / 10.0;
                                        switch (vectorSelect) {
                                            case 1:
                                                player.setVelocity(new Vector(v, 0, 0));
                                                break;

                                            case 2:
                                                player.setVelocity(new Vector(0, v, 0));
                                                break;

                                            case 3:
                                                player.setVelocity(new Vector(0, 0, v));
                                                break;

                                            default:
                                                break;
                                        }
                                        removeFuel(player, Material.COAL_BLOCK, Material.COAL, itemFuelCost);
                                        break;

                                    case TREE:
                                        if (random.nextInt(10) == 5) {

                                            player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_BLAST, 0.6f, 0.65f);
                                            messageSend(getPlugin(), player, true, RB_MALFUNCTION);
                                            disableRocketBoots(player, true, true, true, true, true, true);

                                        } else removeFuel(player, Material.LOG, Material.WOOD, itemFuelCost);
                                        break;

                                    case FURY:
                                        if (random.nextInt(10) == 5) {

                                            player.getWorld().playSound(player.getLocation(), Sound.FIREWORK_BLAST, 0.6f, 0.65f);
                                            messageSend(getPlugin(), player, true, RB_MALFUNCTION);
                                            disableRocketBoots(player, true, true, true, true, true, true);

                                        } else
                                            removeFuel(player, Material.REDSTONE_BLOCK, Material.REDSTONE, itemFuelCost);
                                        break;

                                    default:
                                        removeFuel(player, Material.COAL_BLOCK, Material.COAL, itemFuelCost);
                                        break;

                                }

                            }

                            if (player.getLocation().getY() >= 250) {

                                disableRocketBoots(player, true, true, true, true, true, true);
                                messageSend(getPlugin(), player, true, RB_HIGH);

                            } else if (player.getLocation().getY() <= 0) {

                                disableRocketBoots(player, true, true, true, true, true, true);
                                messageSend(getPlugin(), player, true, RB_LOW);

                            }

                        }

                    }

                }), 0, 100);

    }

}
