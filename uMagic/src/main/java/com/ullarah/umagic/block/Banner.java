package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.MagicInit;
import org.bukkit.DyeColor;
import org.bukkit.block.Block;
import org.bukkit.block.banner.Pattern;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;

public class Banner extends MagicFunctions {

    public Banner(Block block) {

        org.bukkit.block.Banner banner = (org.bukkit.block.Banner) block.getState();

        DyeColor color = banner.getBaseColor();
        List<Pattern> patterns = banner.getPatterns();

        byte data = block.getData();
        block.setData(data < 15 ? (byte) (data + 1) : (byte) 0);

        banner.setBaseColor(color);
        banner.setPatterns(patterns);

        block.setMetadata(metaBanr, new FixedMetadataValue(MagicInit.getPlugin(), true));
        saveMetadata(block.getLocation(), metaBanr);

    }

}
