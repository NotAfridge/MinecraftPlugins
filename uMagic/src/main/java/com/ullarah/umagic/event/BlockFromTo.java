package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

public class BlockFromTo extends MagicFunctions implements Listener {

    public BlockFromTo() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(BlockFromToEvent event) {

        for (String meta : new String[]{metaWate})
            if (event.getBlock().hasMetadata(meta)) event.setCancelled(true);

    }

}
