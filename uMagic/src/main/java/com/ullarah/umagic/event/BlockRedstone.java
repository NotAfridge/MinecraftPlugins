package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;

class BlockRedstone implements Listener {

    @EventHandler
    public void event(BlockRedstoneEvent e) {

        MagicFunctions f = new MagicFunctions();
        if (e.getBlock().hasMetadata(f.metaLamp)) e.setNewCurrent(15);

    }

}
