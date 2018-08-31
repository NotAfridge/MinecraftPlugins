package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Lightable;
import org.bukkit.metadata.FixedMetadataValue;

public class Lamp extends MagicFunctions {

    public Lamp(Block block) {

        super(false);

        Lightable data = (Lightable) block.getBlockData();
        data.setLit(!data.isLit());

        block.setMetadata(metaLamp, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaLamp);

        block.setBlockData(data);

    }

}
