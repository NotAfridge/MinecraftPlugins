package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketFunctions;
import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.RocketPlayer;
import com.ullarah.urocket.function.CommonString;
import com.ullarah.urocket.init.RocketEnhancement;
import com.ullarah.urocket.init.RocketLanguage;
import com.ullarah.urocket.init.RocketVariant;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.UUID;

public class RocketFuel {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    for (Player player : RocketInit.getPlayers()) {
                        if (player != null && player.isFlying()) {
                            processFuelUse(player);
                        }
                    }

                }), 0, 100);

    }

    /**
     * Process fuel consumption for the given player
     * @param player Player who is flying
     */
    private void processFuelUse(Player player) {
        RocketFunctions rocketFunctions = new RocketFunctions();
        CommonString commonString = new CommonString();

        RocketPlayer rp = RocketInit.getPlayer(player);
        if (!rp.isUsingBoots()) {
            return;
        }

        RocketEnhancement.Enhancement enhancement = rp.getBootData().getEnhancement();
        RocketVariant.Variant variant = rp.getBootData().getVariant();

        boolean isFuelEfficient = (enhancement == RocketEnhancement.Enhancement.FUEL);
        boolean isSolarPowered = (enhancement == RocketEnhancement.Enhancement.SOLAR);
        boolean isUnlimited = (enhancement == RocketEnhancement.Enhancement.UNLIMITED);
        boolean isStable = (enhancement == RocketEnhancement.Enhancement.STABLE);

        Random random = new Random();

        ItemStack rocketBoots = player.getInventory().getBoots();
        Material bootMaterial = rocketBoots.getType();

        double getHealthFromBoots = 0;
        int getFoodLevelFromBoots = 0;
        int itemFuelCost = 0;
        int malfunctionRate = 0;

        switch (bootMaterial) {

            case LEATHER_BOOTS:
                malfunctionRate = 500;
                itemFuelCost = 1 + rocketFunctions.getBootPowerLevel(rocketBoots);
                getHealthFromBoots = (player.getHealth() - 3.5);
                getFoodLevelFromBoots = (player.getFoodLevel() - 4);

                if (isFuelEfficient) itemFuelCost -= 1;
                if (isSolarPowered) itemFuelCost -= 1;
                break;

            case IRON_BOOTS:
                malfunctionRate = 1000;
                itemFuelCost = 2 + rocketFunctions.getBootPowerLevel(rocketBoots);
                getHealthFromBoots = (player.getHealth() - 2.5);
                getFoodLevelFromBoots = (player.getFoodLevel() - 3);

                if (isFuelEfficient) itemFuelCost -= 1;
                if (isSolarPowered) itemFuelCost -= 2;
                break;

            case GOLDEN_BOOTS:
                malfunctionRate = 1500;
                itemFuelCost = 3 + rocketFunctions.getBootPowerLevel(rocketBoots);
                getHealthFromBoots = (player.getHealth() - 1.5);
                getFoodLevelFromBoots = (player.getFoodLevel() - 2);

                if (isFuelEfficient) itemFuelCost -= 2;
                if (isSolarPowered) itemFuelCost -= 3;
                break;

            case DIAMOND_BOOTS:
                malfunctionRate = 2000;
                itemFuelCost = 4 + rocketFunctions.getBootPowerLevel(rocketBoots);
                getHealthFromBoots = (player.getHealth() - 0.5);
                getFoodLevelFromBoots = (player.getFoodLevel() - 1);

                if (isFuelEfficient) itemFuelCost -= 3;
                if (isSolarPowered) itemFuelCost -= 4;
                break;

        }

        switch (variant) {

            case HEALTH:
                if (player.getHealth() <= 1.0 || player.getFoodLevel() <= 2) {

                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_HUNGRY);
                    rocketFunctions.disableRocketBoots(player, true);

                } else {

                    player.setHealth(getHealthFromBoots);
                    player.setFoodLevel(getFoodLevelFromBoots);

                }
                break;

            case MONEY:
                if (RocketInit.getVaultEconomy().getBalance(player) <= 10.0) {

                    commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_MONEY);
                    rocketFunctions.disableRocketBoots(player, true);

                } else {

                    RocketInit.getVaultEconomy().withdrawPlayer(player, 5);

                    for (int m = 0; m < 2; m++) {

                        int r = new Random().nextInt(Bukkit.getOnlinePlayers().size());
                        Player randomPlayer = (Player) Bukkit.getOnlinePlayers().toArray()[r];

                        RocketInit.getVaultEconomy().depositPlayer(randomPlayer, 1);

                    }

                }
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
                break;

        }

        if (!isUnlimited) if (!isStable) {
            if (random.nextInt(malfunctionRate) == 1) {

                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_BLAST, 0.6f, 0.65f);
                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_MALFUNCTION);
                rocketFunctions.disableRocketBoots(player, true);

            }
        }

        Material fuelBlock = variant.getFuelBlock();
        Material fuelSingle = variant.getFuelSingle();

        if (!isUnlimited && fuelBlock != null && fuelSingle != null)
            rocketFunctions.fuelRemove(player, fuelBlock, fuelSingle, itemFuelCost);

        if (player.getLocation().getY() >= 250) {

            rocketFunctions.disableRocketBoots(player, true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_HIGH);

        } else if (player.getLocation().getY() <= 0) {

            rocketFunctions.disableRocketBoots(player, true);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_LOW);

        }
    }

}
