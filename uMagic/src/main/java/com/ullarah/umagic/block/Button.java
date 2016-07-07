package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Button {

    public void block(Block b) {

        b.setData((byte) (b.getData() + 8));

    }

}
