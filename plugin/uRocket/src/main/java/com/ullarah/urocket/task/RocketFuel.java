package com.ullarah.urocket.task;

import com.ullarah.ulib.function.Experience;
import com.ullarah.ulib.function.TitleSubtitle;
import org.apache.commons.io.output.StringBuilderWriter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

import static com.ullarah.urocket.RocketFunctions.*;
import static com.ullarah.urocket.RocketInit.*;

public class RocketFuel {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getServer().getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketUsage.isEmpty()) for (UUID uuid : rocketUsage) {

                        Player player = Bukkit.getPlayer(uuid);

                        if (player.isFlying()) {

                            if (player.getLevel() <= 2) {

                                player.sendMessage(getMsgPrefix() + "You ran out of fuel for your Rocket Boots!");
                                disableRocketBoots(player, false, true, false, false, false, false);

                            } else if (rocketUsage.contains(uuid) && player.getLevel() <= 6) {

                                rocketLowFuel.add(uuid);

                                player.sendMessage(getMsgPrefix() + "You might want to consider landing!");
                                TitleSubtitle.both(player, 2,
                                        ChatColor.YELLOW + "Low XP", "You might want to consider landing!");

                            }

                            if (rocketVariant.containsKey(player.getUniqueId())) {

                                Variant bootVariant = rocketVariant.get(player.getUniqueId());
                                Material bootMaterial = player.getInventory().getBoots().getType();

                                double getHealthFromBoots = 0;
                                int getFoodLevelFromBoots = 0;
                                double getExperienceFromBoots = 0;

                                switch (bootMaterial) {

                                    case LEATHER_BOOTS:
                                        getHealthFromBoots = (player.getHealth() - 3.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 4);
                                        getExperienceFromBoots = 1200;
                                        break;

                                    case IRON_BOOTS:
                                        getHealthFromBoots = (player.getHealth() - 2.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 3);
                                        getExperienceFromBoots = 800;
                                        break;

                                    case GOLD_BOOTS:
                                        getHealthFromBoots = (player.getHealth() - 1.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 2);
                                        getExperienceFromBoots = 600;
                                        break;

                                    case DIAMOND_BOOTS:
                                        getHealthFromBoots = (player.getHealth() - 0.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 1);
                                        getExperienceFromBoots = 500;
                                        break;

                                }

                                Random random = new Random();

                                switch (bootVariant) {

                                    case HEALTH:
                                        if (player.getHealth() <= 1.0 || player.getFoodLevel() <= 2) {

                                            player.sendMessage(getMsgPrefix() + "You are too hungry to fly...");
                                            disableRocketBoots(player, false, true, false, false, false, false);

                                        } else {

                                            player.setHealth(getHealthFromBoots);
                                            player.setFoodLevel(getFoodLevelFromBoots);

                                        }
                                        break;

                                    case MONEY:
                                        double money = getVaultEconomy().getBalance(player);

                                        if (money <= 10.0) {

                                            player.sendMessage(getMsgPrefix() + "You are too poor to fly...");
                                            disableRocketBoots(player, false, true, false, false, false, false);

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

                                        player.sendMessage(agendaMessage.toString());
                                        Experience.removeExperience(player, getExperienceFromBoots);
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
                                        Experience.removeExperience(player, getExperienceFromBoots);
                                        break;

                                    case COAL:
                                        if (random.nextInt(20) == 10) {

                                            player.getWorld().playSound(player.getLocation(),
                                                    Sound.FIREWORK_BLAST, 0.6f, 0.65f);

                                            player.sendMessage(getMsgPrefix() + ChatColor.RED +
                                                    "Your boots have malfunctioned!");

                                            disableRocketBoots(player, true, true, true, true, false, false);

                                        } else itemFuel(player, Material.COAL, Material.COAL_BLOCK);

                                        break;

                                    case REDSTONE:
                                        if (random.nextInt(20) == 10) {

                                            player.getWorld().playSound(player.getLocation(),
                                                    Sound.FIREWORK_BLAST, 0.6f, 0.65f);

                                            player.sendMessage(getMsgPrefix() + ChatColor.RED +
                                                    "Your boots have malfunctioned!");

                                            disableRocketBoots(player, true, true, true, true, false, false);

                                        } else itemFuel(player, Material.REDSTONE, Material.REDSTONE_BLOCK);

                                        break;

                                    case RUNNER:
                                        //do nothing
                                        break;

                                    default:
                                        Experience.removeExperience(player, getExperienceFromBoots);
                                        break;

                                }

                            }

                            if (player.getLocation().getY() > 250) {

                                player.setFlying(false);
                                player.sendMessage(getMsgPrefix() + "Rocket Boots don't work so well up high!");

                    }

                        }

            }

                }), 0, 100);

    }

}
