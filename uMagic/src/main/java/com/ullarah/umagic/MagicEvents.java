package com.ullarah.umagic;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import static com.ullarah.umagic.MagicInit.getPlugin;
import static com.ullarah.umagic.MagicInit.getWorldGuard;
import static com.ullarah.umagic.MagicRecipe.hoeName;
import static org.bukkit.Material.DIAMOND_HOE;

@SuppressWarnings("deprecation")
public class MagicEvents implements Listener {

    private MagicFunctions magicFunctions = new MagicFunctions();
    private FixedMetadataValue metadataValue = new FixedMetadataValue(getPlugin(), true);
    private String magicWarning = ChatColor.GOLD + "[" + getPlugin().getName() + "] "
            + ChatColor.RED + ChatColor.BOLD + "WARNING: " + ChatColor.YELLOW;

    private String metaSand = "uMagic.sg";
    private String metaLamp = "uMagic.rl";
    private String metaWool = "uMagic.wl";
    private String metaEmBr = "uMagic.ch";
    private String metaLadd = "uMagic.ld";

    @EventHandler
    public void blockRedstone(BlockRedstoneEvent event) {

        if (event.getBlock().hasMetadata(metaLamp)) event.setNewCurrent(15);

    }

    @EventHandler
    public void blockDamage(BlockDamageEvent event) {

        Block block = event.getBlock();

        for (String meta : new String[]{metaWool, metaLadd})
            if (block.hasMetadata(meta)) {
                event.getPlayer().sendMessage(magicWarning + "Magical block detected, convert back using Magic Hoe.");
                event.setCancelled(true);
            }

    }

    @EventHandler
    public void blockBreak(BlockBreakEvent event) {

        Block block = event.getBlock();

        for (String meta : new String[]{metaSand, metaLamp, metaWool, metaEmBr, metaLadd})
            if (block.hasMetadata(meta)) {
                block.removeMetadata(meta, getPlugin());
                magicFunctions.removeMetadata(block.getLocation());
            }

    }

    @EventHandler
    public void blockPhysics(BlockPhysicsEvent event) {

        Block block = event.getBlock();

        for (String meta : new String[]{metaSand, metaWool, metaLadd})
            if (block.hasMetadata(meta)) event.setCancelled(true);

    }

