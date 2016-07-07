package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Log {

    public void block(Block b) {

        switch (b.getData()) {

            case 0:
            case 4:
            case 8:
                b.setData((byte) 12);
                break;

            case 1:
            case 5:
            case 9:
                b.setData((byte) 13);
                break;

            case 2:
            case 6:
            case 10:
                b.setData((byte) 14);
                break;

            case 3:
            case 7:
            case 11:
                b.setData((byte) 15);
                break;

        }

    }

}
