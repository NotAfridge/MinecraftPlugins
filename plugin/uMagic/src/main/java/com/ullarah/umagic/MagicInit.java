package com.ullarah.umagic;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;
import java.util.logging.Level;

import static com.ullarah.umagic.MagicInit.getWorldGuard;
import static org.bukkit.Material.*;

public class MagicInit extends JavaPlugin {

    private static WorldGuardPlugin worldGuard;

    public static WorldGuardPlugin getWorldGuard() {
        return worldGuard;
    }

    private static void setWorldGuard(WorldGuardPlugin worldGuard) {
        MagicInit.worldGuard = worldGuard;
    }

    @Override
    public void onEnable() {

        PluginManager pluginManager = getServer().getPluginManager();
        Plugin pluginWorldGuard = pluginManager.getPlugin("WorldGuard");

        if (pluginWorldGuard != null) {
            setWorldGuard((WorldGuardPlugin) pluginWorldGuard);
            getServer().addRecipe(Recipes.hoeRecipe());
            getServer().addRecipe(Recipes.clockRecipe());
            getServer().getPluginManager().registerEvents(new MagicEvents(), this);
        } else {
            Bukkit.getLogger().log(Level.SEVERE, "WorldGuard not found, disabling uMagic!");
            pluginManager.disablePlugin(this);
        }

    }

    @Override
    public void onDisable() {
    }

}

class Recipes {

    public static ItemStack hoe() {

        ItemStack hoeStack = new ItemStack(DIAMOND_HOE, 1);
        ItemMeta hoeMeta = hoeStack.getItemMeta();

        hoeMeta.setDisplayName(ChatColor.AQUA + "Magical Hoe");

        hoeMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        hoeStack.setItemMeta(hoeMeta);

        hoeStack.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return hoeStack;

    }

    public static ItemStack clock() {

        ItemStack clockStack = new ItemStack(Material.WATCH, 1);
        ItemMeta clockMeta = clockStack.getItemMeta();

        clockMeta.setDisplayName(ChatColor.AQUA + "Magical Clock");

        clockMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        clockStack.setItemMeta(clockMeta);

        clockStack.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return clockStack;

    }

    public static ShapedRecipe hoeRecipe() {

        ShapedRecipe hoeRecipe = new ShapedRecipe(hoe());
        hoeRecipe.shape(" M ", "MHM", " M ");

        hoeRecipe.setIngredient('H', DIAMOND_HOE);
        hoeRecipe.setIngredient('M', SPECKLED_MELON);

        return hoeRecipe;

    }

    public static ShapedRecipe clockRecipe() {

        ShapedRecipe clockRecipe = new ShapedRecipe(clock());
        clockRecipe.shape("ESE", "SCS", "ESE");

        clockRecipe.setIngredient('C', WATCH);
        clockRecipe.setIngredient('E', EYE_OF_ENDER);
        clockRecipe.setIngredient('S', NETHER_STAR);

        return clockRecipe;

    }

}

@SuppressWarnings({"deprecation"})
class MagicEvents implements Listener {

