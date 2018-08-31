package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.blockdata.DirectionalData;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.MultipleFacing;

public class Terracotta extends MagicFunctions {

    public Terracotta(Block block) {

        super(false);

        new DirectionalData(block);

    }

}
