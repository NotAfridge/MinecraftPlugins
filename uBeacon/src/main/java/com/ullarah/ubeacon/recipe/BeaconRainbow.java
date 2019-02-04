package com.ullarah.ubeacon.recipe;

import com.ullarah.ubeacon.BeaconInit;
import com.ullarah.ubeacon.function.NewRecipe;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class BeaconRainbow implements NewRecipe {

    private static ItemStack rainbow() {

        ItemStack beacon = new ItemStack(Material.BEACON, 1);

        ItemMeta beaconMeta = beacon.getItemMeta();

        beaconMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Rainbow Beacon");
        beaconMeta.setLore(Arrays.asList(
                ChatColor.GREEN + "It's a rainbow beacon!",
                ChatColor.AQUA + "So colourful!",
                ChatColor.YELLOW + "So bright!"));

        beaconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        beacon.setItemMeta(beaconMeta);

        beacon.addUnsafeEnchantment(Enchantment.LUCK, 1);

        return beacon;

    }

    public ArrayList<ShapedRecipe> recipes() {

        ArrayList<ShapedRecipe> recipes = new ArrayList<>();

        for (Material stained : BeaconInit.getMaterials()) {

            NamespacedKey key = new NamespacedKey(BeaconInit.getPlugin(), "beacon.rainbow." + stained);
            ShapedRecipe beaconRecipe = new ShapedRecipe(key, rainbow());

            beaconRecipe.shape("SSS", " C ", "RBR");

            beaconRecipe.setIngredient('B', Material.BEACON);
            beaconRecipe.setIngredient('R', Material.REDSTONE_BLOCK);
            beaconRecipe.setIngredient('C', Material.COMPARATOR);
            beaconRecipe.setIngredient('S', stained);

            recipes.add(beaconRecipe);

        }

        return recipes;

    }

}
