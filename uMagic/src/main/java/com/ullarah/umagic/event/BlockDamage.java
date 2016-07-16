package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

class BlockDamage implements Listener {

    @EventHandler
    public void event(BlockDamageEvent e) {

        CommonString c = new CommonString();
        MagicFunctions f = new MagicFunctions();
        Block b = e.getBlock();

        for (String meta : new String[]{f.metaWool, f.metaLadd})
            if (b.hasMetadata(meta)) {

                c.messageSend(MagicInit.getPlugin(), e.getPlayer(),
                        "Magical block detected, convert back using Magic Hoe.");
                e.setCancelled(true);

            }

    }

}
