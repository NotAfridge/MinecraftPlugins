package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

public class BlockPhysics extends MagicFunctions implements Listener {

    @EventHandler
    public void event(BlockPhysicsEvent event) {

        for (String meta : new String[]{metaSand, metaWool, metaLadd, metaRail})
            if (event.getBlock().hasMetadata(meta)) event.setCancelled(true);

    }

}
