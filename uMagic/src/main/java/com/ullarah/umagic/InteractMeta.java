package com.ullarah.umagic;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class InteractMeta {

    private Block block;
    private BlockFace face;
    private Player player;

    public InteractMeta(Block block, BlockFace face, Player player) {
        this.block = block;
        this.face = face;
        this.player = player;
    }

    public Block getBlock() {
        return block;
    }

    public BlockFace getFace() {
        return face;
    }

    public Player getPlayer() {
        return player;
    }
}
