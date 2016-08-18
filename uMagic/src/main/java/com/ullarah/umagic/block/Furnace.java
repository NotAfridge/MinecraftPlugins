package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Furnace extends MagicFunctions {

    public Furnace(Block block, Player player) {

        super(false);

        String noChange = "Furnace has contents. Not changing.";

        org.bukkit.block.Furnace furnace = (org.bukkit.block.Furnace) block.getState();

        if (furnace.getInventory().getFuel() != null
                || furnace.getInventory().getSmelting() != null
                || furnace.getInventory().getResult() != null) {
            getCommonString().messageSend(player, noChange);
            return;
        }

        furnace.getInventory().setFuel(getFurnaceFuel());
        furnace.getInventory().setSmelting(getFurnaceSmelt());

        furnace.update();

        furnace.setCookTime((short) Integer.MAX_VALUE);
        furnace.setBurnTime((short) Integer.MAX_VALUE);

        block.setMetadata(metaFurn, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaFurn);

    }

}
