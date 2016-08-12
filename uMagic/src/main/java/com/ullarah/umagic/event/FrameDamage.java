package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class FrameDamage extends MagicFunctions implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(HangingBreakEvent event) {

        if (event.getEntity() instanceof ItemFrame || event.getEntity() instanceof Painting) {

            Hanging hanging = event.getEntity();

            if (hanging.hasMetadata(metaFram)) event.setCancelled(true);

        }

    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void event(HangingBreakByEntityEvent event) {

        if (event.getEntity() instanceof ItemFrame || event.getEntity() instanceof Painting) {

            Hanging hanging = event.getEntity();

            if (event.getRemover() instanceof Player) {

                Player player = (Player) event.getRemover();

                if (usingMagicHoe(player)) {

                    if (!player.hasPermission("umagic.usage")) {
                        event.setCancelled(true);
                        return;
                    }

                    if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                        event.setCancelled(true);
                        return;
                    }

                    hanging.setMetadata(metaFram, new FixedMetadataValue(getPlugin(), true));
                    saveMetadata(hanging.getLocation(), metaFram);

                    hanging.getWorld().playSound(hanging.getLocation(), Sound.UI_BUTTON_CLICK, 0.75f, 0.75f);
                    event.setCancelled(true);

                } else if (hanging.hasMetadata(metaFram)) {

                    hanging.removeMetadata(metaFram, getPlugin());
                    removeMetadata(hanging.getLocation());

                }

            }

        }

    }

}
