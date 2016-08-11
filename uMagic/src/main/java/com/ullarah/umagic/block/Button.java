package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;

public class Button extends MagicFunctions {

    public Button(Block block) {

        block.setData((byte) (block.getData() + 8));

        displayParticles(block);

    }

}
