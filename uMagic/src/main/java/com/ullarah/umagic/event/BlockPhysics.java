package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysics implements Listener {

    @EventHandler
    public void event(BlockPhysicsEvent e) {

        MagicFunctions f = new MagicFunctions();
        Block b = e.getBlock();

        for (String meta : new String[]{f.metaSand, f.metaWool, f.metaLadd, f.metaRail})
            if (b.hasMetadata(meta)) e.setCancelled(true);

    }

}
