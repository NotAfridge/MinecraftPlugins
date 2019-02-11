package com.ullarah.umagic.block;

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceInventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Furnace extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        if (block.hasMetadata(metaFurn)) {
            turnOff(meta);
        } else {
            turnOn(meta);
        }
    }

    private void turnOn(InteractMeta meta) {
        Block block = meta.getBlock();
        Player player = meta.getPlayer();

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

    private void turnOff(InteractMeta meta) {
        Block block = meta.getBlock();

        org.bukkit.block.Furnace furnace = (org.bukkit.block.Furnace) block.getState();
        FurnaceInventory furnaceInventory = furnace.getInventory();
        furnaceInventory.setFuel(null);
        furnaceInventory.setSmelting(null);
        furnaceInventory.setResult(null);

        org.bukkit.block.data.type.Furnace data = (org.bukkit.block.data.type.Furnace) block.getBlockData();
        BlockFace facing = data.getFacing();

        // Hacky way to get furnace to reset
        block.setType(Material.AIR);
        block.setType(Material.FURNACE);
        data = (org.bukkit.block.data.type.Furnace) block.getBlockData();
        data.setFacing(facing);
        block.setBlockData(data);

        block.removeMetadata(metaFurn, getPlugin());
    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.FURNACE);
    }

}
