package com.ullarah.umagic;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class MagicFunctions {

    public final String metaSand = "uMagic.sg";
    public final String metaLamp = "uMagic.rl";
    public final String metaWool = "uMagic.wl";
    public final String metaEmBr = "uMagic.ch";
    public final String metaLadd = "uMagic.ld";
    public final String metaRail = "uMagic.ra";

    private final String world = "world";
    private final String locX = "loc.X";
    private final String locY = "loc.Y";
    private final String locZ = "loc.Z";
    private final String data = "data";

    public void initMetadata() {

        for (File file : loadMetadata().listFiles()) {

            FileConfiguration metadataConfig = YamlConfiguration.loadConfiguration(file);

            World metaWorld = MagicInit.getPlugin().getServer().getWorld(metadataConfig.getString(world));

            double lX = metadataConfig.getDouble(locX),
                    lY = metadataConfig.getDouble(locY),
                    lZ = metadataConfig.getDouble(locZ);

            Location location = new Location(metaWorld, lX, lY, lZ);

            Chunk chunk = location.getChunk();
            if (!chunk.isLoaded()) chunk.load();

            String metadata = metadataConfig.getString(data);

            Block block = metaWorld.getBlockAt(location);

            block.setMetadata(metadata, new FixedMetadataValue(MagicInit.getPlugin(), true));

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
            MagicInit.getPlugin().getLogger().log(Level.SEVERE,
                    "Metadata file could not be deleted: " + metadataFile.toString());

    }

    private File loadMetadata() {

        boolean metadataFileCreation = true;

        File dataDir = MagicInit.getPlugin().getDataFolder();
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

    public boolean checkBlock(Player player, Block block) {

        if (player.hasPermission("magic.bypass")) return true;

        RegionManager regionManager = MagicInit.getWorldGuard().getRegionManager(block.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(block.getLocation());

        if (applicableRegionSet.getRegions().isEmpty()) return false;
        for (ProtectedRegion r : applicableRegionSet.getRegions())
            if (!r.isOwner(MagicInit.getWorldGuard().wrapPlayer(player))) return false;

        return true;

    }

    public boolean checkMagicHoe(ItemStack item) {

        if (item.getType() == Material.DIAMOND_HOE) {

            if (item.hasItemMeta()) {

                if (item.getItemMeta().hasDisplayName()) {

                    if (item.getItemMeta().getDisplayName().equals(MagicRecipe.hoeName)) {

                        return true;

                    }

                }

            }

        }

        return false;

    }

}
