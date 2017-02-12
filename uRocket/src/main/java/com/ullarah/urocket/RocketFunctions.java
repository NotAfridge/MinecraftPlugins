package com.ullarah.urocket;

import com.ullarah.urocket.function.*;
import com.ullarah.urocket.init.RocketEnhancement;
import com.ullarah.urocket.init.RocketLanguage;
import com.ullarah.urocket.init.RocketVariant.Variant;
import net.minecraft.server.v1_11_R1.EnumParticle;
import net.minecraft.server.v1_11_R1.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class RocketFunctions {

    private final CommonString commonString = new CommonString();
    private final TitleSubtitle titleSubtitle = new TitleSubtitle();
    private final GamemodeCheck gamemodeCheck = new GamemodeCheck();
    private final BlockStacks blockStacks = new BlockStacks();
    private final RomanNumeralToInteger romanNumeralToInteger = new RomanNumeralToInteger();

    public void disableRocketBoots(Player player, Boolean keepUsage, Boolean keepPower, Boolean keepFlight,
                                   Boolean keepVariant, Boolean keepEnhancement) {

        UUID playerUUID = player.getUniqueId();

        if (!keepUsage && RocketInit.rocketUsage.contains(playerUUID))
            RocketInit.rocketUsage.remove(playerUUID);

        if (RocketInit.rocketSprint.containsKey(playerUUID))
            RocketInit.rocketSprint.remove(playerUUID);

        if (!keepPower && RocketInit.rocketPower.containsKey(playerUUID))
            RocketInit.rocketPower.remove(playerUUID);

        if (!RocketInit.rocketFire.isEmpty())
            RocketInit.rocketFire.clear();

        if (RocketInit.rocketWater.contains(playerUUID))
            RocketInit.rocketWater.remove(playerUUID);

        if (RocketInit.rocketRepair.containsKey(playerUUID))
            RocketInit.rocketRepair.remove(playerUUID);

        if (!keepEnhancement && RocketInit.rocketEnhancement.containsKey(playerUUID))
            RocketInit.rocketEnhancement.remove(playerUUID);

        if (!keepVariant && RocketInit.rocketVariant.containsKey(playerUUID)) {

            switch (RocketInit.rocketVariant.get(playerUUID)) {

                case ENDER:
                    player.removePotionEffect(PotionEffectType.NIGHT_VISION);
                    player.removePotionEffect(PotionEffectType.HEALTH_BOOST);
                    break;

                case ZERO:
                    player.removePotionEffect(PotionEffectType.CONFUSION);
                    player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
                    player.removePotionEffect(PotionEffectType.INCREASE_DAMAGE);
                    break;

                case STEALTH:
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) onlinePlayer.showPlayer(player);
                    break;

                case DRUNK:
                    player.removePotionEffect(PotionEffectType.CONFUSION);
                    player.removePotionEffect(PotionEffectType.FAST_DIGGING);
                    break;

                case BOOST:
                    player.removePotionEffect(PotionEffectType.HEAL);
                    break;

                case RUNNER:
                    player.removePotionEffect(PotionEffectType.SPEED);
                    break;

            }

            RocketInit.rocketVariant.remove(playerUUID);

        }

        if (player.isOnline() && gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) onlinePlayer.showPlayer(player);

            player.setFlySpeed(0.1f);
            player.setFlying(false);
            if (!keepFlight) player.setAllowFlight(false);

            player.setNoDamageTicks(100);
            player.setFallDistance(0);

        }

    }

    public void interactRocketBoots(InventoryClickEvent event, ItemStack boots) {

        Player player = (Player) event.getWhoClicked();
        ClickType click = event.getClick();
        Boolean hasRocketMeta = boots.hasItemMeta();

        if (gamemodeCheck.check(player, GameMode.CREATIVE, GameMode.SPECTATOR)) {
            event.setCancelled(true);
            player.closeInventory();
            disableRocketBoots(player, false, false, false, false, false);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_GAMEMODE_ERROR);
            return;
        }

        if (hasRocketMeta) {

            ItemMeta rocketMeta = boots.getItemMeta();

            if (rocketMeta.hasDisplayName())
                if (rocketMeta.getDisplayName().matches(ChatColor.RED + "Rocket Boots")) if (rocketMeta.hasLore()) {

                    String rocketLore = rocketMeta.getLore().get(0);
                    String variantLore = null;

                    if (rocketMeta.getLore().size() >= 2)
                        variantLore = ChatColor.stripColor(rocketMeta.getLore().get(1));

                    Set<String> specialVariants = new HashSet<>(Collections.singletonList("Robin Hood"));

                    if (variantLore != null) if (specialVariants.contains(variantLore)) {

                        Variant variantType = Variant.getEnum(variantLore);

                        if (variantType != null)
                            if (variantType == Variant.MONEY) if (RocketInit.getVaultEconomy() == null) {
                                commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_EQUIP_ERROR);
                            return;
                        }
                    }

                    if (rocketLore.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?"))
                        if (!RocketInit.rocketUsage.contains(player.getUniqueId()))
                            if (click == ClickType.MIDDLE) event.setCancelled(true);
                            else attachRocketBoots(player, boots);

                }

        } else if (RocketInit.rocketSprint.containsKey(player.getUniqueId())) {

            commonString.messageSend(RocketInit.getPlugin(), player, true, new String[]{
                    RocketLanguage.RB_COOLDOWN_TOUCH, RocketLanguage.RB_COOLDOWN_LAND
            });

            event.setCancelled(true);
            player.closeInventory();

        } else if (RocketInit.rocketPower.containsKey(player.getUniqueId()))
            disableRocketBoots(player, false, false, false, false, false);

    }

    public void attachRocketBoots(Player player, ItemStack boots) {

        if (gamemodeCheck.check(player, GameMode.CREATIVE, GameMode.SPECTATOR)) {
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_GAMEMODE_ERROR);
            disableRocketBoots(player, false, false, false, false, false);
            return;
        }

        UUID playerUUID = player.getUniqueId();

        ItemMeta rocketMeta = boots.getItemMeta();
        Block blockMiddle = player.getLocation().getBlock().getRelative(BlockFace.SELF);

        Boolean isWaterVariant = false;
        Boolean isRunnerVariant = false;

        String[] rocketMessage = new String[3];
        rocketMessage[0] = RocketLanguage.RB_ACTIVATE;

        switch (rocketMeta.getLore().size()) {

            case 1:
                rocketMessage[1] = RocketLanguage.RB_VARIANT + RocketLanguage.RB_NOT_FOUND;
                rocketMessage[2] = RocketLanguage.RB_ENHANCE + RocketLanguage.RB_NOT_FOUND;
                RocketInit.rocketVariant.put(playerUUID, Variant.ORIGINAL);
                RocketInit.rocketEnhancement.put(playerUUID, RocketEnhancement.Enhancement.NOTHING);
                break;

            case 2:
                String loreLine = ChatColor.stripColor(rocketMeta.getLore().get(1));

                if (Variant.isVariant(loreLine)) {
                    Variant variantType = Variant.getEnum(loreLine);
                    if (variantType != null) {
                        rocketMessage[1] = RocketLanguage.RB_VARIANT + loreLine;
                        rocketMessage[2] = RocketLanguage.RB_ENHANCE + RocketLanguage.RB_NOT_FOUND;

                        RocketInit.rocketVariant.put(playerUUID, variantType);
                        RocketInit.rocketEnhancement.put(playerUUID, RocketEnhancement.Enhancement.NOTHING);

                        if (variantType.equals(Variant.WATER)) isWaterVariant = true;
                        if (variantType.equals(Variant.RUNNER)) isRunnerVariant = true;
                    }
                }

                if (RocketEnhancement.Enhancement.isEnhancement(loreLine)) {
                    RocketEnhancement.Enhancement enhancementType = RocketEnhancement.Enhancement.getEnum(loreLine);
                    if (enhancementType != null) {
                        rocketMessage[1] = RocketLanguage.RB_VARIANT + RocketLanguage.RB_NOT_FOUND;
                        rocketMessage[2] = RocketLanguage.RB_ENHANCE + loreLine;

                        RocketInit.rocketVariant.put(playerUUID, Variant.ORIGINAL);
                        RocketInit.rocketEnhancement.put(playerUUID, enhancementType);
                    }
                }
                break;

            case 3:
                String variantLore = ChatColor.stripColor(rocketMeta.getLore().get(1));
                String enhancementLore = ChatColor.stripColor(rocketMeta.getLore().get(2));

                Variant variantType = Variant.getEnum(variantLore);
                RocketEnhancement.Enhancement enhancementType = RocketEnhancement.Enhancement.getEnum(enhancementLore);

                if (variantType != null && enhancementType != null) {
                    rocketMessage[1] = RocketLanguage.RB_VARIANT + variantLore;
                    RocketInit.rocketVariant.put(playerUUID, variantType);

                    if (variantType.equals(Variant.WATER)) isWaterVariant = true;
                    if (variantType.equals(Variant.RUNNER)) isRunnerVariant = true;

                    rocketMessage[2] = RocketLanguage.RB_ENHANCE + enhancementLore;
                    RocketInit.rocketEnhancement.put(playerUUID, enhancementType);
                }
                break;

        }

        if (!isWaterVariant && blockMiddle.isLiquid()) {
            RocketInit.rocketWater.add(playerUUID);
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_WATER_WARNING);
            return;
        }

        if (RocketInit.rocketVariant.get(playerUUID) == null || RocketInit.rocketEnhancement.get(playerUUID) == null) {
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_FAIL_ATTACH);
            disableRocketBoots(player, false, false, false, false, false);
            return;
        }

        if (RocketInit.rocketVariant.get(playerUUID) != Variant.ORIGINAL && player.getWorld().getName().equals("world_nether")) {
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_NETHER);
            disableRocketBoots(player, false, false, false, false, false);
            return;
        }

        if (player.getInventory().getBoots() == null)
            if (gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

                commonString.messageSend(RocketInit.getPlugin(), player, true, rocketMessage);
                if (!isRunnerVariant) player.setAllowFlight(true);
                RocketInit.rocketPower.put(playerUUID, getBootPowerLevel(boots));

            }

    }

    public File getFuelFile(Player player) {

        return new File(RocketInit.getPlugin().getDataFolder() + File.separator + "fuel", player.getUniqueId().toString() + ".yml");

    }

    private FileConfiguration getFuelConfig(Player player) {

        File fuelFile = getFuelFile(player);
        FileConfiguration fuelConfig = YamlConfiguration.loadConfiguration(fuelFile);

        if (fuelFile.exists()) return fuelConfig;

        return null;

    }

    private Inventory getFuelInventory(Player player) {

        if (isValidFuelJacket(player.getInventory().getChestplate())) {

            FileConfiguration fuelConfig = getFuelConfig(player);
            Material jacket = player.getInventory().getChestplate().getType();

            int jacketSize = getFuelJacketSize(jacket);
            String jacketType = getFuelJacketConfigString(jacket);

            if (fuelConfig.get(jacketType) != null) {

                Inventory fuelInventory;
                ArrayList<ItemStack> itemStack = new ArrayList<>();

                itemStack.addAll(fuelConfig.getList(jacketType).stream().map(fuelCurrentItem
                        -> (ItemStack) fuelCurrentItem).collect(Collectors.toList()));

                fuelInventory = Bukkit.createInventory(player, jacketSize, "" + ChatColor.DARK_RED + ChatColor.BOLD + "Rocket Boot Fuel Jacket");
                fuelInventory.setContents(itemStack.toArray(new ItemStack[itemStack.size()]));

                return fuelInventory;

            }

        }

        return null;

    }

    public boolean fuelCheck(Player player, Material single, Material block) {

        Inventory fuelInventory = getFuelInventory(player);
        ItemStack rocketBoots = player.getInventory().getBoots();

        int fuelCost = 0;

        switch (rocketBoots.getType()) {

            case LEATHER_BOOTS:
                fuelCost = 1 + getBootPowerLevel(rocketBoots);
                break;

            case IRON_BOOTS:
                fuelCost = 2 + getBootPowerLevel(rocketBoots);
                break;

            case GOLD_BOOTS:
                fuelCost = 3 + getBootPowerLevel(rocketBoots);
                break;

            case DIAMOND_BOOTS:
                fuelCost = 4 + getBootPowerLevel(rocketBoots);
                break;

        }

        if (fuelInventory.containsAtLeast(new ItemStack(block), fuelCost)) return true;
        if (fuelInventory.containsAtLeast(new ItemStack(single), fuelCost)) return true;

        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.FuelRequired(single.name().toLowerCase()));
        RocketInit.rocketTimeout.add(player.getUniqueId());

        new BukkitRunnable() {
            int c = 5;

            @Override
            public void run() {
                if (c <= 0) {
                    RocketInit.rocketTimeout.remove(player.getUniqueId());
                    this.cancel();
                    return;
                }
                player.setFlying(false);
                c--;
            }

        }.runTaskTimer(RocketInit.getPlugin(), 0, 20);

        return false;

    }

    public void fuelRemove(Player player, Material block, Material single, int cost) {

        Inventory fuelInventory = getFuelInventory(player);

        for (ItemStack item : fuelInventory.getContents())
            if (item != null) if (item.getAmount() <= 0) item.setType(Material.AIR);

        if (!blockStacks.split(fuelInventory, block, single, cost, (9 - cost))) {
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.FuelOutage(single.toString().toLowerCase()));
            disableRocketBoots(player, true, true, true, true, true);
        }

        try {

            Material jacketType = player.getInventory().getChestplate().getType();
            FileConfiguration fuelConfig = getFuelConfig(player);

            fuelConfig.set(getFuelJacketConfigString(jacketType), fuelInventory.getContents());
            fuelConfig.save(getFuelFile(player));

        } catch (IOException e) {
            commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_JACKET_SAVE_ERROR);
            e.printStackTrace();
        }

    }

    public boolean isValidFuelJacket(ItemStack jacket) {

        if (jacket.hasItemMeta()) {
            ItemMeta jacketMeta = jacket.getItemMeta();
            if (jacketMeta.hasDisplayName())
                if (jacketMeta.getDisplayName().equals(ChatColor.RED + "Rocket Boot Fuel Jacket")) return true;

        }

        return false;

    }

    public String getFuelJacketConfigString(Material type) {

        switch (type) {

            case LEATHER_CHESTPLATE:
                return "leather";

            case IRON_CHESTPLATE:
                return "iron";

            case GOLD_CHESTPLATE:
                return "gold";

            case DIAMOND_CHESTPLATE:
                return "diamond";

        }

        return null;

    }

    public int getFuelJacketSize(Material type) {

        switch (type) {

            case LEATHER_CHESTPLATE:
                return 9;

            case IRON_CHESTPLATE:
                return 18;

            case GOLD_CHESTPLATE:
                return 27;

            case DIAMOND_CHESTPLATE:
                return 36;

        }

        return 0;

    }

    public boolean isValidRocketBoots(ItemStack boots) {

        if (boots.hasItemMeta()) {
            ItemMeta bootMeta = boots.getItemMeta();

            if (bootMeta.hasDisplayName())
                if (bootMeta.getDisplayName().matches(RocketLanguage.RB_NAME))
                    if (bootMeta.hasLore())
                        if (bootMeta.getLore().get(0).matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?"))
                            return true;
        }

        return false;

    }

    public int getBootPowerLevel(ItemStack boots) {

        return romanNumeralToInteger.decode(boots.getItemMeta().getLore().get(0).replaceFirst(RocketLanguage.RB_LEVEL, ""));

    }

    public int getBootRepairRate(ItemStack boots) {

        boolean fastRepair = false;

        if (isValidRocketBoots(boots)) if (boots.getItemMeta().getLore().size() == 3) {
            String enhancementLore = boots.getItemMeta().getLore().get(2);
            RocketEnhancement.Enhancement enhancement = RocketEnhancement.Enhancement.getEnum(enhancementLore);
            if (enhancement != null) if (enhancement.equals(RocketEnhancement.Enhancement.FASTREP)) fastRepair = true;
        }

        switch (getBootPowerLevel(boots)) {

            case 1:
                return fastRepair ? 20 : 5;

            case 2:
                return fastRepair ? 17 : 4;

            case 3:
                return fastRepair ? 15 : 3;

            case 4:
                return fastRepair ? 13 : 2;

            case 5:
                return fastRepair ? 10 : 1;

        }

        return 0;

    }

    public int getBootRepairRate(Material boots) {

        switch (boots) {

            case LEATHER_BOOTS:
                return 5;

            case IRON_BOOTS:
                return 4;

            case GOLD_BOOTS:
                return 3;

            case DIAMOND_BOOTS:
                return 2;

        }

        return 0;

    }

    public int getBootDurability(ItemStack boots) {

        switch (boots.getType()) {

            case LEATHER_BOOTS:
                return 65;

            case IRON_BOOTS:
                return 195;

            case GOLD_BOOTS:
                return 91;

            case DIAMOND_BOOTS:
                return 429;

        }

        return 0;

    }

    public void changeBootDurability(Player player, ItemStack boots) {

        Short rocketDurability = boots.getDurability();

        int bootMaterialDurability = getBootDurability(boots);
        short changedDurability = 0;

        switch (getBootPowerLevel(boots)) {

            case 1:
                changedDurability = (short) (rocketDurability + 7);
                break;

            case 2:
                changedDurability = (short) (rocketDurability + 6);
                break;

            case 3:
                changedDurability = (short) (rocketDurability + 5);
                break;

            case 4:
                changedDurability = (short) (rocketDurability + 4);
                break;

            case 5:
                changedDurability = (short) (rocketDurability + 3);
                break;

            case 10:
                changedDurability = (short) (rocketDurability + new Random().nextInt(10));
                break;

        }

        boots.setDurability(changedDurability);

        int newBootDurability = bootMaterialDurability - changedDurability;

        if (newBootDurability < 0) {

            boots.setDurability((short) bootMaterialDurability);
            newBootDurability = 0;

        }

        String totalDurability = RocketLanguage.RB_DURABILITY + newBootDurability + " / " + bootMaterialDurability;

        commonString.messageSend(RocketInit.getPlugin(), player, true, new String[]{totalDurability});
        titleSubtitle.subtitle(player, 2, ChatColor.YELLOW + totalDurability);

    }

    public void reloadFlyZones(boolean showMessage) {

        RocketInit.rocketZoneLocations.clear();
        List<String> zoneList = RocketInit.getPlugin().getConfig().getStringList("zones");

        if (zoneList.size() > 0) {

            for (String zone : zoneList) {

                String[] zoneSection = zone.split("\\|");

                final Location zoneLocationStart = new Location(Bukkit.getWorld(zoneSection[1]),
                        Integer.parseInt(zoneSection[2]) - 25,
                        Integer.parseInt(zoneSection[3]) - 5,
                        Integer.parseInt(zoneSection[4]) - 25);

                final Location zoneLocationEnd = new Location(Bukkit.getWorld(zoneSection[1]),
                        Integer.parseInt(zoneSection[2]) + 25,
                        Integer.parseInt(zoneSection[3]) + 50,
                        Integer.parseInt(zoneSection[4]) + 25);

                RocketInit.rocketZoneLocations.put(UUID.fromString(zoneSection[0]), new ConcurrentHashMap<Location, Location>() {{
                    put(zoneLocationStart, zoneLocationEnd);
                }});

            }

            if (showMessage) RocketInit.registerMap.put("zone", zoneList.size());

        }

    }

    public void zoneCrystalCreation(Player player, Location blockLocation) {

        World world = player.getWorld();

        Location centerBlock = new CenterBlock().variable(player, blockLocation, 0.475);

        centerBlock.getBlock().setType(Material.AIR);
        world.spawn(centerBlock, EnderCrystal.class);

        int cBX = centerBlock.getBlockX();
        int cBY = centerBlock.getBlockY();
        int cBZ = centerBlock.getBlockZ();

        List<String> zoneList = RocketInit.getPlugin().getConfig().getStringList("zones");
        String activeZone = player.getUniqueId().toString() + "|" + world.getName() + "|" + cBX + "|" + cBY + "|" + cBZ;
        zoneList.add(activeZone);

        RocketInit.getPlugin().getConfig().set("zones", zoneList);
        RocketInit.getPlugin().saveConfig();

        reloadFlyZones(false);

        Location particleLocation = new Location(world, cBX + 0.5, cBY + 1.2, cBZ + 0.5);

        world.playSound(centerBlock, Sound.ENTITY_WITHER_AMBIENT, 1.25f, 0.55f);

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                EnumParticle.PORTAL, false,
                particleLocation.getBlockX(), particleLocation.getBlockY(), particleLocation.getBlockZ(),
                0.0f, 0.0f, 0.0f, 2, 2500, null);

        for (Player serverPlayer : player.getWorld().getPlayers())
            ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);

        commonString.messageSend(RocketInit.getPlugin(), player, true, RocketLanguage.RB_FZ_SUCCESS);

    }

    public boolean isValidRocketSaddle(ItemStack saddle) {

        if (saddle != null && saddle.hasItemMeta()) {
            ItemMeta saddleMeta = saddle.getItemMeta();
            if (saddleMeta.hasDisplayName())
                if (saddleMeta.getDisplayName().equals(RocketLanguage.RB_SADDLE)) return true;
        }

        return false;

    }

}
