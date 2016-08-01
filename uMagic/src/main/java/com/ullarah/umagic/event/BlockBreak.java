package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak extends MagicFunctions implements Listener {

    @EventHandler
    public void event(BlockBreakEvent event) {

        Block block = event.getBlock();

        for (String meta : new String[]{metaSand, metaLamp, metaWool, metaEmBr, metaLadd, metaRail})
            if (block.hasMetadata(meta)) {

                block.removeMetadata(meta, MagicInit.getPlugin());
                removeMetadata(block.getLocation());

            }

        if (block.getType() == Material.GLASS || block.getType() == Material.STAINED_GLASS)
            for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {

                Block blockNext = block.getRelative(face);

                if (blockNext.hasMetadata(metaLadd)) {

                    blockNext.setType(Material.WOOD);
                    blockNext.removeMetadata(metaLadd, MagicInit.getPlugin());
                    removeMetadata(blockNext.getLocation());

                }

            }

    }

}