    @EventHandler
    public void changeBlock(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();
        Block block = event.getClickedBlock();
        ItemStack inHand = player.getItemInHand();

        if (inHand.getType() == WATCH) {
            if (inHand.hasItemMeta()) {
                if (inHand.getItemMeta().hasDisplayName()) {
                    if (inHand.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Magical Clock")) {

                        switch (event.getAction()) {

                            case LEFT_CLICK_AIR:
                            case LEFT_CLICK_BLOCK:
                                player.resetPlayerTime();
                                player.sendMessage(ChatColor.GOLD + "[uClock] " + ChatColor.RESET
                                        + "Your time is now in sync with everyone else!");
                                player.playSound(player.getEyeLocation(), Sound.ENDERMAN_TELEPORT, 0.6f, 0.75f);
                                break;

                            case RIGHT_CLICK_AIR:
                            case RIGHT_CLICK_BLOCK:
                                switch (new Random().nextInt(151)) {

                                    case 0:
                                        Bukkit.broadcastMessage(ChatColor.GOLD + "[uClock] " + ChatColor.RESET
                                                + ChatColor.YELLOW + player.getDisplayName()
                                                + ChatColor.RESET + " shifted their time incorrectly...");

                                        for (Player online : Bukkit.getOnlinePlayers())
                                            online.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 200, 1), true);

                                        world.setStorm(true);
                                        world.setThundering(true);
                                        world.setWeatherDuration(6000);
                                        world.setThunderDuration(6000);

                                        player.playSound(player.getLocation(), Sound.AMBIENCE_THUNDER, 0.5f, 0.75f);
                                        break;

                                    case 25: // RANDOM TELEPORT
                                        break;

                                    case 50:
                                        world.createExplosion(
                                                player.getLocation().getBlockX(),
                                                player.getLocation().getBlockY(),
                                                player.getLocation().getBlockZ(),
                                                1.25f, true, false
                                        );
                                        break;

                                    case 100: // PLAYER VANISH
                                        player.spigot().setCollidesWithEntities(false);

                                        break;

                                    case 150:
                                        player.kickPlayer("java.space.continuum: An error occurred in the fabric of time on the remote host");
                                        break;

                                    default:
                                        switch (new Random().nextInt(100)) {

                                            case 0:
                                                player.sendMessage(ChatColor.GOLD + "[uClock] " + ChatColor.RESET
                                                        + "Nothing happened...");
                                                break;

                                            default:
                                                player.setPlayerTime(player.getPlayerTime() + 2000, false);
                                                player.sendMessage(ChatColor.GOLD + "[uClock] " + ChatColor.RESET
                                                        + "Your current time has been changed, be careful!");
                                                player.playSound(player.getEyeLocation(), Sound.PORTAL_TRAVEL, 0.6f, 0.75f);
                                                break;

                                        }
                                        break;

                                }
                                break;

                        }

                    }
                }
            }
        }

        if (inHand.getType() == DIAMOND_HOE) {
            if (inHand.hasItemMeta()) {
                if (inHand.getItemMeta().hasDisplayName()) {
                    if (inHand.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Magical Hoe")) {
                        if (block != null) {
                            if (getWorldGuard().canBuild(player, block)) {

                                switch (block.getType()) {

                                    case LOG:
                                        switch (block.getData()) {

                                            case 0:
                                            case 4:
                                            case 8:
                                                block.setTypeIdAndData(17, (byte) 12, true);
                                                break;

                                            case 1:
                                            case 5:
                                            case 9:
                                                block.setTypeIdAndData(17, (byte) 13, true);
                                                break;

                                            case 2:
                                            case 6:
                                            case 10:
                                                block.setTypeIdAndData(17, (byte) 14, true);
                                                break;

                                            case 3:
                                            case 7:
                                            case 11:
                                                block.setTypeIdAndData(17, (byte) 15, true);
                                                break;

                                        }
                                        break;

                                    case LOG_2:
                                        switch (block.getData()) {

                                            case 0:
                                                block.setTypeIdAndData(162, (byte) 13, true);
                                                break;

                                            case 1:
                                                block.setTypeIdAndData(162, (byte) 12, true);
                                                break;

                                        }
                                        break;

                                    case DOUBLE_STEP:
                                        switch (block.getData()) {

                                            case 0:
                                                block.setTypeIdAndData(43, (byte) 8, true);
                                                break;

                                            case 1:
                                                block.setTypeIdAndData(43, (byte) 9, true);
                                                break;

                                        }
                                        break;

                                    case DOUBLE_STONE_SLAB2:
                                        switch (block.getData()) {

                                            case 0:
                                                block.setTypeIdAndData(181, (byte) 8, true);
                                                break;

                                        }
                                        break;

                                    case PISTON_BASE:
                                        switch (event.getAction()) {

                                            case RIGHT_CLICK_BLOCK:
                                                switch (block.getData()) {

                                                    case 0:
                                                        block.setTypeIdAndData(33, (byte) 8, true);
                                                        break;

                                                    case 1:
                                                        block.setTypeIdAndData(33, (byte) 9, true);
                                                        break;

                                                    case 2:
                                                        block.setTypeIdAndData(33, (byte) 10, true);
                                                        break;

                                                    case 3:
                                                        block.setTypeIdAndData(33, (byte) 11, true);
                                                        break;

                                                    case 4:
                                                        block.setTypeIdAndData(33, (byte) 12, true);
                                                        break;

                                                    case 5:
                                                        block.setTypeIdAndData(33, (byte) 13, true);
                                                        break;

                                                }
                                                break;

                                            case LEFT_CLICK_BLOCK:
                                                switch (block.getData()) {

                                                    case 0:
                                                        block.setTypeIdAndData(34, (byte) 0, true);
                                                        break;

                                                    case 1:
                                                        block.setTypeIdAndData(34, (byte) 1, true);
                                                        break;

                                                    case 2:
                                                        block.setTypeIdAndData(34, (byte) 2, true);
                                                        break;

                                                    case 3:
                                                        block.setTypeIdAndData(34, (byte) 3, true);
                                                        break;

                                                    case 4:
                                                        block.setTypeIdAndData(34, (byte) 4, true);
                                                        break;

                                                    case 5:
                                                        block.setTypeIdAndData(34, (byte) 5, true);
                                                        break;

                                                }
                                                break;

                                        }
                                        break;

                                    case PISTON_STICKY_BASE:

                                        switch (event.getAction()) {

                                            case RIGHT_CLICK_BLOCK:
                                                switch (block.getData()) {

                                                    case 0:
                                                        block.setTypeIdAndData(29, (byte) 8, true);
                                                        break;

                                                    case 1:
                                                        block.setTypeIdAndData(29, (byte) 9, true);
                                                        break;

                                                    case 2:
                                                        block.setTypeIdAndData(29, (byte) 10, true);
                                                        break;

                                                    case 3:
                                                        block.setTypeIdAndData(29, (byte) 11, true);
                                                        break;

                                                    case 4:
                                                        block.setTypeIdAndData(29, (byte) 12, true);
                                                        break;

                                                    case 5:
                                                        block.setTypeIdAndData(29, (byte) 13, true);
                                                        break;

                                                }
                                                break;

                                            case LEFT_CLICK_BLOCK:
                                                switch (block.getData()) {

                                                    case 0:
                                                        block.setTypeIdAndData(34, (byte) 8, true);
                                                        break;

                                                    case 1:
                                                        block.setTypeIdAndData(34, (byte) 9, true);
                                                        break;

                                                    case 2:
                                                        block.setTypeIdAndData(34, (byte) 10, true);
                                                        break;

                                                    case 3:
                                                        block.setTypeIdAndData(34, (byte) 11, true);
                                                        break;

                                                    case 4:
                                                        block.setTypeIdAndData(34, (byte) 12, true);
                                                        break;

                                                    case 5:
                                                        block.setTypeIdAndData(34, (byte) 13, true);
                                                        break;

                                                }
                                                break;

                                        }

                                        break;

                                    case PUMPKIN:
                                        // No faceless in 1.8?
                                        // block.setTypeIdAndData(86, (byte) 4, true);
                                        break;

                                    case JACK_O_LANTERN:
                                        // No faceless in 1.8?
                                        // block.setTypeIdAndData(91, (byte) 4, true);
                                        break;

                                    case HUGE_MUSHROOM_1:
                                        block.setTypeIdAndData(99, (byte) 0, true);
                                        break;

                                    case HUGE_MUSHROOM_2:
                                        block.setTypeIdAndData(99, (byte) 15, true);
                                        break;

                                }

                            }
                        }
                    }
                }
            }
        }

    }

}
