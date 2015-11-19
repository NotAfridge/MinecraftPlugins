package com.ullarah.umagic;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static com.ullarah.umagic.MagicInit.getWorldGuard;
import static org.bukkit.Material.DIAMOND_HOE;

@SuppressWarnings({"deprecation"})
public class MagicEvents implements Listener {

    @EventHandler
    public void changeBlock(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        ItemStack inHand = player.getItemInHand();

        if (inHand.getType() == DIAMOND_HOE) if (inHand.hasItemMeta()) if (inHand.getItemMeta().hasDisplayName()) {
            if (inHand.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Magical Hoe")) {

                Block block = event.getClickedBlock();

                if (block != null) if (getWorldGuard().canBuild(player, block)) {

                    String placeWarning = "" + ChatColor.RED + ChatColor.BOLD + "WARNING: " +
                            ChatColor.YELLOW + "Glitch block created. Do not place blocks next to it.";

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
                                    player.sendMessage(placeWarning);
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
                                    player.sendMessage(placeWarning);
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
                                    player.sendMessage(placeWarning);
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
                                    player.sendMessage(placeWarning);
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
