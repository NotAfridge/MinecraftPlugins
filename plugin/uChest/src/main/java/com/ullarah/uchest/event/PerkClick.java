package com.ullarah.uchest.event;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static com.ullarah.uchest.ChestInit.getMsgPrefix;
import static com.ullarah.uchest.ChestInit.getVaultEconomy;
import static com.ullarah.ulib.function.Experience.addExperience;

public class PerkClick implements Listener {

    @SuppressWarnings("unused")
    @EventHandler
    public void event(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        String title = event.getInventory().getTitle();

        if (title.equals(ChatColor.DARK_GREEN + "Perk Chest")) {

            if (event.getRawSlot() < 54) {

                if (event.getCurrentItem().getType() != Material.AIR) {

                    int random;

                    ItemStack itemStack = event.getCurrentItem();
                    String perkTitle = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());

                    switch (perkTitle) {

                        case "Random Experience":
                            random = new Random().nextInt(1000);
                            addExperience(player, random);
                            player.sendMessage(getMsgPrefix() + "You gained " + ChatColor.GOLD + random + "xp");
                            break;

                        case "Random Money":
                            random = new Random().nextInt(100);
                            getVaultEconomy().depositPlayer(player, random);
                            player.sendMessage(getMsgPrefix() + "You gained " + ChatColor.GREEN + "$" + random);
                            break;

                        default:
                            break;

                    }

                    player.closeInventory();

                }

            }

            event.setCancelled(true);

        }

    }

}
