package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockDamage extends MagicFunctions implements Listener {

    @EventHandler
    public void event(BlockDamageEvent event) {

        for (String meta : new String[]{metaWool, metaLadd})
            if (event.getBlock().hasMetadata(meta)) {

                new CommonString().messageSend(event.getPlayer(),
                        "Magical block detected, convert back using Magic Hoe.");
                event.setCancelled(true);

            }

    }

}
