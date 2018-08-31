package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.block.*;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerInteract extends MagicFunctions implements Listener {

    public PlayerInteract() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        BlockFace face = event.getBlockFace();

        if (!usingMagicHoe(player))
            return;

        if (!checkHoeInteract(event, player, block))
            return;

        event.setCancelled(true);

        switch (block.getType()) {

            case TRIPWIRE_HOOK:
                new Triphook(block);
                break;

            case HAY_BLOCK:
                new Bed(block);
                break;

            case BLACK_BED:
            case BLUE_BED:
            case BROWN_BED:
            case CYAN_BED:
            case GRAY_BED:
            case GREEN_BED:
            case LIGHT_BLUE_BED:
            case LIGHT_GRAY_BED:
            case LIME_BED:
            case MAGENTA_BED:
            case ORANGE_BED:
            case PINK_BED:
            case PURPLE_BED:
            case RED_BED:
            case WHITE_BED:
            case YELLOW_BED:
                new Bed(block);
                break;

            case ACACIA_TRAPDOOR:
            case BIRCH_TRAPDOOR:
            case DARK_OAK_TRAPDOOR:
            case JUNGLE_TRAPDOOR:
            case OAK_TRAPDOOR:
            case SPRUCE_TRAPDOOR:
            case IRON_TRAPDOOR:
                new Trapdoor(block);
                break;

            case SIGN:
                new Sign(block);
                break;

            case WALL_SIGN:
                new SignWall(block);
                break;

            case REDSTONE_LAMP:
                new Lamp(block);
                break;

            case ACACIA_STAIRS:
            case BIRCH_STAIRS:
            case BRICK_STAIRS:
            case COBBLESTONE_STAIRS:
            case DARK_OAK_STAIRS:
            case JUNGLE_STAIRS:
            case NETHER_BRICK_STAIRS:
            case PURPUR_STAIRS:
            case QUARTZ_STAIRS:
            case RED_SANDSTONE_STAIRS:
            case SANDSTONE_STAIRS:
            case STONE_BRICK_STAIRS:
            case SPRUCE_STAIRS:
            case OAK_STAIRS:
                new Stairs(block);
                break;

            case BLACK_WOOL:
            case BLUE_WOOL:
            case BROWN_WOOL:
            case CYAN_WOOL:
            case GRAY_WOOL:
            case GREEN_WOOL:
            case LIGHT_BLUE_WOOL:
            case LIGHT_GRAY_WOOL:
            case LIME_WOOL:
            case MAGENTA_WOOL:
            case ORANGE_WOOL:
            case PINK_WOOL:
            case PURPLE_WOOL:
            case RED_WOOL:
            case WHITE_WOOL:
            case YELLOW_WOOL:
                new Wool(block);
                break;

            case BLACK_CARPET:
            case BLUE_CARPET:
            case BROWN_CARPET:
            case CYAN_CARPET:
            case GRAY_CARPET:
            case GREEN_CARPET:
            case LIGHT_BLUE_CARPET:
            case LIGHT_GRAY_CARPET:
            case LIME_CARPET:
            case MAGENTA_CARPET:
            case ORANGE_CARPET:
            case PINK_CARPET:
            case PURPLE_CARPET:
            case RED_CARPET:
            case WHITE_CARPET:
            case YELLOW_CARPET:
                new Carpet(block);
                break;

            case SNOW:
            case SNOW_BLOCK:
                new Snow(block); // TODO BROKEN
                break;

            case OAK_PLANKS:
                new Wood(block);
                break;

            case LADDER:
                new Ladder(block);
                break;

            case STONE_BUTTON:
            case ACACIA_BUTTON:
            case BIRCH_BUTTON:
            case DARK_OAK_BUTTON:
            case JUNGLE_BUTTON:
            case OAK_BUTTON:
            case SPRUCE_BUTTON:
                new Button(block);
                break;

            case ACACIA_PRESSURE_PLATE:
            case BIRCH_PRESSURE_PLATE:
            case DARK_OAK_PRESSURE_PLATE:
            case JUNGLE_PRESSURE_PLATE:
            case OAK_PRESSURE_PLATE:
            case SPRUCE_PRESSURE_PLATE:
            case STONE_PRESSURE_PLATE:
            case LIGHT_WEIGHTED_PRESSURE_PLATE:
            case HEAVY_WEIGHTED_PRESSURE_PLATE:
                new Plate(block);
                break;

            case RED_MUSHROOM_BLOCK:
            case BROWN_MUSHROOM_BLOCK:
                new Mushroom(block); // TODO BROKEN
                break;

            case FURNACE:
                new Furnace(block, player);
                break;

            case VINE:
                new Vines(block);
                break;

            case BLACK_BANNER:
            case BLUE_BANNER:
            case BROWN_BANNER:
            case CYAN_BANNER:
            case GRAY_BANNER:
            case GREEN_BANNER:
            case LIGHT_BLUE_BANNER:
            case LIGHT_GRAY_BANNER:
            case LIME_BANNER:
            case MAGENTA_BANNER:
            case ORANGE_BANNER:
            case PINK_BANNER:
            case PURPLE_BANNER:
            case RED_BANNER:
            case WHITE_BANNER:
            case YELLOW_BANNER:
                new Banner(block);
                break;

            case BLACK_WALL_BANNER:
            case BLUE_WALL_BANNER:
            case BROWN_WALL_BANNER:
            case CYAN_WALL_BANNER:
            case GRAY_WALL_BANNER:
            case GREEN_WALL_BANNER:
            case LIGHT_BLUE_WALL_BANNER:
            case LIGHT_GRAY_WALL_BANNER:
            case LIME_WALL_BANNER:
            case MAGENTA_WALL_BANNER:
            case ORANGE_WALL_BANNER:
            case PINK_WALL_BANNER:
            case PURPLE_WALL_BANNER:
            case RED_WALL_BANNER:
            case WHITE_WALL_BANNER:
            case YELLOW_WALL_BANNER:
                new BannerWall(block);
                break;

            case WALL_TORCH:
                new Torch(block);
                break;

            case RAIL:
            case POWERED_RAIL:
                new Rails(block);
                break;

            case SAND:
            case GRAVEL:
            case BLACK_CONCRETE_POWDER:
            case BLUE_CONCRETE_POWDER:
            case BROWN_CONCRETE_POWDER:
            case CYAN_CONCRETE_POWDER:
            case GRAY_CONCRETE_POWDER:
            case GREEN_CONCRETE_POWDER:
            case LIGHT_BLUE_CONCRETE_POWDER:
            case LIGHT_GRAY_CONCRETE_POWDER:
            case LIME_CONCRETE_POWDER:
            case MAGENTA_CONCRETE_POWDER:
            case ORANGE_CONCRETE_POWDER:
            case PINK_CONCRETE_POWDER:
            case PURPLE_CONCRETE_POWDER:
            case RED_CONCRETE_POWDER:
            case WHITE_CONCRETE_POWDER:
            case YELLOW_CONCRETE_POWDER:
                new Sand(block); // TODO BROKEN
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

            case LAPIS_BLOCK:
                new Lapis(block);
                break;

            case STRUCTURE_VOID:
                new StructureVoid(block);
                break;

            case SPAWNER:
                new Spawner(block, face);
                break;

            case OBSIDIAN:
                new Obsidian(block);
                break;

            case STRUCTURE_BLOCK:
                new StructureBlock(block);
                break;

            case MELON:
                new Melon(block);
                break;

            case CACTUS:
                new Cactus(block);
                break;

            case ICE:
                new Ice(block);
                break;

            case PACKED_ICE:
                new PackedIce(block); // TODO BROKEN
                break;

            case MAGMA_BLOCK:
                new Magma(block, player); // TODO BROKEN
                break;

            case WHITE_GLAZED_TERRACOTTA:
            case ORANGE_GLAZED_TERRACOTTA:
            case MAGENTA_GLAZED_TERRACOTTA:
            case LIGHT_BLUE_GLAZED_TERRACOTTA:
            case YELLOW_GLAZED_TERRACOTTA:
            case LIME_GLAZED_TERRACOTTA:
            case PINK_GLAZED_TERRACOTTA:
            case GRAY_GLAZED_TERRACOTTA:
            case LIGHT_GRAY_GLAZED_TERRACOTTA:
            case CYAN_GLAZED_TERRACOTTA:
            case PURPLE_GLAZED_TERRACOTTA:
            case BLUE_GLAZED_TERRACOTTA:
            case BROWN_GLAZED_TERRACOTTA:
            case GREEN_GLAZED_TERRACOTTA:
            case RED_GLAZED_TERRACOTTA:
            case BLACK_GLAZED_TERRACOTTA:
                new Terracotta(block);
                break;
        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PlayerSwapHandItemsEvent event) {

        Player player = event.getPlayer();

        if (usingMagicHoe(player)) {

            getActionMessage().message(player, "" + ChatColor.RED + ChatColor.BOLD
                    + "Cannot be used in off-hand slot!");
            event.setCancelled(true);

        }

    }

}
