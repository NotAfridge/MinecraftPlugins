package com.ullarah.umagic;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.ullarah.umagic.function.CommonString;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;

public class MagicFunctions {

    protected final String metaSand = "uMagic.sg";
    protected final String metaLamp = "uMagic.rl";
    protected final String metaWool = "uMagic.wl";
    protected final String metaEmBr = "uMagic.em";
    protected final String metaLadd = "uMagic.ld";
    protected final String metaRail = "uMagic.ra";
    protected final String metaSign = "uMagic.si";
    protected final String metaTrch = "uMagic.tc";
    protected final String metaBanr = "uMagic.bn";
    protected final String metaFram = "uMagic.if";
    protected final String metaVine = "uMagic.vn";
    protected final String metaFurn = "uMagic.fc";
    protected final String metaBeds = "uMagic.be";
    private final String world = "world";
    private final String locX = "loc.X";
    private final String locY = "loc.Y";
    private final String locZ = "loc.Z";
    private final String data = "data";
    private String furnaceFuel = "" + ChatColor.DARK_RED + ChatColor.ITALIC + ChatColor.GREEN + ChatColor.BOLD;
    private String furnaceSmelt = "" + ChatColor.BOLD + ChatColor.ITALIC + ChatColor.YELLOW;

    public MagicFunctions() {
        initMetadata();
    }

    private void initMetadata() {

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

            metaWorld.getNearbyEntities(location, 2.0, 2.0, 2.0).stream()
                    .filter(frame -> frame instanceof ItemFrame)
                    .filter(frame -> frame.getLocation().equals(location))
                    .forEach(frame -> frame.setMetadata(metadata, new FixedMetadataValue(MagicInit.getPlugin(), true)));

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

    protected void saveMetadata(Location location, String metadata) {

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

    protected void removeMetadata(Location location) {

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

    protected boolean checkBlock(Player player, Block block) {

        if (player.hasPermission("umagic.bypass")) return true;

        RegionManager regionManager = MagicInit.getWorldGuard().getRegionManager(block.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(block.getLocation());

        if (applicableRegionSet.getRegions().isEmpty()) return false;
        for (ProtectedRegion r : applicableRegionSet.getRegions())
            if (!r.isOwner(MagicInit.getWorldGuard().wrapPlayer(player))) return false;

        return true;

    }

    protected boolean checkMagicHoe(ItemStack item, String hoe) {

        if (item.getType() == Material.DIAMOND_HOE) if (item.hasItemMeta()) if (item.getItemMeta().hasDisplayName())
            if (item.getItemMeta().getDisplayName().equals(hoe)) return true;

        return false;

    }

    protected boolean checkHoeInteract(PlayerInteractEvent event, Player player, Block block) {

        if (!player.hasPermission("umagic.usage")) {
            event.setCancelled(true);
            return false;
        }

        if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
            event.setCancelled(true);
            return false;
        }

        if (event.getAction().equals(Action.PHYSICAL)) {
            if (new ArrayList<Material>() {{
                add(Material.STONE_PLATE);
                add(Material.WOOD_PLATE);
                add(Material.IRON_PLATE);
                add(Material.GOLD_PLATE);
            }}.contains(event.getClickedBlock().getType())) {
                event.setCancelled(true);
                return false;
            }
        }

        if (event.getAction() == Action.LEFT_CLICK_AIR
                || event.getAction() == Action.RIGHT_CLICK_AIR) {
            event.setCancelled(true);
            return false;
        }

        if (!checkBlock(player, block)) {
            event.setCancelled(true);
            return false;
        }

        return true;

    }

    protected void displayParticles(Player player, Block block) {

        double bX = block.getLocation().getX() + 0.5,
                bY = block.getLocation().getY() + 1,
                bZ = block.getLocation().getZ() + 0.5;

        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 15);
        block.getWorld().playSound(block.getLocation(), Sound.UI_BUTTON_CLICK, 0.75f, 0.75f);

    }

    void giveMagicHoe(Player player, ItemStack hoe) {

        if (player.hasPermission("umagic.gethoe")) {

            PlayerInventory playerInventory = player.getInventory();
            int firstEmpty = playerInventory.firstEmpty();

            if (firstEmpty >= 0) playerInventory.setItem(firstEmpty, hoe);
            else new CommonString().messageSend(player, "Your inventory is full.");

        }

    }

    public ItemStack getFurnaceFuel() {
        ItemStack fuel = new ItemStack(Material.COAL);
        ItemMeta meta = fuel.getItemMeta();
        meta.setDisplayName(this.furnaceFuel);
        fuel.setItemMeta(meta);
        return fuel;
    }

    public ItemStack getFurnaceSmelt() {
        ItemStack smelt = new ItemStack(Material.LOG);
        ItemMeta meta = smelt.getItemMeta();
        meta.setDisplayName(this.furnaceFuel);
        smelt.setItemMeta(meta);
        return smelt;
    }

}
