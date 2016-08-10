package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Furnace extends MagicFunctions {

    public Furnace(Block block, Player player) {

        String noChange = "Furnace has contents. Not changing.";

        org.bukkit.block.Furnace furnace = (org.bukkit.block.Furnace) block.getState();

        if (furnace.getInventory().getFuel() != null) {
            new CommonString().messageSend(player, noChange);
            return;
        }

        if (furnace.getInventory().getSmelting() != null) {
            new CommonString().messageSend(player, noChange);
            return;
        }

        if (furnace.getInventory().getResult() != null) {
            new CommonString().messageSend(player, noChange);
            return;
        }

        furnace.getInventory().setFuel(getFurnaceFuel());
        furnace.getInventory().setSmelting(getFurnaceSmelt());

        furnace.update();

        furnace.setCookTime((short) Integer.MAX_VALUE);
        furnace.setBurnTime((short) Integer.MAX_VALUE);

        block.setMetadata(metaFurn, new FixedMetadataValue(MagicInit.getPlugin(), true));
        saveMetadata(block.getLocation(), metaFurn);

    }

}
