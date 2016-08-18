package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;

public class Stairs extends MagicFunctions {

    public Stairs(Block block) {

        super(false);

        block.setData(block.getData() >= 7 ? (byte) 0 : (byte) (block.getData() + 1));

    }

}
