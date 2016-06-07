package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.function.TitleSubtitle;
import com.ullarah.urocket.init.RocketVariant;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.init.RocketEnhancement.Enhancement.UNLIMITED;
import static com.ullarah.urocket.init.RocketLanguage.*;

public class RocketLowFuel {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();
        TitleSubtitle titleSubtitle = new TitleSubtitle();

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!rocketUsage.isEmpty()) for (UUID uuid : rocketUsage) {

                        Player player = Bukkit.getPlayer(uuid);

                        if (player.isFlying()) {

                            if (rocketEnhancement.containsKey(uuid))
                                if (rocketEnhancement.get(uuid).equals(UNLIMITED)) return;

                            boolean fuelLow = false;
                            String fuelType = "";
                            RocketVariant.Variant bootVariant = rocketVariant.get(player.getUniqueId());

                            File fuelFile = new File(getPlugin().getDataFolder() + File.separator + "fuel", player.getUniqueId().toString() + ".yml");
                            FileConfiguration fuelConfig = YamlConfiguration.loadConfiguration(fuelFile);

                            Inventory fuelInventory = null;

                            if (bootVariant.getFuelSingle() != null && bootVariant.getFuelBlock() != null) {

                                if (fuelFile.exists()) {

                                    Material jacket = player.getInventory().getChestplate().getType();

                                    int jacketSize = rocketFunctions.getFuelJacketSize(jacket);
                                    String jacketType = rocketFunctions.getFuelJacketConfigString(jacket);

                                    if (fuelConfig.get(jacketType) != null) {

                                        ArrayList<ItemStack> itemStack = new ArrayList<>();
                                        itemStack.addAll(fuelConfig.getList(jacketType).stream().map(fuelCurrentItem
                                                -> (ItemStack) fuelCurrentItem).collect(Collectors.toList()));

                                        fuelInventory = Bukkit.createInventory(player, jacketSize,
                                                "" + ChatColor.DARK_RED + ChatColor.BOLD + "Rocket Boot Fuel Jacket");
                                        fuelInventory.setContents(itemStack.toArray(new ItemStack[itemStack.size()]));

                                    }

                                }

                            }

                            switch (bootVariant) {

                                case HEALTH:
                                    fuelType = "health";
                                    if (player.getHealth() <= 3.0 || player.getFoodLevel() <= 4) fuelLow = true;
                                    break;

                                case MONEY:
                                    fuelType = "money";
                                    if (getVaultEconomy().getBalance(player) <= 20.0) fuelLow = true;
                                    break;

                                case TREE:
                                    fuelType = "wood";
                                    assert fuelInventory != null;
                                    if (!fuelInventory.containsAtLeast(new ItemStack(Material.WOOD), 15))
                                        if (!fuelInventory.containsAtLeast(new ItemStack(Material.LOG), 3))
                                            fuelLow = true;
                                    break;

                                case FURY:
                                    fuelType = "redstone";
                                    assert fuelInventory != null;
                                    if (!fuelInventory.containsAtLeast(new ItemStack(Material.REDSTONE), 15))
                                        if (!fuelInventory.containsAtLeast(new ItemStack(Material.REDSTONE_BLOCK), 3))
                                            fuelLow = true;
                                    break;

                                case GLOW:
                                    fuelType = "glowstone";
                                    assert fuelInventory != null;
                                    if (!fuelInventory.containsAtLeast(new ItemStack(Material.GLOWSTONE_DUST), 15))
                                        if (!fuelInventory.containsAtLeast(new ItemStack(Material.GLOWSTONE), 3))
                                            fuelLow = true;
                                    break;

                                default:
                                    fuelType = "coal";
                                    assert fuelInventory != null;
                                    if (!fuelInventory.containsAtLeast(new ItemStack(Material.COAL), 15))
                                        if (!fuelInventory.containsAtLeast(new ItemStack(Material.COAL_BLOCK), 3))
                                            fuelLow = true;
                                    break;

                            }

                            if (fuelLow) {
                                titleSubtitle.subtitle(player, 1, RB_FUEL_LOW);
                                commonString.messageSend(getPlugin(), player, true, new String[]{
                                        FuelLow(fuelType), RB_FUEL_WARNING
                                });
                                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1.3f);
                            }

                        }

                    }

                }), 0, 100);

    }

}
