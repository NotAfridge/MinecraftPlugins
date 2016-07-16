package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.block.*;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

class PlayerInteract implements Listener {

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        MagicFunctions f = new MagicFunctions();
        Player player = event.getPlayer();

        ItemStack inMainHand = player.getInventory().getItemInMainHand(),
                inOffHand = player.getInventory().getItemInOffHand();

        if (f.checkMagicHoe(inMainHand) ? f.checkMagicHoe(inMainHand) : f.checkMagicHoe(inOffHand)) {

            if (!player.hasPermission("umagic.usage")) {
                event.setCancelled(true);
                return;
            }

            if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                event.setCancelled(true);
                return;
            }

            if (event.getAction() == Action.LEFT_CLICK_AIR
                    || event.getAction() == Action.RIGHT_CLICK_AIR) return;

            Block block = event.getClickedBlock();

            if (!f.checkBlock(player, block)) {
                event.setCancelled(true);
                return;
            }

            switch (block.getType()) {

                case RAILS:
                    new Rails().block(block);
                    break;

                case WOOD:
                    new Wood().block(block);
                    break;

                case LADDER:
                    new Ladder().block(block);
                    break;

                case SAND:
                case GRAVEL:
                    new Sand().block(block);
                    break;

                case EMERALD_BLOCK:
                    new Emerald().block(block, player);
                    break;

                case BEDROCK:
                    new Bedrock().block(block, player);
                    break;

                case BARRIER:
                    new Barrier().block(block);
                    break;

                case REDSTONE_LAMP_OFF:
                    new Redstone().block(block);
                    break;

                case ACACIA_STAIRS:
                case BIRCH_WOOD_STAIRS:
                case BRICK_STAIRS:
                case COBBLESTONE_STAIRS:
                case DARK_OAK_STAIRS:
                case JUNGLE_WOOD_STAIRS:
                case NETHER_BRICK_STAIRS:
                case PURPUR_STAIRS:
                case QUARTZ_STAIRS:
                case RED_SANDSTONE_STAIRS:
                case SANDSTONE_STAIRS:
                case SMOOTH_STAIRS:
                case SPRUCE_WOOD_STAIRS:
                case WOOD_STAIRS:
                    new Stairs().block(block);
                    break;

                case WOOL:
                    new Wool().block(block);
                    break;

                case CARPET:
                    new Carpet().block(block);
                    break;

                case LOG:
                case LOG_2:
                    new Log().block(block);
                    break;

                case DOUBLE_STEP:
                case DOUBLE_STONE_SLAB2:
                    new Slab().block(block);
                    break;

                case STONE_BUTTON:
                case WOOD_BUTTON:
                    new Button().block(block);
                    break;

                case HUGE_MUSHROOM_1:
                case HUGE_MUSHROOM_2:
                    new Mushroom().block(block);
                    break;

            }

            double bX = block.getLocation().getX() + 0.5,
                    bY = block.getLocation().getY() + 1,
                    bZ = block.getLocation().getZ() + 0.5;

            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 15);
            block.getWorld().playSound(block.getLocation(), Sound.UI_BUTTON_CLICK, 0.75f, 0.75f);
            event.setCancelled(true);

        }

    }

}