    @EventHandler
    public void playerInteract(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        ItemStack inMainHand = player.getInventory().getItemInMainHand();
        ItemStack inOffHand = player.getInventory().getItemInOffHand();

        if (checkMagicHoe(inMainHand) ? checkMagicHoe(inMainHand) : checkMagicHoe(inOffHand)) {

            if (!player.getGameMode().equals(GameMode.SURVIVAL)) {
                event.setCancelled(true);
                return;
            }

            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) return;

            Block block = event.getClickedBlock();

            if (!checkBlock(player, block)) {
                event.setCancelled(true);
                return;
            }

            String bedrockWarning = magicWarning + "Block converted to Bedrock. Be careful!";
            String barrierWarning = magicWarning + "Block converted to Barrier. Be careful!";

            double bX = block.getLocation().getX() + 0.5;
            double bY = block.getLocation().getY() + 1;
            double bZ = block.getLocation().getZ() + 0.5;

            Block blockUnder = block.getRelative(BlockFace.DOWN);
            Material blockUnderOriginal = blockUnder.getType();

            byte originalData = block.getData();

            switch (block.getType()) {

                case WOOD:
                    for (BlockFace face : new BlockFace[]{BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST}) {
                        Block blockNext = block.getRelative(face);

                        if (blockNext.getType() == Material.GLASS || blockNext.getType() == Material.STAINED_GLASS) {
                            block.setType(Material.LADDER);

                            switch (face) {
                                case NORTH:
                                    block.setData((byte) 3);
                                    break;

                                case EAST:
                                    block.setData((byte) 4);
                                    break;

                                case SOUTH:
                                    block.setData((byte) 2);
                                    break;

                                case WEST:
                                    block.setData((byte) 5);
                                    break;
                            }

                            block.setMetadata(metaLadd, metadataValue);
                            magicFunctions.saveMetadata(block.getLocation(), metaLadd);

                        }
                    }
                    break;

                case LADDER:
                    if (block.hasMetadata(metaLadd)) {
                        block.setType(Material.WOOD);
                        block.removeMetadata(metaLadd, getPlugin());
                        magicFunctions.removeMetadata(block.getLocation());
                    }
                    break;

                case SAND:
                case GRAVEL:
                    block.setMetadata(metaSand, metadataValue);
                    magicFunctions.saveMetadata(block.getLocation(), metaSand);
                    break;

                case EMERALD_BLOCK:
                    player.sendMessage(bedrockWarning);
                    block.setType(Material.BEDROCK);
                    block.setMetadata(metaEmBr, metadataValue);
                    magicFunctions.saveMetadata(block.getLocation(), metaEmBr);
                    break;

                case BEDROCK:
                    if (block.hasMetadata(metaEmBr)) {
                        player.sendMessage(barrierWarning);
                        block.setType(Material.BARRIER);
                    }
                    break;

                case BARRIER:
                    if (block.hasMetadata(metaEmBr)) {
                        block.setType(Material.EMERALD_BLOCK);
                        block.removeMetadata(metaEmBr, getPlugin());
                        magicFunctions.removeMetadata(block.getLocation());
                    }
                    break;

                case REDSTONE_LAMP_OFF:
                    block.setMetadata(metaLamp, metadataValue);
                    blockUnder.setType(Material.REDSTONE_BLOCK, true);
                    block.getRelative(BlockFace.DOWN).setType(blockUnderOriginal, true);
                    magicFunctions.saveMetadata(block.getLocation(), metaLamp);
                    break;

                case ACACIA_STAIRS:
                case BIRCH_WOOD_STAIRS:
                case BRICK_STAIRS:
                case COBBLESTONE_STAIRS:
                case DARK_OAK_STAIRS:
                case JUNGLE_WOOD_STAIRS:
                case NETHER_BRICK_STAIRS:
                case PURPUR_STAIRS:
                case QUARTZ_STAIRS:
                case RED_SANDSTONE_STAIRS:
                case SANDSTONE_STAIRS:
                case SMOOTH_STAIRS:
                case SPRUCE_WOOD_STAIRS:
                case WOOD_STAIRS:
                    block.setData(block.getData() >= 7 ? (byte) 0 : (byte) (block.getData() + 1));
                    break;

                case WOOL:
                    byte woolData = block.getData();
                    block.setType(Material.CARPET);
                    block.setData(woolData);
                    block.setMetadata(metaWool, metadataValue);
                    magicFunctions.saveMetadata(block.getLocation(), metaWool);
                    break;

                case CARPET:
                    if (block.hasMetadata(metaWool)) {
                        byte carpetData = block.getData();
                        block.setType(Material.WOOL);
                        block.setData(carpetData);
                        block.removeMetadata(metaWool, getPlugin());
                        magicFunctions.removeMetadata(block.getLocation());
                    }
                    break;

                case LOG:
                    switch (block.getData()) {

                        case 0:
                        case 4:
                        case 8:
                            block.setData((byte) 12);
                            break;

                        case 1:
                        case 5:
                        case 9:
                            block.setData((byte) 13);
                            break;

                        case 2:
                        case 6:
                        case 10:
                            block.setData((byte) 14);
                            break;

                        case 3:
                        case 7:
                        case 11:
                            block.setData((byte) 15);
                            break;

                    }
                    break;

                case LOG_2:
                    switch (block.getData()) {

                        case 0:
                        case 4:
                        case 8:
                            block.setData((byte) 12);
                            break;

                        case 1:
                        case 5:
                        case 9:
                            block.setData((byte) 13);
                            break;

                    }
                    break;

                case DOUBLE_STEP:
                    switch (block.getData()) {

                        case 0:
                            block.setData((byte) 8);
                            break;

                        case 1:
                            block.setData((byte) 9);
                            break;

                    }
                    break;

                case DOUBLE_STONE_SLAB2:
                    switch (block.getData()) {

                        case 0:
                            block.setData((byte) 8);
                            break;

                    }
                    break;

                case STONE_BUTTON:
                case WOOD_BUTTON:
                    block.setData((byte) (originalData + 8));
                    break;

                case HUGE_MUSHROOM_1:
                case HUGE_MUSHROOM_2:
                    block.setData(block.getData() < 15 ? block.getData() == 10 ?
                            (byte) 14 : (byte) (block.getData() + 1) : (byte) 0);
                    break;

            }

            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 15);
            block.getWorld().playSound(block.getLocation(), Sound.UI_BUTTON_CLICK, 0.75f, 0.75f);
            event.setCancelled(true);

        }

    }

    private boolean checkBlock(Player player, Block block) {

        if (player.hasPermission("magic.bypass")) return true;

        RegionManager regionManager = getWorldGuard().getRegionManager(block.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(block.getLocation());

        if (applicableRegionSet.getRegions().isEmpty()) return false;
        for (ProtectedRegion r : applicableRegionSet.getRegions())
            if (!r.isOwner(getWorldGuard().wrapPlayer(player))) return false;

        return true;

    }

    private boolean checkMagicHoe(ItemStack item) {

        if (item.getType() == DIAMOND_HOE) {

            if (item.hasItemMeta()) {

                if (item.getItemMeta().hasDisplayName()) {

                    if (item.getItemMeta().getDisplayName().equals(hoeName)) {

                        return true;

                    }

                }

            }

        }

        return false;

    }


}
