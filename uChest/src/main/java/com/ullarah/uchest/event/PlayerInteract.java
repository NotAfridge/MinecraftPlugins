package com.ullarah.uchest.event;

import com.ullarah.uchest.ChestFunctions;
import com.ullarah.uchest.ChestInit;
import com.ullarah.uchest.command.ChestCreation;
import com.ullarah.uchest.function.CommonString;
import com.ullarah.uchest.function.HiddenLore;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.UUID;

public class PlayerInteract implements Listener {

    @EventHandler
    public void event(PlayerInteractEvent event) {

        ChestFunctions f = new ChestFunctions();
        CommonString s = new CommonString();
        Player p = event.getPlayer();

        ItemStack inMainHand = p.getInventory().getItemInMainHand();

        if (f.checkPickupTool(inMainHand)) {

            if (ChestInit.getWorldGuard() == null)
                s.messageSend(ChestInit.getPlugin(), p, "Chest Pickup Tool is currently unavailable.");

            if (event.getAction() == Action.LEFT_CLICK_AIR
                    || event.getAction() == Action.RIGHT_CLICK_AIR) return;

            Block b = event.getClickedBlock();

            if (!f.checkBlock(p, b)) if (!p.hasPermission("chest.bypass")) {
                event.setCancelled(true);
                return;
            }

            if (p.isSneaking()) {
                event.setCancelled(true);
                return;
            }

            if (b.getType() == Material.CHEST) {

                if (b.getState() instanceof Chest) {

                    Chest c = (Chest) b.getState();
                    Inventory v = c.getInventory();

                    if (v.getSize() > 27) {
                        s.messageSend(ChestInit.getPlugin(), p, ChatColor.RED + "Double chests are not supported.");
                        event.setCancelled(true);
                        return;
                    }

                    UUID chestUUID = new ChestCreation().create(p, v);

                    if (chestUUID != null) {

                        inMainHand.setType(Material.CHEST);
                        ItemMeta m = inMainHand.getItemMeta();

                        m.setDisplayName("" + ChatColor.YELLOW + ChatColor.BOLD + "Content Chest");
                        m.setLore(Collections.singletonList(
                                ChatColor.AQUA + "Chest can be named using " + ChatColor.GOLD + "/pchest [name]"
                                        + new HiddenLore().encode(chestUUID.toString())
                        ));

                        inMainHand.setItemMeta(m);

                        if (p.getGameMode() != GameMode.CREATIVE) {

                            v.clear();
                            b.setType(Material.AIR);

                        } else event.setCancelled(true);

                    }

                }

            } else event.setCancelled(true);

        }

    }

}
