package com.ullarah.urocket;

import com.ullarah.ulib.function.*;
import com.ullarah.urocket.RocketVariant.Variant;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.ullarah.ulib.function.CommonString.messageSend;
import static com.ullarah.urocket.RocketEnhancement.Enhancement;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketLanguage.*;

public class RocketFunctions {

    public static void disableRocketBoots(Player player, Boolean keepUsage, Boolean keepPower, Boolean keepFlight,
                                          Boolean keepVariant, Boolean keepEnhancement, Boolean disableMessage) {

        UUID playerUUID = player.getUniqueId();

        if (!keepUsage && rocketUsage.contains(playerUUID)) rocketUsage.remove(playerUUID);
        if (rocketSprint.containsKey(playerUUID)) rocketSprint.remove(playerUUID);
        if (!keepPower && rocketPower.containsKey(playerUUID)) rocketPower.remove(playerUUID);

        if (!rocketFire.isEmpty()) rocketFire.clear();
        if (rocketWater.contains(playerUUID)) rocketWater.remove(playerUUID);
        if (rocketRepair.containsKey(playerUUID)) rocketRepair.remove(playerUUID);

        if (!keepEnhancement && rocketEnhancement.containsKey(playerUUID)) rocketEnhancement.remove(playerUUID);

        if (!keepVariant && rocketVariant.containsKey(playerUUID)) {
            switch (rocketVariant.get(playerUUID)) {
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
            rocketVariant.remove(playerUUID);
        }

        if (player.isOnline())
            if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) onlinePlayer.showPlayer(player);

                player.setFlySpeed(0.1f);
                player.setFlying(false);
                if (!keepFlight) player.setAllowFlight(false);

                player.setNoDamageTicks(60);
                player.setFallDistance(0);

                if (disableMessage) messageSend(getPlugin(), player, true, RB_DEACTIVATE);

            }

    }

    public static void interactRocketBoots(InventoryClickEvent event, ItemStack boots) {

        Player player = (Player) event.getWhoClicked();
        ClickType click = event.getClick();
        Boolean hasRocketMeta = boots.hasItemMeta();

        if (GamemodeCheck.check(player, GameMode.CREATIVE, GameMode.SPECTATOR)) {
            event.setCancelled(true);
            player.closeInventory();
            disableRocketBoots(player, false, false, false, false, false, true);
            messageSend(getPlugin(), player, true, RB_GAMEMODE_ERROR);
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

                        if (variantType != null) if (variantType == Variant.MONEY) if (getVaultEconomy() == null) {
                            messageSend(getPlugin(), player, true, RB_EQUIP_ERROR);
                            return;
                        }
                    }

                    if (rocketLore.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?"))
                        if (!rocketUsage.contains(player.getUniqueId()))
                            if (click == ClickType.MIDDLE) event.setCancelled(true);
                            else attachRocketBoots(player, boots);

                }

        } else if (rocketSprint.containsKey(player.getUniqueId())) {

            messageSend(getPlugin(), player, true, new String[]{
                    RB_COOLDOWN_TOUCH, RB_COOLDOWN_LAND
            });

            event.setCancelled(true);
            player.closeInventory();

        } else if (rocketPower.containsKey(player.getUniqueId()))
            disableRocketBoots(player, false, false, false, false, false, true);

    }

    public static void attachRocketBoots(Player player, ItemStack boots) {

        if (GamemodeCheck.check(player, GameMode.CREATIVE, GameMode.SPECTATOR)) {
            messageSend(getPlugin(), player, true, RB_GAMEMODE_ERROR);
            disableRocketBoots(player, false, false, false, false, false, true);
            return;
        }

        UUID playerUUID = player.getUniqueId();

        ItemMeta rocketMeta = boots.getItemMeta();
        Block blockMiddle = player.getLocation().getBlock().getRelative(BlockFace.SELF);

        Boolean isWaterVariant = false;
        Boolean isRunnerVariant = false;

        String[] rocketMessage = new String[3];
        rocketMessage[0] = RB_ACTIVATE;

        switch (rocketMeta.getLore().size()) {

            case 1:
                rocketMessage[1] = RB_VARIANT + RB_NOT_FOUND;
                rocketMessage[2] = RB_ENHANCE + RB_NOT_FOUND;
                rocketVariant.put(playerUUID, Variant.ORIGINAL);
                rocketEnhancement.put(playerUUID, Enhancement.NOTHING);
                break;

            case 2:
                String loreLine = ChatColor.stripColor(rocketMeta.getLore().get(1));

                if (Variant.isVariant(loreLine)) {
                    Variant variantType = Variant.getEnum(loreLine);
                    if (variantType != null) {
                        rocketMessage[1] = RB_VARIANT + loreLine;
                        rocketMessage[2] = RB_ENHANCE + RB_NOT_FOUND;
                        rocketVariant.put(playerUUID, variantType);
                        rocketEnhancement.put(playerUUID, Enhancement.NOTHING);
                        if (variantType.equals(Variant.WATER)) isWaterVariant = true;
                        if (variantType.equals(Variant.RUNNER)) isRunnerVariant = true;
                    }
                }

                if (Enhancement.isEnhancement(loreLine)) {
                    Enhancement enhancementType = Enhancement.getEnum(loreLine);
                    if (enhancementType != null) {
                        rocketMessage[1] = RB_VARIANT + RB_NOT_FOUND;
                        rocketMessage[2] = RB_ENHANCE + loreLine;
                        rocketVariant.put(playerUUID, Variant.ORIGINAL);
                        rocketEnhancement.put(playerUUID, enhancementType);
                    }
                }
                break;

            case 3:
                String variantLore = ChatColor.stripColor(rocketMeta.getLore().get(1));
                String enhancementLore = ChatColor.stripColor(rocketMeta.getLore().get(2));

                Variant variantType = Variant.getEnum(variantLore);
                Enhancement enhancementType = Enhancement.getEnum(enhancementLore);

                if (variantType != null && enhancementType != null) {
                    rocketMessage[1] = RB_VARIANT + variantLore;
                    rocketVariant.put(playerUUID, variantType);

                    if (variantType.getEnhancementAllow()) {
                        rocketMessage[2] = RB_ENHANCE + enhancementLore;
                        rocketEnhancement.put(playerUUID, enhancementType);
                    } else rocketMessage[2] = RB_ENHANCE + RB_NOT_WORKING;

                }
                break;

        }

        if (!isWaterVariant && blockMiddle.isLiquid()) {
            rocketWater.add(playerUUID);
            messageSend(getPlugin(), player, true, RB_WATER_WARNING);
            return;
        }

        if (rocketVariant.get(playerUUID) == null || rocketEnhancement.get(playerUUID) == null) {
            messageSend(getPlugin(), player, true, RB_FAIL_ATTACH);
            disableRocketBoots(player, false, false, false, false, false, true);
            return;
        }

        if (rocketVariant.get(playerUUID) != Variant.ORIGINAL && player.getWorld().getName().equals("world_nether")) {
            messageSend(getPlugin(), player, true, RB_NETHER);
            disableRocketBoots(player, false, false, false, false, false, true);
            return;
        }

        if (player.getInventory().getBoots() == null)
            if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

                messageSend(getPlugin(), player, true, rocketMessage);
                if (!isRunnerVariant) player.setAllowFlight(true);
                rocketPower.put(playerUUID, getBootPowerLevel(boots));

            }

    }

    public static void removeFuel(Player player, Material block, Material single, int cost) {

        if (!BlockStacks.split(getPlugin(), player, block, single, cost, (9 - cost)))
            disableRocketBoots(player, true, true, true, true, true, true);

    }

    public static boolean isValidRocketBoots(ItemStack boots) {

        if (boots.hasItemMeta()) {
            ItemMeta bootMeta = boots.getItemMeta();

            if (bootMeta.hasDisplayName())
                if (bootMeta.getDisplayName().matches(RB_NAME))
                    if (bootMeta.hasLore())
                        if (bootMeta.getLore().get(0).matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?"))
                            return true;
        }

        return false;

    }

    public static int getBootPowerLevel(ItemStack boots) {

        return RomanNumeralToInteger.decode(boots.getItemMeta().getLore().get(0).replaceFirst(RB_LEVEL, ""));

    }

    public static int getBootRepairRate(ItemStack boots) {

        switch (getBootPowerLevel(boots)) {

            case 1:
                return 5;

            case 2:
                return 4;

            case 3:
                return 3;

            case 4:
                return 2;

            case 5:
                return new Random().nextInt(9) + 1;

        }

        return 0;

    }

    public static int getBootRepairRate(Material boots) {

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

    public static int getBootDurability(ItemStack boots) {

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

    public static void changeBootDurability(Player player, ItemStack boots) {

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

        String totalDurability = RB_DURABILITY + newBootDurability + " / " + bootMaterialDurability;

        messageSend(getPlugin(), player, true, new String[]{totalDurability});
        TitleSubtitle.subtitle(player, 2, ChatColor.YELLOW + totalDurability);

    }

    public static void reloadFlyZones(boolean showMessage) {

        rocketZoneLocations.clear();
        List<String> zoneList = getPlugin().getConfig().getStringList("zones");

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

                rocketZoneLocations.put(UUID.fromString(zoneSection[0]), new ConcurrentHashMap<Location, Location>() {{
                    put(zoneLocationStart, zoneLocationEnd);
                }});

            }

            if (showMessage) registerMap.put("zone", zoneList.size());

        }

    }

    public static void zoneCrystalCreation(Player player, Location blockLocation) {

        World world = player.getWorld();

        Location centerBlock = CenterBlock.variable(player, blockLocation, 0.475);

        centerBlock.getBlock().setType(Material.AIR);
        world.spawn(centerBlock, EnderCrystal.class);

        int cBX = centerBlock.getBlockX();
        int cBY = centerBlock.getBlockY();
        int cBZ = centerBlock.getBlockZ();

        List<String> zoneList = getPlugin().getConfig().getStringList("zones");
        String activeZone = player.getUniqueId().toString() + "|" + world.getName() + "|" + cBX + "|" + cBY + "|" + cBZ;
        zoneList.add(activeZone);

        getPlugin().getConfig().set("zones", zoneList);
        getPlugin().saveConfig();

        reloadFlyZones(false);

        Location particleLocation = new Location(world, cBX + 0.5, cBY + 1.2, cBZ + 0.5);

        world.playSound(centerBlock, Sound.WITHER_IDLE, 1.25f, 0.55f);
        Particles.show(Particles.ParticleType.PORTAL, particleLocation, new Float[]{0.0f, 0.0f, 0.0f}, 2, 2500);

        messageSend(getPlugin(), player, true, RB_FZ_SUCCESS);

    }

    public static boolean rocketSaddleCheck(ItemStack saddle) {

        if (saddle != null && saddle.hasItemMeta()) {
            ItemMeta saddleMeta = saddle.getItemMeta();
            if (saddleMeta.hasDisplayName())
                if (saddleMeta.getDisplayName().equals(RB_SADDLE)) return true;
        }

        return false;

    }

}
