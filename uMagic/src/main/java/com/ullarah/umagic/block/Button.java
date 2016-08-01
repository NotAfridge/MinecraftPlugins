package com.ullarah.umagic.block;

import org.bukkit.block.Block;

public class Button {

    public Button(Block block) {

        block.setData((byte) (block.getData() + 8));

    }

}
