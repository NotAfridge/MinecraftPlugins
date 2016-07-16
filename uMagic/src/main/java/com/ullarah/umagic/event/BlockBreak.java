package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

class BlockBreak implements Listener {

    @EventHandler
    public void event(BlockBreakEvent e) {

        MagicFunctions f = new MagicFunctions();
        Block b = e.getBlock();

        for (String meta : new String[]{f.metaSand, f.metaLamp, f.metaWool, f.metaEmBr, f.metaLadd, f.metaRail})
            if (b.hasMetadata(meta)) {
                b.removeMetadata(meta, MagicInit.getPlugin());
                f.removeMetadata(b.getLocation());
            }

        if (b.getType() == Material.GLASS || b.getType() == Material.STAINED_GLASS)
            for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {
                Block blockNext = b.getRelative(face);
                if (blockNext.hasMetadata(f.metaLadd)) {
                    blockNext.setType(Material.WOOD);
                    blockNext.removeMetadata(f.metaLadd, MagicInit.getPlugin());
                    f.removeMetadata(blockNext.getLocation());
                }
            }

    }

}
