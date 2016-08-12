package com.ullarah.umagic.block;

import com.ullarah.umagic.MagicFunctions;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Emerald extends MagicFunctions {

    public Emerald(Block block, Player player) {

        if (player.hasPermission("umagic.danger")) {

            getCommonString().messageSend(player, "Block converted to Bedrock. Be careful!");

            block.setType(Material.BEDROCK);

            block.setMetadata(metaEmBr, new FixedMetadataValue(getPlugin(), true));
            saveMetadata(block.getLocation(), metaEmBr);

        }

    }

}
