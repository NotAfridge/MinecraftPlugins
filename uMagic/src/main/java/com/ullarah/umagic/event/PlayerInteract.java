package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.block.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerInteract extends MagicFunctions implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        if (usingMagicHoe(player)) {

            if (checkHoeInteract(event, player, block)) {

                switch (block.getType()) {

                    case TRIPWIRE_HOOK:
                        new Triphook(block);
                        break;

                    case HAY_BLOCK:
                        new Bed(block);
                        break;

                    case BED_BLOCK:
                        new Bed(block);
                        break;

                    case TRAP_DOOR:
                    case IRON_TRAPDOOR:
                        new Trapdoor(block);
                        break;

                    case SIGN:
                    case SIGN_POST:
                    case WALL_SIGN:
                        new Sign(block);
                        break;

                    case REDSTONE_LAMP_OFF:
                        new Redstone(block);
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
                        new Stairs(block);
                        break;

                    case WOOL:
                        new Wool(block);
                        break;

                    case CARPET:
                        new Carpet(block);
                        break;

                    case WOOD:
                        new Wood(block);
                        break;

                    case LADDER:
                        new Ladder(block);
                        break;

                    case LOG:
                    case LOG_2:
                        new Log(block);
                        break;

                    case DOUBLE_STEP:
                    case DOUBLE_STONE_SLAB2:
                        new Slab(block);
                        break;

                    case STONE_BUTTON:
                    case WOOD_BUTTON:
                        new Button(block);
                        break;

                    case STONE_PLATE:
                    case WOOD_PLATE:
                    case IRON_PLATE:
                    case GOLD_PLATE:
                        new Plate(block);
                        break;

                    case HUGE_MUSHROOM_1:
                    case HUGE_MUSHROOM_2:
                        new Mushroom(block);
                        break;

                    case FURNACE:
                        new Furnace(block, player);
                        break;

                    case BURNING_FURNACE:
                        new FurnaceBurn(block);
                        break;

                    case VINE:
                        new Vines(block);
                        break;

                    case STANDING_BANNER:
                    case WALL_BANNER:
                    case BANNER:
                        new Banner(block);
                        break;

                    case TORCH:
                    case REDSTONE_TORCH_OFF:
                    case REDSTONE_TORCH_ON:
                        new Torch(block);
                        break;

                    case RAILS:
                        new Rails(block);
                        break;

                    case SAND:
                    case GRAVEL:
                        new Sand(block);
                        break;

                    case EMERALD_BLOCK:
                        new Emerald(block, player);
                        break;

                    case BEDROCK:
                        new Bedrock(block, player);
                        break;

                    case BARRIER:
                        new Barrier(block);
                        break;

                    case NETHERRACK:
                        new Netherrack(block);
                        break;

                    default:
                        event.setCancelled(true);
                        break;

                }

            }

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PlayerSwapHandItemsEvent event) {

        Player player = event.getPlayer();

        if (usingMagicHoe(player)) {

            getCommonString().messageSend(player, "Cannot be used in off-hand slot.");
            event.setCancelled(true);

        }

    }

}
