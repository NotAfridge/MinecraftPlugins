package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockGrowEvent;

public class BlockGrowth extends MagicFunctions implements Listener {

    @EventHandler
    public void event(BlockGrowEvent event) {

        for (String meta : new String[]{metaVine})
            if (event.getBlock().hasMetadata(meta)) event.setCancelled(true);

    }

}
