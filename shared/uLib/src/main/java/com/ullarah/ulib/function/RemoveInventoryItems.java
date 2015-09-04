package com.ullarah.ulib.function;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class RemoveInventoryItems {

    public static void remove(PlayerInventory inventory, Material material, int amount) {

        for (ItemStack itemStack : inventory.getContents()) {

            if (itemStack != null && itemStack.getType() == material) {

                int newAmount = itemStack.getAmount() - amount;

                if (newAmount > 0) {

                    itemStack.setAmount(newAmount);
                    break;

                } else {

                    inventory.remove(itemStack);
                    if (-newAmount == 0) break;

                }

            }

        }

    }

}