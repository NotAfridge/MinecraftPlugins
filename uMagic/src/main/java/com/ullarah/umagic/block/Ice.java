package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.metadata.FixedMetadataValue;

public class Ice extends MagicFunctions {

    public Ice(Block block) {

        super(false);

        block.setType(Material.FROSTED_ICE, true);

        Ageable data = (Ageable) block.getBlockData();
        data.setAge(data.getMaximumAge());
        block.setBlockData(data);

        block.setMetadata(metaCice, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaCice);

    }

}
