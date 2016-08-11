package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Torch extends MagicFunctions {

    public Torch(Block block) {

        byte newData;

        if (block.getData() >= 5) {
            newData = (byte) 0;
        } else {
            newData = (byte) (block.getData() + 1);
        }

        block.setData(newData);

        block.setMetadata(metaTrch, new FixedMetadataValue(MagicInit.getPlugin(), true));
        saveMetadata(block.getLocation(), metaTrch);

        displayParticles(block);

    }

}
