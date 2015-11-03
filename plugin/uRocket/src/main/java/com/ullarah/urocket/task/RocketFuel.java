package com.ullarah.urocket.task;

import com.ullarah.ulib.function.BlockStacks;
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

import static com.ullarah.urocket.RocketFunctions.Variant;
import static com.ullarah.urocket.RocketFunctions.disableRocketBoots;
import static com.ullarah.urocket.RocketInit.*;

public class RocketFuel {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getServer().getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketUsage.isEmpty()) for (UUID uuid : rocketUsage) {

                        Player player = Bukkit.getPlayer(uuid);

                        if (player.isFlying()) {

                            if (player.getLevel() < 0) player.setLevel(0);
                            if (player.getExp() < 0) player.setExp(0);
                            if (player.getTotalExperience() < 0 || player.getTotalExperience() == 2147483647)
                                player.setTotalExperience(0);

                            boolean alternateFuel = false;
                            boolean isFuelEfficient = false;
                            boolean isSolarPowered = false;

                            switch (rocketVariant.get(uuid)) {

                                case COAL:
                                    alternateFuel = true;
                                    break;

                                case HEALTH:
                                    alternateFuel = true;
                                    break;

                                case MONEY:
                                    alternateFuel = true;
                                    break;

                                case REDSTONE:
                                    alternateFuel = true;
                                    break;

                            }

                            if (rocketEfficient.containsKey(uuid)) isFuelEfficient = rocketEfficient.get(uuid);
                            if (rocketSolar.containsKey(uuid)) isSolarPowered = rocketSolar.get(uuid);

                            if (rocketUsage.contains(uuid) && player.getLevel() <= 1 && !alternateFuel) {

                                rocketHealer.replace(uuid, 0);

                                player.sendMessage(getMsgPrefix() + "You ran out of XP for your Rocket Boots!");
                                TitleSubtitle.subtitle(player, 2,
                                        ChatColor.YELLOW + "You ran out of XP for your Rocket Boots!");

                                disableRocketBoots(player, true, true, true, true, true);

                            } else if (rocketUsage.contains(uuid) && player.getLevel() <= 5 && !alternateFuel) {

                                rocketHealer.replace(uuid, 0);

                                player.sendMessage(getMsgPrefix() + "You might want to consider landing!");
                                TitleSubtitle.both(player, 2,
                                        ChatColor.YELLOW + "Low XP", "You might want to consider landing!");

                            }

                            if (rocketVariant.containsKey(player.getUniqueId())) {

                                Random random = new Random();

                                Variant bootVariant = rocketVariant.get(player.getUniqueId());
                                Material bootMaterial = player.getInventory().getBoots().getType();

                                double getHealthFromBoots = 0;
                                int getFoodLevelFromBoots = 0;
                                double getExperienceFromBoots = 0;

                                switch (bootMaterial) {

                                    case LEATHER_BOOTS:
                                        getHealthFromBoots = (player.getHealth() - 3.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 4);

                                        if (isFuelEfficient) getExperienceFromBoots = 80.85;
                                        else if (isSolarPowered) getExperienceFromBoots = 54.25;
                                        else getExperienceFromBoots = 128.125;
                                        break;

                                    case IRON_BOOTS:
                                        getHealthFromBoots = (player.getHealth() - 2.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 3);

                                        if (isFuelEfficient) getExperienceFromBoots = 60.65;
                                        else if (isSolarPowered) getExperienceFromBoots = 39.45;
                                        else getExperienceFromBoots = 96.95;
                                        break;

                                    case GOLD_BOOTS:
                                        getHealthFromBoots = (player.getHealth() - 1.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 2);

                                        if (isFuelEfficient) getExperienceFromBoots = 40.45;
                                        else if (isSolarPowered) getExperienceFromBoots = 27.25;
                                        else getExperienceFromBoots = 64.65;
                                        break;

                                    case DIAMOND_BOOTS:
                                        getHealthFromBoots = (player.getHealth() - 0.5);
                                        getFoodLevelFromBoots = (player.getFoodLevel() - 1);

                                        if (isFuelEfficient) getExperienceFromBoots = 20.25;
                                        else if (isSolarPowered) getExperienceFromBoots = 13.75;
                                        else getExperienceFromBoots = 32.35;
                                        break;

                                }

                                switch (bootVariant) {

                                    case HEALTH:
                                        if (player.getHealth() <= 1.0 || player.getFoodLevel() <= 2) {

                                            player.sendMessage(getMsgPrefix() + "You are too hungry to fly...");
                                            disableRocketBoots(player, false, true, false, true, true);

                                        } else {

                                            player.setHealth(getHealthFromBoots);
                                            player.setFoodLevel(getFoodLevelFromBoots);

                                        }
                                        break;

                                    case MONEY:
                                        double money = getVaultEconomy().getBalance(player);

                                        if (money <= 10.0) {

                                            player.sendMessage(getMsgPrefix() + "You are too poor to fly...");
                                            disableRocketBoots(player, false, true, false, true, true);

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

                                            disableRocketBoots(player, true, true, true, true, true);

                                        } else if (BlockStacks.split(getPlugin(), player, Material.COAL_BLOCK, Material.COAL, 2, 8))
                                            disableRocketBoots(player, false, true, false, true, true);

                                        break;

                                    case REDSTONE:
                                        if (random.nextInt(20) == 10) {

                                            player.getWorld().playSound(player.getLocation(),
                                                    Sound.FIREWORK_BLAST, 0.6f, 0.65f);

                                            player.sendMessage(getMsgPrefix() + ChatColor.RED +
                                                    "Your boots have malfunctioned!");

                                            disableRocketBoots(player, true, true, true, true, true);

                                        } else if (BlockStacks.split(getPlugin(), player, Material.REDSTONE_BLOCK, Material.REDSTONE, 2, 8))
                                            disableRocketBoots(player, false, true, false, true, true);

                                        break;

                                    default:
                                        Experience.removeExperience(player, getExperienceFromBoots);
                                        break;

                                }

                            }

                            if (player.getLocation().getY() >= 250) {

                                disableRocketBoots(player, true, true, true, true, true);
                                player.sendMessage(getMsgPrefix() + "Rocket Boots don't work so well up high!");

                            } else if (player.getLocation().getY() <= 0) {

                                disableRocketBoots(player, true, true, true, true, true);
                                player.sendMessage(getMsgPrefix() + "Rocket Boots don't work so in the void!");

                            }

                        }

                    }

                }), 0, 100);

    }

}
