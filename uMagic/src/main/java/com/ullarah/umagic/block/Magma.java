package com.ullarah.umagic.block;

/*import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.selections.CuboidSelection;*/

import com.ullarah.umagic.InteractMeta;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;

public class Magma extends BaseBlock {

    public void process(InteractMeta meta) {
        Block block = meta.getBlock();

        Location location = block.getLocation();

        block.setType(Material.LAVA, true);

        Levelled data = (Levelled) block.getBlockData();
        data.setLevel(7);

        block.setBlockData(data);

        block.setMetadata(metaLava, new FixedMetadataValue(getPlugin(), true));
        saveMetadata(block.getLocation(), metaLava);

        /*obsidianCheck(location, player);
    }

    private void obsidianCheck(Location location, Player player) {

        getPlugin().getServer().getScheduler().runTaskAsynchronously(getPlugin(), () -> {

            Block block = location.getBlock();

            if (block.getType().equals(Material.OBSIDIAN)) {

                getPlugin().getServer().getScheduler().runTask(getPlugin(), () -> {

                    try {

                        getWorldEdit().setSelection(player, new CuboidSelection(block.getWorld(), location, location));

                        EditSession editSession = getWorldEdit().createEditSession(player);
                        editSession.setBlock(getWorldEdit().getSelection(player).getNativeMaximumPoint(), new BaseBlock(11, 7));
                        editSession.flushQueue();

                    } catch (MaxChangedBlocksException e) {

                        e.printStackTrace();

                    }

                });

            }

        });*/

    }

    public List<Material> getPermittedBlocks() {
        return Arrays.asList(Material.MAGMA_BLOCK);
    }

}
