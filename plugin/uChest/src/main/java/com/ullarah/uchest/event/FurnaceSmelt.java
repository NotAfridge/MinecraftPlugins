package com.ullarah.uchest.event;

import com.ullarah.uchest.furnace.FleshLeather;
import com.ullarah.uchest.furnace.FlintSulphur;
import org.bukkit.Material;
import org.bukkit.block.Furnace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.FurnaceSmeltEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

import static com.ullarah.uchest.ChestInit.customBurner;
import static com.ullarah.uchest.ChestInit.getPlugin;

public class FurnaceSmelt implements Listener {

    @EventHandler
    public void event(final FurnaceSmeltEvent event) {

        Furnace furnace = (Furnace) event.getBlock().getState();

        short cookTime = 400;

        switch (event.getSource().getType()) {

            case ROTTEN_FLESH:
                event.setResult(checkFurnaceCooker(furnace)
                        ? FleshLeather.result() : new ItemStack(Material.AIR));
                break;

            case FLINT:
                event.setResult(checkFurnaceCooker(furnace)
                        ? FlintSulphur.result() : new ItemStack(Material.AIR));
                break;

            case IRON_HELMET:
                furnace.setCookTime(cookTime);
                break;

            case IRON_CHESTPLATE:
                furnace.setCookTime(cookTime);
                break;

            case IRON_LEGGINGS:
                furnace.setCookTime(cookTime);
                break;

            case IRON_BOOTS:
                furnace.setCookTime(cookTime);
                break;

        }

    }

    private static boolean checkFurnaceCooker(Furnace furnace) {

        int amount = 0;

        Material smelting = furnace.getInventory().getSmelting().getType();

        if (customBurner.containsKey(furnace)) {

            HashMap<Material, Integer> furnaceType = customBurner.get(furnace);

            if (furnaceType.containsKey(smelting)) amount = furnaceType.get(smelting);

            if (amount == getPlugin().getConfig().getInt("furnaceamount")) {

                customBurner.remove(furnace);
                return true;

            } else {

                furnaceType.put(smelting, amount + 1);
                customBurner.put(furnace, furnaceType);

                return false;

            }

        } else {

            HashMap<Material, Integer> furnaceType = new HashMap<Material, Integer>(){{put(smelting, 1);}};
            customBurner.put(furnace, furnaceType);

            return false;

        }

    }

}
