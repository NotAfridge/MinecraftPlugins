package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;

public class Triphook extends MagicFunctions {

    public Triphook(Block block) {

        byte data = block.getData();

        int[] northHook = {0, 4, 8, 12};
        int[] eastHook = {1, 5, 9, 13};
        int[] southHook = {2, 6, 10, 14};
        int[] westHook = {3, 7, 11, 15};

        for (int[] iA : new int[][]{northHook, eastHook, southHook, westHook})
            block.setData((data == iA[3] ? (byte) iA[0] : (byte) (data + 4)));

    }

}
