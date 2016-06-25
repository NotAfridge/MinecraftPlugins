package com.ullarah.umagic;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static com.ullarah.umagic.MagicInit.getWorldGuard;
import static org.bukkit.Material.DIAMOND_HOE;

@SuppressWarnings("deprecation")
public class MagicEvents implements Listener {

    @EventHandler
    public void changeBlock(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack inMainHand = player.getInventory().getItemInMainHand();
        ItemStack inOffHand = player.getInventory().getItemInOffHand();

        if (checkMagicHoe(inMainHand) ? checkMagicHoe(inMainHand) : checkMagicHoe(inOffHand)) {

            Block block = event.getClickedBlock();

            if (block != null) if (getWorldGuard().canBuild(player, block)) {

                String placeWarning = "" + ChatColor.RED + ChatColor.BOLD + "WARNING: " +
                        ChatColor.YELLOW + "Glitch block created. Do not place blocks next to it.";

                double bX = block.getLocation().getX() + 0.5;
                double bY = block.getLocation().getY() + 1;
                double bZ = block.getLocation().getZ() + 0.5;

                switch (block.getType()) {

                    case LOG:
                        switch (block.getData()) {

                            case 0:
                            case 4:
                            case 8:
                                block.setData((byte) 12);
                                break;

                            case 1:
                            case 5:
                            case 9:
                                block.setData((byte) 13);
                                break;

                            case 2:
                            case 6:
                            case 10:
                                block.setData((byte) 14);
                                break;

                            case 3:
                            case 7:
                            case 11:
                                block.setData((byte) 15);
                                break;

                        }
                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 25);
                        break;

                    case LOG_2:
                        switch (block.getData()) {

                            case 0:
                            case 4:
                            case 8:
                                block.setData((byte) 12);
                                break;

                            case 1:
                            case 5:
                            case 9:
                                block.setData((byte) 13);
                                break;

                        }
                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 25);
                        break;

                    case DOUBLE_STEP:
                        switch (block.getData()) {

                            case 0:
                                block.setData((byte) 8);
                                break;

                            case 1:
                                block.setData((byte) 9);
                                break;

                        }
                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 25);
                        break;

                    case DOUBLE_STONE_SLAB2:
                        switch (block.getData()) {

                            case 0:
                                block.setData((byte) 8);
                                break;

                        }
                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 25);
                        break;

                    case PISTON_BASE:
                        switch (event.getAction()) {

                            case RIGHT_CLICK_BLOCK:
                                player.sendMessage(placeWarning);
                                switch (block.getData()) {

                                    case 0:
                                        block.setData((byte) 8);
                                        break;

                                    case 1:
                                        block.setData((byte) 9);
                                        break;

                                    case 2:
                                        block.setData((byte) 10);
                                        break;

                                    case 3:
                                        block.setData((byte) 11);
                                        break;

                                    case 4:
                                        block.setData((byte) 12);
                                        break;

                                    case 5:
                                        block.setData((byte) 13);
                                        break;

                                }
                                break;

                            case LEFT_CLICK_BLOCK:
                                player.sendMessage(placeWarning);
                                switch (block.getData()) {

                                    case 0:
                                        block.setData((byte) 0);
                                        break;

                                    case 1:
                                        block.setData((byte) 1);
                                        break;

                                    case 2:
                                        block.setData((byte) 2);
                                        break;

                                    case 3:
                                        block.setData((byte) 3);
                                        break;

                                    case 4:
                                        block.setData((byte) 4);
                                        break;

                                    case 5:
                                        block.setData((byte) 5);
                                        break;

                                }
                                break;

                        }
                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 25);
                        break;

                    case PISTON_STICKY_BASE:
                        switch (event.getAction()) {

                            case RIGHT_CLICK_BLOCK:
                                player.sendMessage(placeWarning);
                                switch (block.getData()) {

                                    case 0:
                                        block.setData((byte) 8);
                                        break;

                                    case 1:
                                        block.setData((byte) 9);
                                        break;

                                    case 2:
                                        block.setData((byte) 10);
                                        break;

                                    case 3:
                                        block.setData((byte) 11);
                                        break;

                                    case 4:
                                        block.setData((byte) 12);
                                        break;

                                    case 5:
                                        block.setData((byte) 13);
                                        break;

                                }
                                break;

                            case LEFT_CLICK_BLOCK:
                                player.sendMessage(placeWarning);
                                switch (block.getData()) {

                                    case 0:
                                        block.setData((byte) 0);
                                        break;

                                    case 1:
                                        block.setData((byte) 1);
                                        break;

                                    case 2:
                                        block.setData((byte) 2);
                                        break;

                                    case 3:
                                        block.setData((byte) 3);
                                        break;

                                    case 4:
                                        block.setData((byte) 4);
                                        break;

                                    case 5:
                                        block.setData((byte) 5);
                                        break;

                                }
                                break;

                        }
                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 25);
                        break;

                    case HUGE_MUSHROOM_1:
                        block.setData(block.getData() < 15 ? block.getData() == 10 ?
                                (byte) 14 : (byte) (block.getData() + 1) : (byte) 0);
                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 25);
                        break;

                    case HUGE_MUSHROOM_2:
                        block.setData(block.getData() < 15 ? block.getData() == 10 ?
                                (byte) 14 : (byte) (block.getData() + 1) : (byte) 0);
                        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 25);
                        break;

                }

            }

        }

    }

    private boolean checkMagicHoe(ItemStack item) {

        if (item.getType() == DIAMOND_HOE) {

            if (item.hasItemMeta()) {

                if (item.getItemMeta().hasDisplayName()) {

                    if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Magical Hoe")) {

                        return true;

                    }

                }

            }

        }

        return false;

    }

}
