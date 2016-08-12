package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;

public class Slab extends MagicFunctions {

    public Slab(Block block) {

        switch (block.getData()) {

            case 0:
                block.setData((byte) 8);
                break;

            case 1:
                block.setData((byte) 9);
                break;

        }

    }

}
