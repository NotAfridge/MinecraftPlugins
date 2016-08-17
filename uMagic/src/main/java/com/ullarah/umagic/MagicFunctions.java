package com.ullarah.umagic;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.ullarah.umagic.database.SQLConnection;
import com.ullarah.umagic.database.SQLMessage;
import com.ullarah.umagic.function.CommonString;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;

public class MagicFunctions {

    protected final String metaSand = "uMagic.sg", metaLamp = "uMagic.rl", metaWool = "uMagic.wl",
            metaEmBr = "uMagic.em", metaLadd = "uMagic.ld", metaRail = "uMagic.ra", metaSign = "uMagic.si",
            metaTrch = "uMagic.tc", metaBanr = "uMagic.bn", metaFram = "uMagic.if", metaVine = "uMagic.vn",
            metaFurn = "uMagic.fc", metaBeds = "uMagic.be", metaFire = "uMagic.fi";

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
            Material.EMERALD_BLOCK, Material.BEDROCK, Material.BARRIER, Material.NETHERRACK
    };

    private final String furnaceFuel = "" + ChatColor.DARK_RED + ChatColor.ITALIC + ChatColor.GREEN + ChatColor.BOLD,
            furnaceSmelt = "" + ChatColor.BOLD + ChatColor.ITALIC + ChatColor.YELLOW;
    private final String database = MagicInit.getDatabaseName();
    private Plugin plugin;
    private WorldGuardPlugin worldGuard;
    private CommonString commonString;
    private SQLConnection sqlConnection;
    private SQLMessage sqlMessage;

    public MagicFunctions() {

        setPlugin(MagicInit.getPlugin());
        setWorldGuard(MagicInit.getWorldGuard());
        setSqlConnection(MagicInit.getSqlConnection());

        setSqlMessage(new SQLMessage());
        setCommonString(new CommonString(getPlugin()));

    }

    public MagicFunctions(boolean doInit) {

        setPlugin(MagicInit.getPlugin());
        setWorldGuard(MagicInit.getWorldGuard());
        setSqlConnection(MagicInit.getSqlConnection());

        setSqlMessage(new SQLMessage());
        setCommonString(new CommonString(getPlugin()));

        if (doInit) {

            initMetadata();

        }

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

    private SQLConnection getSqlConnection() {
        return sqlConnection;
    }

    private void setSqlConnection(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;
    }

    private SQLMessage getSqlMessage() {
        return sqlMessage;
    }

    private void setSqlMessage(SQLMessage sqlMessage) {
        this.sqlMessage = sqlMessage;
    }

    protected CommonString getCommonString() {
        return commonString;
    }

    private void setCommonString(CommonString commonString) {
        this.commonString = commonString;
    }

    private void initMetadata() {

        try {

            ResultSet resultSet = getSqlConnection().getResult("SELECT * FROM " + database);

            while (resultSet.next()) {

                String data = resultSet.getString("data");
                World world = getPlugin().getServer().getWorld(resultSet.getString("world"));

                Double x = Double.parseDouble(resultSet.getString("locX")),
                        y = Double.parseDouble(resultSet.getString("locY")),
                        z = Double.parseDouble(resultSet.getString("locZ"));

                Location location = new Location(world, x, y, z);

                Chunk chunk = location.getChunk();
                if (!chunk.isLoaded()) chunk.load();

                for (Entity entity : world.getNearbyEntities(location, 3.0, 3.0, 3.0)) {
                    if (entity instanceof ItemFrame) {
                        World eW = entity.getLocation().getWorld();
                        Double eX = (double) entity.getLocation().getBlockX(),
                                eY = (double) entity.getLocation().getBlockY(),
                                eZ = (double) entity.getLocation().getBlockZ();
                        if (new Location(eW, eX, eY, eZ).equals(location))
                            entity.setMetadata(data, new FixedMetadataValue(getPlugin(), true));
                    }
                }

                world.getBlockAt(location).setMetadata(data, new FixedMetadataValue(getPlugin(), true));

            }

        } catch (SQLException e) {

            getPlugin().getLogger().log(Level.SEVERE, getSqlMessage().sqlConnectionFailure(), e);

        } finally {

            getSqlConnection().closeSQLConnection();

        }

    }

    protected void saveMetadata(Location location, String metadata) {

        try {

            if (getSqlConnection().getResult("SELECT data FROM " + database + " WHERE "
                    + StringUtils.join(new String[]{"world = " + location.getWorld().getName(),
                    "locX = " + String.valueOf(location.getBlockX()), "locY = " + String.valueOf(location.getBlockY()),
                    "locZ = " + String.valueOf(location.getBlockZ())}, " AND ")).next()) removeMetadata(location);

        } catch (SQLException e) {

            getPlugin().getLogger().log(Level.SEVERE, getSqlMessage().sqlConnectionFailure(), e);

        } finally {

            getSqlConnection().closeSQLConnection();

        }

        getSqlConnection().runStatement("INSERT INTO " + database + " VALUES ("
                + StringUtils.join(new String[]{"NULL", "'" + metadata + "'", "'" + location.getWorld().getName()
                + "'", String.valueOf(location.getBlockX()), String.valueOf(location.getBlockY()),
                String.valueOf(location.getBlockZ())}, ",") + ");");

    }

    protected void removeMetadata(Location location) {

        getSqlConnection().runStatement("DELETE FROM " + database + " WHERE "
                + StringUtils.join(new String[]{"world = " + location.getWorld().getName(),
                "locX = " + String.valueOf(location.getBlockX()), "locY = " + String.valueOf(location.getBlockY()),
                "locZ = " + String.valueOf(location.getBlockZ())}, " AND "));

    }

    private boolean checkBlock(Player player, Block block) {

        if (player.hasPermission("umagic.bypass")) return true;

        RegionManager regionManager = getWorldGuard().getRegionManager(block.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(block.getLocation());

        if (applicableRegionSet.getRegions().isEmpty()) return false;

        for (ProtectedRegion region : applicableRegionSet.getRegions()) {

            boolean isOwner = region.isOwner(getWorldGuard().wrapPlayer(player));
            boolean isMember = region.isMember(getWorldGuard().wrapPlayer(player));

            if (!isOwner) if (!isMember) return false;

        }

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
