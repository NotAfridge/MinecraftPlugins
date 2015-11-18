package com.ullarah.ulib.function;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockStacks {

    /**
     * Removes items from players inventory, if a block of the same
     * material is present, it will be split into items.
     *
     * @param inventory the inventory to check for items
     * @param block     the block material to split
     * @param single    the item material to remove or give after a block split
     * @param amount    the amount of single item material to remove
     * @param split     the amount of items to give after a block split
     * @return whether or not the split or item removal was successful
     */
    public boolean split(Inventory inventory, Material block, Material single, int amount, int split) {

        if (inventory.contains(single)) new RemoveInventoryItems().remove(inventory, single, amount);
        else if (inventory.contains(block)) {

            if (inventory.firstEmpty() == -1) {

                return false;

            } else {

                if (new RemoveInventoryItems().remove(inventory, block, 1)) inventory.addItem(new ItemStack(single, split));
                else return false;

            }

        } else {

            return false;

        }

        return true;

    }

}
