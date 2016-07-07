package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Mushroom {

    public void block(Block b) {

        b.setData(b.getData() < 15 ? b.getData() == 10 ?
                (byte) 14 : (byte) (b.getData() + 1) : (byte) 0);

    }

}
