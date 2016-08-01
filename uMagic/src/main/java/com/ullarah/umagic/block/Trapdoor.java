package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Trapdoor {

    public Trapdoor(Block block) {

        block.setData(block.getData() >= 8 ? (byte) 3 : (byte) (block.getData() + 1));

    }

}
