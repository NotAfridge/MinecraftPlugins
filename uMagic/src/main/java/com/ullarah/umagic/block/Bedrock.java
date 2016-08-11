package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Bedrock extends MagicFunctions {

    public Bedrock(Block block, Player player) {

        if (block.hasMetadata(metaEmBr)) {

            new CommonString().messageSend(player,
                    "Block converted to Barrier. Be careful!");

            block.setType(Material.BARRIER);

            displayParticles(block);

        }

    }

}
