package com.ullarah.umagic.event;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class FrameDamage extends MagicFunctions implements Listener {

    @EventHandler
    public void event(HangingBreakEvent event) {

        if (event.getEntity() instanceof ItemFrame) {

            Hanging frame = event.getEntity();

            if (frame.hasMetadata(metaFram)) event.setCancelled(true);

        }

    }

    @EventHandler
    public void event(HangingBreakByEntityEvent event) {

        if (event.getEntity() instanceof ItemFrame) {

            Hanging frame = event.getEntity();

            if (event.getRemover() instanceof Player) {

                Player player = (Player) event.getRemover();

                ItemStack inMainHand = player.getInventory().getItemInMainHand(),
                        inOffHand = player.getInventory().getItemInOffHand();

                if (checkMagicHoe(inMainHand) ? checkMagicHoe(inMainHand) : checkMagicHoe(inOffHand)) {

                    if (!player.hasPermission("umagic.usage")) {
                        event.setCancelled(true);
                        return;
                    }

                    if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                        event.setCancelled(true);
                        return;
                    }

                    frame.setMetadata(metaFram, new FixedMetadataValue(MagicInit.getPlugin(), true));
                    saveMetadata(frame.getLocation(), metaFram);

                    new CommonString().messageSend(player, "Floating Item Frame Created.");

                    frame.getWorld().playSound(frame.getLocation(), Sound.UI_BUTTON_CLICK, 0.75f, 0.75f);
                    event.setCancelled(true);

                } else if (frame.hasMetadata(metaFram)) {

                    frame.removeMetadata(metaFram, MagicInit.getPlugin());
                    removeMetadata(frame.getLocation());

                }

            }

        }

    }

}
