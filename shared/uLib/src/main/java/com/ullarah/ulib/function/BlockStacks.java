package com.ullarah.ulib.function;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class BlockStacks {

    public static void split(Plugin plugin, Player player, Material single, Material block, int amount) {

        String pluginName = "[" + plugin.getName() + "] ";
        PlayerInventory inventory = player.getInventory();
        String materialType = single.name().toLowerCase();

        if (inventory.contains(single)) {

            RemoveInventoryItems.remove(inventory, single, 2);

        } else if (inventory.contains(block)) {

            if (inventory.firstEmpty() == -1) {

                player.sendMessage(pluginName + ChatColor.RED +
                        "Your inventory is too full to split a " + materialType + " block!");

            } else {

                RemoveInventoryItems.remove(inventory, block, 1);
                inventory.addItem(new ItemStack(single, amount));

            }

        } else {

            player.sendMessage(pluginName + "You have run out of " + materialType + "!");

        }

    }

}
