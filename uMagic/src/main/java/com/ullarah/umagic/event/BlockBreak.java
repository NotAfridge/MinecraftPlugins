package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak extends MagicFunctions implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(BlockBreakEvent event) {

        if (usingMagicHoe(event.getPlayer())) {
            event.setCancelled(true);
            return;
        }

        Block block = event.getBlock();

        for (String meta : new String[]{metaSand, metaLamp, metaWool, metaEmBr, metaVine, metaFurn,
                metaLadd, metaRail, metaSign, metaTrch, metaBanr, metaBeds, metaFire})
            if (block.hasMetadata(meta)) {

                if (block.hasMetadata(metaBeds)) block.setType(Material.HAY_BLOCK);

                block.removeMetadata(meta, getPlugin());
                removeMetadata(block.getLocation());

            }

        if (block.getType() == Material.GLASS || block.getType() == Material.STAINED_GLASS)
            for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {

                Block blockNext = block.getRelative(face);

                if (blockNext.hasMetadata(metaLadd)) {

                    blockNext.setType(Material.WOOD);
                    blockNext.removeMetadata(metaLadd, getPlugin());
                    removeMetadata(blockNext.getLocation());

                }

            }

    }

}
