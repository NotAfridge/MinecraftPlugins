package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class BlockDamage extends MagicFunctions implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(BlockDamageEvent event) {

        String[] magicalBlocks = new String[]{metaWool, metaLadd, metaFurn};

        for (String meta : magicalBlocks) {

            if (event.getBlock().hasMetadata(meta)) {

                if (!usingMagicHoe(event.getPlayer())) {

                    new CommonString().messageSend(event.getPlayer(),
                            "Magical block detected, convert back using Magic Hoe.");

                    event.setCancelled(true);

                }

            }

        }





    }

}
