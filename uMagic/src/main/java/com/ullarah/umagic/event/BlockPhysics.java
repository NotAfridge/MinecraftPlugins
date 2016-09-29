package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
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

        for (String meta : new String[]{metaSand, metaWool, metaLadd, metaRail, metaCact, metaLava,
                metaSign, metaTrch, metaBanr, metaVine, metaBeds, metaFire, metaSnow, metaWate})
            if (event.getBlock().hasMetadata(meta)) event.setCancelled(true);

    }

}
