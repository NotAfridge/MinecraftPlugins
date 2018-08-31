package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Powerable;
import org.bukkit.material.PressurePlate;

public class Plate extends MagicFunctions {

    public Plate(Block block) {

        super(false);

        Powerable data = (Powerable) block.getBlockData();
        data.setPowered(!data.isPowered());
        block.setBlockData(data);

    }

}
