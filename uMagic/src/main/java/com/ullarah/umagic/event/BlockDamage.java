package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockDamage extends MagicFunctions implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(BlockDamageEvent event) {

        String[] magicalBlocks = new String[]{metaWool, metaLadd, metaFurn, metaFire};

        for (String meta : magicalBlocks) {

            if (event.getBlock().hasMetadata(meta)) {

                if (!usingMagicHoe(event.getPlayer())) {

                    getCommonString().messageSend(event.getPlayer(),
                            "Magical block detected, convert back using Magic Hoe.");

                    event.setCancelled(true);

                }

            }

        }


    }

}
