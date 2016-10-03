package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

public class BlockForm extends MagicFunctions implements Listener {

    public BlockForm() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(BlockFormEvent event) {

        for (String meta : new String[]{metaWate, metaLava, metaCice})
            if (event.getBlock().hasMetadata(meta)) event.setCancelled(true);

    }

}
