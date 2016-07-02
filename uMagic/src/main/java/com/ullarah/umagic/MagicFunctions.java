package com.ullarah.umagic;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

import static com.ullarah.umagic.MagicInit.getPlugin;

public class MagicFunctions {

    private String world = "world";
    private String locX = "loc.X";
    private String locY = "loc.Y";
    private String locZ = "loc.Z";
    private String data = "data";

    public void initMetadata() {

        for (File file : loadMetadata().listFiles()) {

            FileConfiguration metadataConfig = YamlConfiguration.loadConfiguration(file);

            World metaWorld = getPlugin().getServer().getWorld(metadataConfig.getString(world));

            double lX = metadataConfig.getDouble(locX);
            double lY = metadataConfig.getDouble(locY);
            double lZ = metadataConfig.getDouble(locZ);

            Location location = new Location(metaWorld, lX, lY, lZ);

            Chunk chunk = location.getChunk();
            if (!chunk.isLoaded()) chunk.load();

            String metadata = metadataConfig.getString(data);

            Block block = metaWorld.getBlockAt(location);

            block.setMetadata(metadata, new FixedMetadataValue(getPlugin(), true));

            if (block.getType() == Material.REDSTONE_LAMP_OFF) {

                Block blockUnder = block.getRelative(BlockFace.DOWN);
                Material blockUnderOriginal = blockUnder.getType();

                blockUnder.setType(Material.REDSTONE_BLOCK, true);
                block.getRelative(BlockFace.DOWN).setType(blockUnderOriginal, true);

            }

        }

    }

    public void saveMetadata(Location location, String metadata) {

        File metadataFile = getMetadata(location);

        FileConfiguration metadataConfig = YamlConfiguration.loadConfiguration(metadataFile);

        metadataConfig.set(world, location.getWorld().getName());
        metadataConfig.set(locX, location.getX());
        metadataConfig.set(locY, location.getY());
        metadataConfig.set(locZ, location.getZ());
        metadataConfig.set(data, metadata);

        try {
            metadataConfig.save(metadataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeMetadata(Location location) {

        File metadataFile = getMetadata(location);

        boolean fileDeleted = metadataFile.delete();

        if (!fileDeleted)
            Bukkit.getLogger().log(Level.SEVERE, "Metadata file could not be deleted: " + metadataFile.toString());

    }

    private File loadMetadata() {

        boolean metadataFileCreation = true;

        File dataDir = getPlugin().getDataFolder();
        if (!dataDir.exists()) metadataFileCreation = dataDir.mkdir();

        File metadataDir = new File(dataDir + File.separator + "metadata");
        if (!metadataDir.exists()) metadataFileCreation = metadataDir.mkdir();

        if (metadataFileCreation) return metadataDir;

        return null;

    }

    private File getMetadata(Location location) {

        return new File(loadMetadata(),
                location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ() + ".yml");

    }

}
