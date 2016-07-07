package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Stairs {

    public void block(Block b) {

        b.setData(b.getData() >= 7 ? (byte) 0 : (byte) (b.getData() + 1));

    }

}
