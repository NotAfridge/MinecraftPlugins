package com.ullarah.ulib.function;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RemoveInventoryItems {

    /**
     * Removes a number of material items from an inventory
     *
     * @param inventory the inventory object
     * @param material  the type of item to remove from inventory
     * @param amount    the amount of item(s) to remove from inventory
     * @return if the item removal was successful
     */
    public boolean remove(Inventory inventory, Material material, int amount) {

        for (ItemStack itemStack : inventory.getContents()) {

            if (itemStack != null && itemStack.getType() == material) {

                int newAmount = itemStack.getAmount() - amount;

                if (newAmount > 0) {

                    itemStack.setAmount(newAmount);
                    return true;

                } else {

                    inventory.remove(itemStack);
                    if (-newAmount == 0) return true;

                }

            }

        }

        return false;

    }

}