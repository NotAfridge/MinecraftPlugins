package com.ullarah.ujoinquit;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static com.ullarah.ujoinquit.JoinQuitFunctions.getMessage;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.JOIN;
import static com.ullarah.ujoinquit.JoinQuitFunctions.messageType.QUIT;
import static com.ullarah.ujoinquit.JoinQuitFunctions.setMessage;
import static com.ullarah.ujoinquit.JoinQuitInit.playerJoinMessage;
import static com.ullarah.ujoinquit.JoinQuitInit.playerQuitMessage;

class JoinQuitEvents implements Listener {

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (playerJoinMessage.containsKey(player.getUniqueId()))
            event.setJoinMessage(" ▶▶ " + ChatColor.translateAlternateColorCodes('&',
                    getMessage(player, JOIN).replaceAll("%s", player.getPlayerListName())));

    }

    @SuppressWarnings("unused")
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void playerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();

        if (playerQuitMessage.containsKey(player.getUniqueId()))
            event.setQuitMessage(" ◀◀ " + ChatColor.translateAlternateColorCodes('&',
                    getMessage(player, QUIT).replaceAll("%s", player.getPlayerListName())));

    }

    @SuppressWarnings("unused")
    @EventHandler
    public void playerClickInventory(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        String chestName = event.getInventory().getTitle();

        if (chestName.matches(".*(Join|Quit) Message") && player.hasPermission("jq.access")) {

            if (event.getRawSlot() < 54) {

                if (event.getCurrentItem().getType() != Material.AIR && event.getCurrentItem().getType() == Material.PAPER) {

                    if (chestName.matches(".*Join Message")) setMessage(player, JOIN, event.getRawSlot());
                    if (chestName.matches(".*Quit Message")) setMessage(player, QUIT, event.getRawSlot());

                    player.closeInventory();

                }

            }

            event.setCancelled(true);

        }

    }

}
