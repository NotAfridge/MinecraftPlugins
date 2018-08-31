package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.metadata.FixedMetadataValue;

public class Torch extends MagicFunctions {

    public Torch(Block block) {

        super(false);

        Directional data = (Directional) block.getBlockData();
        BlockFace facing = data.getFacing();
        int index = (facing.getOppositeFace().ordinal() + 1) % 4;
        data.setFacing(BlockFace.values()[index]);

        block.setMetadata(metaTrch, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaTrch);

        block.setBlockData(data);
    }

}
