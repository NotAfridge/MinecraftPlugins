package com.ullarah.ulib.function;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemMetaCheck {

    public static boolean check(ItemStack stack, String name) {

        if (stack != null && stack.hasItemMeta()) {

            ItemMeta meta = stack.getItemMeta();

            if (meta.hasDisplayName()) {

                String metaDisplayName = meta.getDisplayName();

                if (metaDisplayName.equals(name)) return true;

            }

        }

        return false;

    }

    public static boolean check(ItemStack stack, List<String> lore) {

        if (stack != null && stack.hasItemMeta()) {

            ItemMeta meta = stack.getItemMeta();

            if (meta.hasLore()) {

                List<String> metaLore = meta.getLore();

                if (metaLore.equals(lore)) return true;

            }

        }

        return false;

    }

    public static boolean check(ItemStack stack, String name, List<String> lore) {

        if (stack != null && stack.hasItemMeta()) {

            ItemMeta meta = stack.getItemMeta();

            if (meta.hasDisplayName()) {

                String metaDisplayName = meta.getDisplayName();
                List<String> metaLore = meta.getLore();

                if (metaDisplayName.equals(name) && metaLore.equals(lore)) return true;

            }

        }

        return false;

    }

}
