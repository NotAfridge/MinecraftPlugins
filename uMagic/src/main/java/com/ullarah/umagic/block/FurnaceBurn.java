package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.FurnaceInventory;

public class FurnaceBurn extends MagicFunctions {

    public FurnaceBurn(Block block) {

        super(false);

        org.bukkit.block.Furnace furnace = (org.bukkit.block.Furnace) block.getState();

        if (block.hasMetadata(metaFurn)) {

            FurnaceInventory furnaceInventory = furnace.getInventory();

            furnaceInventory.setFuel(null);
            furnaceInventory.setSmelting(null);
            furnaceInventory.setResult(null);

            block.removeMetadata(metaFurn, getPlugin());
            removeMetadata(block.getLocation());

            block.setType(Material.FURNACE);

        }

    }

}
