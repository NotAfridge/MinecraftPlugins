package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Slab {

    public void block(Block b) {

        switch (b.getData()) {

            case 0:
                b.setData((byte) 8);
                break;

            case 1:
                b.setData((byte) 9);
                break;

        }

    }

}
