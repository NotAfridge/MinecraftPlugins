package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.metadata.FixedMetadataValue;

public class Wool extends MagicFunctions {

    public Wool(Block block) {

        byte woolData = block.getData();
        block.setType(Material.CARPET);
        block.setData(woolData);
        block.setMetadata(metaWool, new FixedMetadataValue(MagicInit.getPlugin(), true));
        saveMetadata(block.getLocation(), metaWool);

    }

}
