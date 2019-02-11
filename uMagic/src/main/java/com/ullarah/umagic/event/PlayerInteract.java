package com.ullarah.umagic.event;

import com.ullarah.umagic.InteractMeta;
import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.block.*;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerInteract extends MagicFunctions implements Listener {

    public PlayerInteract() {
        super(false);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Block block = event.getClickedBlock();
        BlockFace face = event.getBlockFace();

        if (!usingMagicHoe(player))
            return;

        if (!checkHoeInteract(event, player, block))
            return;

        event.setCancelled(true);

        InteractMeta meta = new InteractMeta(block, face, player);
        getBlock(block.getType()).process(meta);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(PlayerSwapHandItemsEvent event) {

        Player player = event.getPlayer();

        if (usingMagicHoe(player)) {

            getActionMessage().message(player, "" + ChatColor.RED + ChatColor.BOLD
                    + "Cannot be used in off-hand slot!");
            event.setCancelled(true);

        }

    }

}
