package com.ullarah.ulottery.function;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class RemoveInventoryItems {

    /**
     * Removes a number of material items from an inventory
     *
     * @param player   the owner of the inventory
     * @param material the type of item to remove from inventory
     * @param amount   the amount of item(s) to remove from inventory
     * @return if the item removal was successful
     */
    public boolean remove(Player player, Material material, int amount) {

        Inventory inventory = player.getInventory();

        for (int i = 0; i < inventory.getSize(); i++) {

            ItemStack itemStack = inventory.getItem(i);

            if (itemStack != null && itemStack.getType() == material) {

                int newAmount = itemStack.getAmount() - amount;

                if (newAmount > 0) {

                    itemStack.setAmount(newAmount);
                    return true;

                } else {

                    inventory.setItem(i, new ItemStack(Material.AIR));
                    amount = -newAmount;

                    if (amount == 0) return true;

                }

            }

        }

        return false;

    }

}