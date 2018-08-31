package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.metadata.FixedMetadataValue;

public class Furnace extends MagicFunctions {

    public Furnace(Block block, Player player) {

        super(false);

        org.bukkit.block.Furnace furnace = (org.bukkit.block.Furnace) block.getState();

        String noChange = "Furnace has contents. Not changing.";

        FurnaceInventory furnaceInventory = furnace.getInventory();

        if (furnaceInventory.getFuel() != null || furnaceInventory.getSmelting() != null
                || furnaceInventory.getResult() != null) {
            getCommonString().messageSend(player, noChange);
            return;
        }

        furnaceInventory.setFuel(getFurnaceFuel());
        furnaceInventory.setSmelting(getFurnaceSmelt());
        furnaceInventory.setResult(null);

        furnace.setBurnTime((short) Integer.MAX_VALUE);
        furnace.setCookTime((short) Integer.MAX_VALUE);

        block.setMetadata(metaFurn, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaFurn);
    }

}
