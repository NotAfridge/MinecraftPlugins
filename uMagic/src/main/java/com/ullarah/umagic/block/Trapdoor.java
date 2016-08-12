package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;

public class Trapdoor extends MagicFunctions {

    public Trapdoor(Block block) {

        block.setData(block.getData() >= 8 ? (byte) 3 : (byte) (block.getData() + 1));

    }

}
