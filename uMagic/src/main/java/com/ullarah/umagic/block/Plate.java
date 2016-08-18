package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;

public class Plate extends MagicFunctions {

    public Plate(Block block) {

        super(false);

        block.setData((byte) 1);

    }

}
