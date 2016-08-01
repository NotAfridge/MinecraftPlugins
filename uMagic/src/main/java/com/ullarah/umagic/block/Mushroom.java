package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Mushroom {

    public Mushroom(Block block) {

        block.setData(block.getData() < 15 ? block.getData() == 10 ?
                (byte) 14 : (byte) (block.getData() + 1) : (byte) 0);

    }

}
