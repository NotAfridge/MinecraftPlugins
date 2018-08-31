package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.block.Block;
import org.bukkit.material.types.MushroomBlockTexture;

public class Mushroom extends MagicFunctions {

    public Mushroom(Block block) {

        super(false);

        org.bukkit.material.Mushroom mushroom = (org.bukkit.material.Mushroom) block;
        MushroomBlockTexture[] values = MushroomBlockTexture.values();
        int next = (mushroom.getBlockTexture().ordinal() + 1) % values.length;
        mushroom.setBlockTexture(values[next]);

    }

}
