package com.ullarah.umagic;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import com.ullarah.umagic.block.*;
import com.ullarah.umagic.database.SQLConnection;
import com.ullarah.umagic.database.SQLMessage;
import com.ullarah.umagic.function.ActionMessage;
import com.ullarah.umagic.function.CommonString;
import com.ullarah.umagic.recipe.MagicHoeCosmic;
import com.ullarah.umagic.recipe.MagicHoeNormal;
import com.ullarah.umagic.recipe.MagicHoeSuper;
import com.ullarah.umagic.recipe.MagicHoeUber;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.libs.org.apache.commons.lang3.StringUtils;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

public class MagicFunctions {

    protected final String
            metaSand = "uMagic.sg", metaLamp = "uMagic.rl", metaWool = "uMagic.wl", metaLava = "uMagic.lv",
            metaEmBr = "uMagic.em", metaLadd = "uMagic.ld", metaRail = "uMagic.ra", metaSign = "uMagic.si",
            metaTrch = "uMagic.tc", metaBanr = "uMagic.bn", metaFram = "uMagic.if", metaVine = "uMagic.vn",
            metaFurn = "uMagic.fc", metaBeds = "uMagic.be", metaFire = "uMagic.fi", metaSnow = "uMagic.sw",
            metaVoid = "uMagic.vd", metaCact = "uMagic.cs", metaCice = "uMagic.ci", metaWate = "uMagic.wa",
            metaReds = "uMagic.rd";

    private static final HashMap<Material, BaseBlock> blockRegister = new HashMap<>();

    private final Material[] pressurePlates = new Material[] {
            Material.ACACIA_PRESSURE_PLATE, Material.BIRCH_PRESSURE_PLATE, Material.DARK_OAK_PRESSURE_PLATE,
            Material.JUNGLE_PRESSURE_PLATE, Material.OAK_PRESSURE_PLATE, Material.SPRUCE_PRESSURE_PLATE,
            Material.STONE_PRESSURE_PLATE, Material.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.HEAVY_WEIGHTED_PRESSURE_PLATE
    };

    private final String furnaceFuel = "" + ChatColor.DARK_RED + ChatColor.ITALIC + ChatColor.GREEN + ChatColor.BOLD,
            furnaceSmelt = "" + ChatColor.BOLD + ChatColor.ITALIC + ChatColor.YELLOW;
    private final String database = MagicInit.getDatabaseName();
    private Plugin plugin;
    private WorldEditPlugin worldEdit;
    private WorldGuardPlugin worldGuard;
    private CommonString commonString;
    private ActionMessage actionMessage;
    private SQLConnection sqlConnection;
    private SQLMessage sqlMessage;

    public MagicFunctions(boolean doInit) {

        setPlugin(MagicInit.getPlugin());
        setWorldEdit(MagicInit.getWorldEdit());
        setWorldGuard(MagicInit.getWorldGuard());
        setSqlConnection(MagicInit.getSqlConnection());

        setSqlMessage(new SQLMessage());
        setCommonString(new CommonString(getPlugin()));
        setActionMessage(new ActionMessage());

        if (doInit) {
            initMetadata();

            if (blockRegister.isEmpty()) {
                addToRegister(
                        new Banner(), new BannerWall(), new Barrier(), new Bed(),
                        new Bedrock(), new Button(), new Cactus(), new Carpet(),
                        new Emerald(), new Furnace(), new Ice(), new Ladder(),
                        new Lamp(), new Lapis(), new Magma(), new Melon(),
                        new Mushroom(), new Netherrack(), new Obsidian(), new PackedIce(),
                        new Plate(), new Rails(), new Redstone(), new Sand(),
                        new Sign(), new SignWall(), new Snow(), new Spawner(),
                        new Stairs(), new StructureBlock(), new StructureVoid(), new Terracotta(),
                        new Torch(), new Trapdoor(), new Triphook(), new Vines(),
                        new Wood(), new Wool());
            }
        }
    }

    protected Plugin getPlugin() {
        return plugin;
    }

    private void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    protected WorldEditPlugin getWorldEdit() {
        return worldEdit;
    }

