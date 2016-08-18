package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Bed extends MagicFunctions {

    public Bed(Block block) {

        super(false);

        if (block.getType() == Material.HAY_BLOCK) {

            block.setType(Material.BED_BLOCK);

            block.setMetadata(metaBeds, new FixedMetadataValue(getPlugin(), true));
            saveMetadata(block.getLocation(), metaBeds);

        }

        if (block.hasMetadata(metaBeds)) {

            byte data = block.getData();

            block.setData((byte) (block.getData() + 1));

            if (data == 3) block.setData((byte) 8);
            if (data == 11) block.setData((byte) 0);

        }

    }

}
