package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Ice extends MagicFunctions {

    public Ice(Block block) {

        super(false);

        if (block.getType().equals(Material.ICE)) block.setType(Material.FROSTED_ICE, true);

        block.setMetadata(metaCice, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaCice);

        switch (block.getData()) {

            case 0:
                block.setData((byte) 1);
                break;

            case 1:
                block.setData((byte) 2);
                break;

            case 2:
                block.setData((byte) 3);
                break;

            case 3:
                block.setData((byte) 0);
                break;

        }

    }

}
