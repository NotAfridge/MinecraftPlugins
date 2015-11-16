package com.ullarah.ulib.function;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class BlockStacks {

    /**
     * Removes items from players inventory, if a block of the same
     * material is present, it will be split into items.
     *
     * @param plugin the plugin object, to grab the name of the plugin
     * @param player the option to log the message(s) to console
     * @param block  the block material to split
     * @param single the item material to remove or give after a block split
     * @param amount the amount of single item material to remove
     * @param split  the amount of items to give after a block split
     * @return whether or not the split or item removal was successful
     */
    public boolean split(Plugin plugin, Player player, Material block, Material single, int amount, int split) {

        String pluginName = ChatColor.GOLD + "[" + plugin.getName() + "] " + ChatColor.WHITE;
        PlayerInventory inventory = player.getInventory();
        String materialType = single.name().toLowerCase();

        if (inventory.contains(single)) new RemoveInventoryItems().remove(inventory, single, amount);
        else if (inventory.contains(block)) {

            if (inventory.firstEmpty() == -1) {

                player.sendMessage(pluginName + ChatColor.RED + "Your inventory is too full to split a " + materialType + " block!");
                return false;

            } else {

                if (new RemoveInventoryItems().remove(inventory, block, 1)) inventory.addItem(new ItemStack(single, split));
                else return false;

            }

        } else {

            player.sendMessage(pluginName + "You have run out of " + materialType + "!");
            return false;

        }

        return true;

    }

}
