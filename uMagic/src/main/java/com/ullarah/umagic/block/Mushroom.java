package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;

public class Mushroom extends MagicFunctions {

    public Mushroom(Block block) {

        super(false);

        block.setData(block.getData() < 15 ? block.getData() == 10 ?
                (byte) 14 : (byte) (block.getData() + 1) : (byte) 0);

    }

}
