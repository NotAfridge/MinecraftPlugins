package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Bukkit;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysics extends MagicFunctions implements Listener {

    public BlockPhysics() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(BlockPhysicsEvent event) {

        String[] metas = new String[] {metaSand, metaWool, metaLadd, metaRail, metaCact, metaLava, metaSign,
                metaTrch, metaBanr, metaVine, metaBeds, metaFire, metaSnow, metaCice, metaWate, metaReds};
        BlockFace[] faces = new BlockFace[] {BlockFace.SELF, BlockFace.UP, BlockFace.DOWN,
                BlockFace.NORTH, BlockFace.SOUTH, BlockFace.EAST, BlockFace.WEST};

        for (BlockFace face : faces) {
            for (String meta : metas) {
                if (event.getBlock().getRelative(face).hasMetadata(meta)) {
                    event.setCancelled(true);
                }
            }
        }

    }

}
