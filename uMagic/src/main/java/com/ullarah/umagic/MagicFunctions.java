package com.ullarah.umagic;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;

import static com.ullarah.umagic.MagicInit.getPlugin;

public class MagicFunctions {

    public void initMetadata() {

        File[] metadataFiles = loadMetadata().listFiles();

        for (File file : metadataFiles) {

            FileConfiguration metadataConfig = YamlConfiguration.loadConfiguration(file);

            World world = getPlugin().getServer().getWorld(metadataConfig.getString("world"));

            double lX = metadataConfig.getDouble("loc.X");
            double lY = metadataConfig.getDouble("loc.Y");
            double lZ = metadataConfig.getDouble("loc.Z");

            Location location = new Location(world, lX, lY, lZ);

            Chunk chunk = location.getChunk();
            if (!chunk.isLoaded()) chunk.load();

            String metadata = metadataConfig.getString("data");

            Block block = world.getBlockAt(location);

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

        File metadataFile = new File(loadMetadata(),
                location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ() + ".yml");

        if (!metadataFile.exists()) {

            try {
                metadataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        FileConfiguration metadataConfig = YamlConfiguration.loadConfiguration(metadataFile);

        metadataConfig.set("world", location.getWorld().getName());
        metadataConfig.set("loc.X", location.getX());
        metadataConfig.set("loc.Y", location.getY());
        metadataConfig.set("loc.Z", location.getZ());
        metadataConfig.set("data", metadata);

        try {
            metadataConfig.save(metadataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void removeMetadata(Location location) {

        File metadataFile = new File(loadMetadata(),
                location.getBlockX() + "." + location.getBlockY() + "." + location.getBlockZ() + ".yml");

        metadataFile.delete();

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

}
