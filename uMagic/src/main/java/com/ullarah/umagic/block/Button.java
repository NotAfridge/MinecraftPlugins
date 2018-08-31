package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.block.data.Powerable;

public class Button extends MagicFunctions {

    public Button(Block block) {

        super(false);

        Powerable data = (Powerable) block.getBlockData();
        data.setPowered(!data.isPowered());
        block.setBlockData(data);

    }

}
