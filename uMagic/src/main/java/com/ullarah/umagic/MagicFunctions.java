package com.ullarah.umagic;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
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
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
    private final Material[] validMagicBlocks = new Material[]{
            Material.TRIPWIRE_HOOK, Material.HAY_BLOCK, Material.BED_BLOCK, Material.TRAP_DOOR, Material.IRON_TRAPDOOR,
            Material.SIGN, Material.SIGN_POST, Material.WALL_SIGN, Material.REDSTONE_LAMP_OFF, Material.ACACIA_STAIRS,
            Material.BIRCH_WOOD_STAIRS, Material.BRICK_STAIRS, Material.COBBLESTONE_STAIRS, Material.DARK_OAK_STAIRS,
            Material.JUNGLE_WOOD_STAIRS, Material.NETHER_BRICK_STAIRS, Material.PURPUR_STAIRS, Material.QUARTZ_STAIRS,
            Material.RED_SANDSTONE_STAIRS, Material.SANDSTONE_STAIRS, Material.SMOOTH_STAIRS, Material.SPRUCE_WOOD_STAIRS,
            Material.WOOD_STAIRS, Material.WOOL, Material.CARPET, Material.WOOD, Material.LADDER, Material.LOG,
            Material.LOG_2, Material.DOUBLE_STEP, Material.DOUBLE_STONE_SLAB2, Material.STONE_BUTTON,
            Material.WOOD_BUTTON, Material.GOLD_PLATE, Material.IRON_PLATE, Material.STONE_PLATE, Material.WOOD_PLATE,
            Material.HUGE_MUSHROOM_1, Material.HUGE_MUSHROOM_2, Material.FURNACE, Material.BURNING_FURNACE,
            Material.VINE, Material.BANNER, Material.STANDING_BANNER, Material.WALL_BANNER, Material.TORCH,
            Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON, Material.RAILS, Material.SAND, Material.GRAVEL,
            Material.EMERALD_BLOCK, Material.BEDROCK, Material.BARRIER
    };
    private final String furnaceFuel = "" + ChatColor.DARK_RED + ChatColor.ITALIC + ChatColor.GREEN + ChatColor.BOLD;
    private final String furnaceSmelt = "" + ChatColor.BOLD + ChatColor.ITALIC + ChatColor.YELLOW;
    private Plugin plugin;
    private WorldGuardPlugin worldGuard;
    private CommonString commonString;

    public MagicFunctions(Plugin plugin, WorldGuardPlugin worldGuard) {

        setPlugin(plugin);
        setWorldGuard(worldGuard);
        setCommonString(new CommonString(plugin));

        initMetadata();

    }

    public MagicFunctions() {

        super();

    }

    private Material[] getValidMagicBlocks() {
        return validMagicBlocks;
    }

    protected Plugin getPlugin() {
        return plugin;
    }

    private void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    private WorldGuardPlugin getWorldGuard() {
        return worldGuard;
    }

    private void setWorldGuard(WorldGuardPlugin worldGuard) {
        this.worldGuard = worldGuard;
    }

    protected CommonString getCommonString() {
        return commonString;
    }

    private void setCommonString(CommonString commonString) {
        this.commonString = commonString;
    }

    private void initMetadata() {

        for (File file : loadMetadata().listFiles()) {

            FileConfiguration metadataConfig = YamlConfiguration.loadConfiguration(file);

            World metaWorld = getPlugin().getServer().getWorld(metadataConfig.getString(world));

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
                    .forEach(frame -> frame.setMetadata(metadata, new FixedMetadataValue(getPlugin(), true)));

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
            getPlugin().getLogger().log(Level.SEVERE,
                    "Metadata file could not be deleted: " + metadataFile.toString());

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

    private boolean checkBlock(Player player, Block block) {

        if (player.hasPermission("umagic.bypass")) return true;

        RegionManager regionManager = getWorldGuard().getRegionManager(block.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(block.getLocation());

        if (applicableRegionSet.getRegions().isEmpty()) return false;
        for (ProtectedRegion r : applicableRegionSet.getRegions())
            if (!r.isOwner(getWorldGuard().wrapPlayer(player))) return false;

        return true;

    }

    protected boolean usingMagicHoe(Player player) {

        MagicRecipe recipe = new MagicRecipe();
        ItemStack inMainHand = player.getInventory().getItemInMainHand();

        if (inMainHand.getType() == Material.DIAMOND_HOE)
            if (inMainHand.hasItemMeta()) if (inMainHand.getItemMeta().hasDisplayName())
                if (inMainHand.getItemMeta().getDisplayName().equals(recipe.getHoeDisplayName())) return true;

        return false;

    }

    protected boolean checkHoeInteract(PlayerInteractEvent event, Player player, Block block) {

        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            event.setCancelled(true);
            return false;
        }

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

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR
                || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.PHYSICAL) {
            event.setCancelled(true);
            return false;
        }

        if (!checkBlock(player, block)) {
            event.setCancelled(true);
            return false;
        }

        if (Arrays.asList(getValidMagicBlocks()).contains(block.getType())) {

            ItemStack inMainHand = player.getInventory().getItemInMainHand();

            displayParticles(block);

            inMainHand.setDurability((short) (inMainHand.getDurability() + 25));

            if (inMainHand.getDurability() >= inMainHand.getType().getMaxDurability()) {
                player.getInventory().clear(player.getInventory().getHeldItemSlot());
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.75f, 0.75f);
            }

            return true;

        }

        event.setCancelled(true);
        return false;

    }

    private void displayParticles(Block block) {

        double bX = block.getLocation().getX() + 0.5,
                bY = block.getLocation().getY() + 0.5,
                bZ = block.getLocation().getZ() + 0.5;

        block.getWorld().spawnParticle(Particle.CRIT_MAGIC, bX, bY, bZ, 25);
        block.getWorld().playSound(block.getLocation(), Sound.UI_BUTTON_CLICK, 0.75f, 0.75f);

    }

    void giveMagicHoe(Player player, ItemStack hoe) {

        if (player.hasPermission("umagic.gethoe")) {

            PlayerInventory playerInventory = player.getInventory();
            int firstEmpty = playerInventory.firstEmpty();

            if (firstEmpty >= 0) playerInventory.setItem(firstEmpty, hoe);
            else getCommonString().messageSend(player, "Your inventory is full.");

        }

    }

    protected ItemStack getFurnaceFuel() {

        ItemStack fuel = new ItemStack(Material.COAL);
        ItemMeta meta = fuel.getItemMeta();

        meta.setDisplayName(this.furnaceFuel);
        fuel.setItemMeta(meta);

        return fuel;

    }

    protected ItemStack getFurnaceSmelt() {

        ItemStack smelt = new ItemStack(Material.LOG);
        ItemMeta meta = smelt.getItemMeta();

        meta.setDisplayName(this.furnaceSmelt);
        smelt.setItemMeta(meta);

        return smelt;

    }

}
