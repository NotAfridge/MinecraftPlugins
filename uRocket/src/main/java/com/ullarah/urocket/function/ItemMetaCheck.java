package com.ullarah.urocket.function;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemMetaCheck {

    /**
     * Item metadata check, custom name only.
     *
     * @param item the item object to check
     * @param name the display name of the item
     * @return if the item has a custom name and matches name given
     */
    public boolean check(ItemStack item, String name) {

        if (item != null && item.hasItemMeta()) {

            ItemMeta meta = item.getItemMeta();

            if (meta.hasDisplayName()) {

                String metaDisplayName = meta.getDisplayName();

                if (metaDisplayName.equals(name)) return true;

            }

        }

        return false;

    }

    /**
     * Item metadata check, custom lore only.
     *
     * @param item the item object to check
     * @param lore the lore array of the item
     * @return if the item has custom lore and matches the lore array given
     */
    public boolean check(ItemStack item, List<String> lore) {

        if (item != null && item.hasItemMeta()) {

            ItemMeta meta = item.getItemMeta();

            if (meta.hasLore()) {

                List<String> metaLore = meta.getLore();

                if (metaLore.equals(lore)) return true;

            }

        }

        return false;

    }

    /**
     * Item metadata check, custom name, custom lore.
     *
     * @param item the item object to check
     * @param name the display name of the item
     * @param lore the lore array of the item
     * @return if the item has both custom name and lore
     * and matches both name and lore given.
     */
    public boolean check(ItemStack item, String name, List<String> lore) {

        if (item != null && item.hasItemMeta()) {

            ItemMeta meta = item.getItemMeta();

            if (meta.hasDisplayName() && meta.hasLore()) {

                String metaDisplayName = meta.getDisplayName();
                List<String> metaLore = meta.getLore();

                if (metaDisplayName.equals(name) && metaLore.equals(lore)) return true;

            }

        }

        return false;

    }

}
