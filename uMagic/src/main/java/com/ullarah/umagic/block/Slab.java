package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Slab {

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
