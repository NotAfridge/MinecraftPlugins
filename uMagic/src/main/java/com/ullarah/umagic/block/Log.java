package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;

public class Log extends MagicFunctions {

    public Log(Block block) {

        switch (block.getData()) {

            case 0:
            case 4:
            case 8:
                block.setData((byte) 12);
                break;

            case 1:
            case 5:
            case 9:
                block.setData((byte) 13);
                break;

            case 2:
            case 6:
            case 10:
                block.setData((byte) 14);
                break;

            case 3:
            case 7:
            case 11:
                block.setData((byte) 15);
                break;

        }

    }

}
