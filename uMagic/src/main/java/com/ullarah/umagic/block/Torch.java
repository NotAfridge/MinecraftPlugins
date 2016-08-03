package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Torch extends MagicFunctions {

    public Torch(Block block) {

        block.setData(block.getData() >= 4 ? (byte) 0 : (byte) (block.getData() + 1));

        block.setMetadata(metaTrch, new FixedMetadataValue(MagicInit.getPlugin(), true));
        saveMetadata(block.getLocation(), metaTrch);

    }

}
