package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace extends MagicFunctions implements Listener {

    public BlockPlace() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(BlockPlaceEvent event) {

        if (event.getBlockPlaced().getLocation().getBlock().hasMetadata(metaVoid)) {

            getActionMessage().message(event.getPlayer(), "" + ChatColor.AQUA + ChatColor.BOLD
                    + "Magical Block Detected!"
                    + ChatColor.RED + ChatColor.BOLD + " Convert back using Magical Hoe!");

            event.setCancelled(true);

        }

    }

}
