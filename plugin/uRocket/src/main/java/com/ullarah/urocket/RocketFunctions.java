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

import static com.ullarah.urocket.RocketEnhancement.Enhancement;
import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketVariant.Variant.*;

public class RocketFunctions {

    public static void disableRocketBoots(Player player, Boolean keepUsage, Boolean keepPower, Boolean keepFlight,
                                          Boolean keepVariant, Boolean keepEnhancement) {

        UUID playerUUID = player.getUniqueId();

        if (!keepUsage && rocketUsage.contains(playerUUID)) rocketUsage.remove(playerUUID);
        if (rocketSprint.containsKey(playerUUID)) rocketSprint.remove(playerUUID);
        if (rocketLowFuel.contains(playerUUID)) rocketLowFuel.remove(playerUUID);
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

                player.sendMessage(getMsgPrefix() + "Rocket Boots Deactivated!");

            }

    }

    public static void interactRocketBoots(InventoryClickEvent event, ItemStack boots) {

        Player player = (Player) event.getWhoClicked();
        ClickType click = event.getClick();
        Boolean hasRocketMeta = boots.hasItemMeta();

        if (GamemodeCheck.check(player, GameMode.CREATIVE, GameMode.SPECTATOR)) {
            event.setCancelled(true);
            player.closeInventory();
            disableRocketBoots(player, false, false, false, false, false);
            player.sendMessage(getMsgPrefix() + "Rocket Boots do not work in this gamemode!");
            return;
        }

        if (hasRocketMeta) {

            ItemMeta rocketMeta = boots.getItemMeta();

            if (rocketMeta.hasDisplayName())
                if (rocketMeta.getDisplayName().matches(ChatColor.RED + "Rocket Boots")) if (rocketMeta.hasLore()) {

                    Boolean validBoots = true;
                    String rocketLore = rocketMeta.getLore().get(0);
                    String variantLore = null;

                    if (rocketMeta.getLore().size() >= 2)
                        variantLore = ChatColor.stripColor(rocketMeta.getLore().get(1));

                    Set<String> specialVariants = new HashSet<>(Collections.singletonList("Robin Hood"));

                    if (variantLore != null) if (specialVariants.contains(variantLore)) {

                        Variant variantType = getEnum(variantLore);

                        if (variantType != null) {
                            switch (variantType) {

                                case MONEY:
                                    if (getVaultEconomy() == null) {
                                        validBoots = false;
                                        player.sendMessage(getMsgPrefix() + "These Rocket Boots cannot be equipped!");
                                    }
                                    break;

                                default:
                                    validBoots = true;
                                    break;

                            }
                        }
                    }

                    if (validBoots && rocketLore.matches(ChatColor.YELLOW + "Rocket Level I{0,3}V?X?"))
                        if (!rocketUsage.contains(player.getUniqueId()))
                            if (click == ClickType.MIDDLE) event.setCancelled(true);
                            else attachRocketBoots(player, rocketMeta);

                }

        } else if (rocketSprint.containsKey(player.getUniqueId())) {

            player.sendMessage(new String[]{
                    getMsgPrefix() + ChatColor.RED + "Ouch! You cannot take your boots of yet!",
                    getMsgPrefix() + ChatColor.RESET + "You need to land for them to cool down!"
            });

            event.setCancelled(true);
            player.closeInventory();

        } else if (rocketPower.containsKey(player.getUniqueId()))
            disableRocketBoots(player, false, false, false, false, false);

    }

    public static void attachRocketBoots(Player player, ItemMeta rocketMeta) {

        if (GamemodeCheck.check(player, GameMode.CREATIVE, GameMode.SPECTATOR)) {
            disableRocketBoots(player, false, false, false, false, false);
            player.sendMessage(getMsgPrefix() + "Rocket Boots do not work in this gamemode!");
            return;
        }

        Block blockMiddle = player.getLocation().getBlock().getRelative(BlockFace.SELF);

        Boolean isWaterVariant = false;
        Boolean isRunnerVariant = false;

        String[] rocketMessage = new String[3];
        rocketMessage[0] = "Rocket Boots Activated!";

        switch (rocketMeta.getLore().size()) {

            case 1:
                rocketMessage[1] = "Variant: " + ChatColor.RED + "Not Found";
                rocketMessage[2] = "Enhancement: " + ChatColor.RED + "Not Found";
                rocketVariant.put(player.getUniqueId(), ORIGINAL);
                break;

            case 2:
                String loreLine = ChatColor.stripColor(rocketMeta.getLore().get(1));

                if (Variant.isVariant(loreLine)) {
                    rocketMessage[1] = "Variant: " + ChatColor.AQUA + loreLine;
                    rocketMessage[2] = "Enhancement: " + ChatColor.RED + "Not Found";
                    rocketVariant.put(player.getUniqueId(), Variant.getEnum(loreLine));
                    if (Variant.getEnum(loreLine).equals(WATER)) isWaterVariant = true;
                    if (Variant.getEnum(loreLine).equals(RUNNER)) isRunnerVariant = true;
                }

                if (Enhancement.isEnhancement(loreLine)) {
                    rocketMessage[1] = "Variant: " + ChatColor.RED + "Not Found";
                    rocketMessage[2] = "Enhancement: " + ChatColor.AQUA + loreLine;
                    rocketEnhancement.put(player.getUniqueId(), Enhancement.getEnum(loreLine));
                }
                break;

            case 3:
                String variantLore = ChatColor.stripColor(rocketMeta.getLore().get(1));
                String enhancementLore = ChatColor.stripColor(rocketMeta.getLore().get(2));

                Variant variantType = Variant.getEnum(variantLore);
                Enhancement enhancementType = Enhancement.getEnum(enhancementLore);

                rocketMessage[1] = "Variant: " + ChatColor.AQUA + variantLore;
                rocketVariant.put(player.getUniqueId(), variantType);

                if (variantType.getEnhancementAllow()) {
                    rocketMessage[2] = "Enhancement: " + ChatColor.AQUA + enhancementLore;
                    rocketEnhancement.put(player.getUniqueId(), enhancementType);
                } else rocketMessage[2] = "Enhancement: " + ChatColor.YELLOW + "Not Working";
                break;

            default:
                break;

        }

        if (!isWaterVariant && blockMiddle.isLiquid()) {

            rocketWater.add(player.getUniqueId());
            player.sendMessage(getMsgPrefix() + "These Rocket Boots will not start in water!");
            return;

        }

        if (player.getInventory().getBoots() == null)
            if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

                CommonString.messageSend(getPlugin(), player, true, rocketMessage);

                if (!isRunnerVariant) player.setAllowFlight(true);

                Integer powerLevel = RomanNumeralToInteger.decode(
                        rocketMeta.getLore().get(0).replaceFirst(
                                ChatColor.YELLOW + "Rocket Level ", ""));

                rocketPower.put(player.getUniqueId(), powerLevel);

            }

    }

    public static int getBootPowerLevel(ItemStack rocketBoots) {

        return RomanNumeralToInteger.decode(
                rocketBoots.getItemMeta().getLore().get(0)
                        .replaceFirst(ChatColor.YELLOW + "Rocket Level ", ""));

    }

    public static void changeBootDurability(Player player, ItemStack rocketBoots) {

        Short rocketDurability = rocketBoots.getDurability();
        Material rocketMaterial = rocketBoots.getType();

        int bootMaterialDurability = 0;
        short changedDurability = 0;

        switch (rocketMaterial) {

            case LEATHER_BOOTS:
                bootMaterialDurability = 65;
                break;

            case IRON_BOOTS:
                bootMaterialDurability = 195;
                break;

            case GOLD_BOOTS:
                bootMaterialDurability = 91;
                break;

            case DIAMOND_BOOTS:
                bootMaterialDurability = 429;
                break;

        }

        switch (getBootPowerLevel(rocketBoots)) {

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

        rocketBoots.setDurability(changedDurability);

        int newBootDurability = bootMaterialDurability - changedDurability;

        if (newBootDurability < 0) {
            rocketBoots.setDurability((short) bootMaterialDurability);
            newBootDurability = 0;
        }

        String totalDurability = ChatColor.YELLOW + "Rocket Boot Durability: "
                + newBootDurability + " / " + bootMaterialDurability;

        player.sendMessage(getMsgPrefix() + totalDurability);
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

                ConcurrentHashMap<Location, Location> zoneLocation = new ConcurrentHashMap<Location, Location>() {{
                    put(zoneLocationStart, zoneLocationEnd);
                }};

                rocketZoneLocations.put(UUID.fromString(zoneSection[0]), zoneLocation);

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

        player.sendMessage(getMsgPrefix() + ChatColor.YELLOW + "Rocket Fly Zone Controller is now activated!");

    }

    public static boolean rocketSaddleCheck(ItemStack saddle) {

        if (saddle != null && saddle.hasItemMeta()) {

            ItemMeta saddleMeta = saddle.getItemMeta();

            if (saddleMeta.hasDisplayName()) {

                String saddleName = saddleMeta.getDisplayName();

                if (saddleName.equals(ChatColor.RED + "Rocket Saddle")) {

                    return true;

                }

            }

        }

        return false;

    }

}
