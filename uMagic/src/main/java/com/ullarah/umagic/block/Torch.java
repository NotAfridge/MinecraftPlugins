package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Torch extends MagicFunctions {

    public Torch(Block block) {

        switch (block.getData()) {

            case 1:
                block.setData((byte) 3);
                break;

            case 2:
                block.setData((byte) 4);
                break;

            case 3:
                block.setData((byte) 2);
                break;

            case 4:
                block.setData((byte) 5);
                break;

            case 5:
                block.setData((byte) 1);
                break;

        }

        block.setMetadata(metaTrch, new FixedMetadataValue(MagicInit.getPlugin(), true));
        saveMetadata(block.getLocation(), metaTrch);

        displayParticles(block);

    }

}
