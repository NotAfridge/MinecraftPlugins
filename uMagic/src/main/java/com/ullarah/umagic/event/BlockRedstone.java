package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

public class BlockRedstone extends MagicFunctions implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(BlockRedstoneEvent event) {

        if (event.getBlock().hasMetadata(metaLamp)) event.setNewCurrent(15);

    }

}