    private void setWorldEdit(WorldEditPlugin worldEdit) {
        this.worldEdit = worldEdit;
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

    protected ActionMessage getActionMessage() {
        return actionMessage;
    }

    private void setActionMessage(ActionMessage actionMessage) {
        this.actionMessage = actionMessage;
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

            String statement = "SELECT data FROM " + database + " WHERE "
                    + StringUtils.join(new String[]{
                            "world='" + location.getWorld().getName() + "'",
                            "locX=" + location.getBlockX(),
                            "locY=" + location.getBlockY(),
                            "locZ=" + location.getBlockZ()},
                            " AND ");
            ResultSet result = getSqlConnection().getResult(statement);
            if (result != null && result.next())
                removeMetadata(location);

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

        String statement = "DELETE FROM " + database + " WHERE "
                + StringUtils.join(new String[]{
                        "world='" + location.getWorld().getName() + "'",
                        "locX=" + location.getBlockX(),
                        "locY=" + location.getBlockY(),
                        "locZ=" + location.getBlockZ()},
                " AND ");
        getSqlConnection().runStatement(statement);

    }

    private boolean checkBlock(Player player, Block block) {

        if (player.hasPermission("umagic.bypass")) return true;

        RegionQuery regionQuery = WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();
        ApplicableRegionSet applicableRegionSet = regionQuery.getApplicableRegions(getWorldEditLocation(block.getLocation()));

        if (applicableRegionSet.getRegions().isEmpty()) {
            getActionMessage().message(player, "" + ChatColor.RED + ChatColor.BOLD
                    + "The " + ChatColor.AQUA + ChatColor.BOLD + "Magical Hoe"
                    + ChatColor.RED + ChatColor.BOLD + " can only be used in regions!");
            return false;
        }

        for (ProtectedRegion region : applicableRegionSet.getRegions()) {

            boolean isOwner = region.isOwner(getWorldGuardPlayer(player));
            boolean isMember = region.isMember(getWorldGuardPlayer(player));

            if (!isOwner) if (!isMember) {
                getActionMessage().message(player, "" + ChatColor.RED + ChatColor.BOLD
                        + "You are not an owner or member of this region!");
                return false;
            }

        }

        return true;

    }

    private LocalPlayer getWorldGuardPlayer(Player player) {
        return getWorldGuard().wrapPlayer(player);
    }

    private com.sk89q.worldedit.util.Location getWorldEditLocation(Location location) {
        return BukkitAdapter.adapt(location);
    }

    protected boolean usingMagicHoe(Player player) {

        MagicHoeNormal recipe = new MagicHoeNormal();
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
            if (Arrays.asList(pressurePlates).contains(event.getClickedBlock().getType())) {
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

        if (blockRegister.containsKey(block.getType())) {

            ItemStack inMainHand = player.getInventory().getItemInMainHand();

            int hoeType = 0;

            if (inMainHand.hasItemMeta()) if (inMainHand.getItemMeta().hasLore()) {

                String loreLine = inMainHand.getItemMeta().getLore().get(0);

                if (loreLine.equals(new MagicHoeSuper().getHoeTypeLore())) hoeType = 1;
                if (loreLine.equals(new MagicHoeUber().getHoeTypeLore())) hoeType = 2;
                if (loreLine.equals(new MagicHoeCosmic().getHoeTypeLore())) hoeType = 3;

            }

            displayParticles(block, hoeType);

            switch (hoeType) {

                case 0:
                    inMainHand.setDurability((short) (inMainHand.getDurability() + 75));
                    break;

                case 1:
                    inMainHand.setDurability((short) (inMainHand.getDurability() + 15));
                    break;

                case 2:
                    inMainHand.setDurability((short) (inMainHand.getDurability() + 1));
                    break;

                case 3:
                    inMainHand.setDurability((short) (inMainHand.getDurability() - 1));
                    break;

            }

            if (inMainHand.getDurability() >= inMainHand.getType().getMaxDurability()) {

                player.getInventory().clear(player.getInventory().getHeldItemSlot());
                player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 0.75f, 0.75f);

            }

            return true;

        }

        event.setCancelled(true);
        return false;

    }

    private void displayParticles(Block block, int hoeType) {

        double bX = block.getLocation().getX() + 0.5,
                bY = block.getLocation().getY() + 0.5,
                bZ = block.getLocation().getZ() + 0.5;

        Particle particle;
        switch (hoeType) {
            default:
            case 0:
                particle = Particle.CRIT_MAGIC;
                break;
            case 1:
                particle = Particle.SPELL_WITCH;
                break;
            case 2:
                particle = Particle.VILLAGER_ANGRY;
                break;
            case 3:
                particle = Particle.DRAGON_BREATH;
                break;
        }

        block.getWorld().spawnParticle(particle, bX, bY, bZ, 30);
        block.getWorld().playSound(block.getLocation(), Sound.ENTITY_SHULKER_TELEPORT, 0.5f, 0.5f);

    }

    void giveMagicHoe(Player player, ItemStack hoe) {

        if (player.hasPermission("umagic.gethoe")) {

            PlayerInventory playerInventory = player.getInventory();
            int firstEmpty = playerInventory.firstEmpty();

            if (firstEmpty >= 0) {
                player.playSound(player.getLocation(), Sound.BLOCK_LAVA_POP, 0.75f, 0.75f);
                playerInventory.setItem(firstEmpty, hoe);
            } else getActionMessage().message(player, "" + ChatColor.AQUA + ChatColor.BOLD
                    + "Your inventory is full!");

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

        ItemStack smelt = new ItemStack(Material.OAK_LOG);
        ItemMeta meta = smelt.getItemMeta();

        meta.setDisplayName(this.furnaceSmelt);
        smelt.setItemMeta(meta);

        return smelt;

    }

    protected BaseBlock getBlock(Material material) {
        return blockRegister.get(material);
    }

    private void addToRegister(BaseBlock... blocks) {
        for (BaseBlock block : blocks)
            for (Material material : block.getPermittedBlocks())
                blockRegister.put(material, block);
    }

}
