package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Stairs {

    public Stairs(Block block) {

        block.setData(block.getData() >= 7 ? (byte) 0 : (byte) (block.getData() + 1));

    }

}
