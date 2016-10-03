package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.EntityBlockFormEvent;

public class EntityBlockForm extends MagicFunctions implements Listener {

    public EntityBlockForm() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(EntityBlockFormEvent event) {

        for (String meta : new String[]{metaWate, metaLava, metaCice})
            if (event.getBlock().hasMetadata(meta)) event.setCancelled(true);

    }

}
