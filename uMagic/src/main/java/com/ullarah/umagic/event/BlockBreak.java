package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Arrays;
import java.util.List;

public class BlockBreak extends MagicFunctions implements Listener {

    private static final List<Material> backingBlocks = Arrays.asList(
            Material.BLACK_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.BROWN_STAINED_GLASS, Material.CYAN_STAINED_GLASS,
            Material.GRAY_STAINED_GLASS, Material.GREEN_STAINED_GLASS, Material.LIGHT_BLUE_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS,
            Material.LIME_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.PINK_STAINED_GLASS,
            Material.PURPLE_STAINED_GLASS, Material.RED_STAINED_GLASS, Material.WHITE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS,
            Material.GLASS
    );

    public BlockBreak() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(BlockBreakEvent event) {

        if (usingMagicHoe(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();

        for (String meta : new String[]{metaSand, metaLamp, metaWool, metaEmBr, metaVine, metaFurn, metaReds,
                metaLadd, metaRail, metaSign, metaTrch, metaBanr, metaBeds, metaFire, metaSnow, metaCice,})
            if (block.hasMetadata(meta)) {

                if (block.hasMetadata(metaBeds)) block.setType(Material.HAY_BLOCK);

                block.removeMetadata(meta, getPlugin());
                removeMetadata(block.getLocation());

            }

        if (backingBlocks.contains(block.getType())) {
            for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {

                Block blockNext = block.getRelative(face);

                if (blockNext.hasMetadata(metaLadd)) {

                    blockNext.setType(Material.OAK_WOOD);
                    blockNext.removeMetadata(metaLadd, getPlugin());
                    removeMetadata(blockNext.getLocation());

                }
            }
        }

    }

}
