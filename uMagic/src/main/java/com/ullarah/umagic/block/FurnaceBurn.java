package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class FurnaceBurn extends MagicFunctions {

    public FurnaceBurn(Block block) {

        super(false);

        org.bukkit.block.Furnace furnace = (org.bukkit.block.Furnace) block.getState();

        if (block.hasMetadata(metaFurn)) {

            furnace.getInventory().setFuel(null);
            furnace.getInventory().setSmelting(null);
            furnace.getInventory().setResult(null);

            furnace.update();

            block.removeMetadata(metaFurn, getPlugin());
            removeMetadata(block.getLocation());

            block.setType(Material.FURNACE);

        }

    }

}
