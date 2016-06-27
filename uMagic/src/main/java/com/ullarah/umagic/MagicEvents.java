package com.ullarah.umagic;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import static com.ullarah.umagic.MagicInit.getPlugin;
import static com.ullarah.umagic.MagicInit.getWorldGuard;
import static org.bukkit.Material.DIAMOND_HOE;

@SuppressWarnings("deprecation")
public class MagicEvents implements Listener {

    @EventHandler
    public void redstoneBlock(BlockRedstoneEvent event) {

        if (event.getBlock().hasMetadata("uMagic.rlon")) event.setNewCurrent(15);

    }

    @EventHandler
    public void changeBlock(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        ItemStack inMainHand = player.getInventory().getItemInMainHand();
        ItemStack inOffHand = player.getInventory().getItemInOffHand();

        if (checkMagicHoe(inMainHand) ? checkMagicHoe(inMainHand) : checkMagicHoe(inOffHand)) {

            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_AIR) return;

            Block block = event.getClickedBlock();
            if (!checkBlock(player, block)) return;

            String warning = " " + ChatColor.RED + ChatColor.BOLD + "WARNING: " + ChatColor.YELLOW;

            String placeWarning = warning + "Glitch block created. Do not place blocks next to it.";
            String barrierWarning = warning + "Barrier block created. Be careful with these.";
            String bedrockWarning = warning + "Bedrock block created. Be careful with these.";

            double bX = block.getLocation().getX() + 0.5;
            double bY = block.getLocation().getY() + 1;
            double bZ = block.getLocation().getZ() + 0.5;

            Block blockUnder = block.getRelative(BlockFace.DOWN);
            Material blockUnderOriginal = blockUnder.getType();

            switch (block.getType()) {

                case EMERALD_BLOCK:
                    player.sendMessage(bedrockWarning);
                    block.setType(Material.BEDROCK);
                    block.setMetadata("uMagic.ch", new FixedMetadataValue(getPlugin(), true));
                    break;

                case BEDROCK:
                    if (block.hasMetadata("uMagic.ch")) {
                        player.sendMessage(barrierWarning);
                        block.setType(Material.BARRIER);
                    }
                    break;

                case BARRIER:
                    if (block.hasMetadata("uMagic.ch")) block.setType(Material.EMERALD_BLOCK);
                    break;

                case REDSTONE_LAMP_OFF:
                    block.setMetadata("uMagic.rlon", new FixedMetadataValue(getPlugin(), true));
                    blockUnder.setType(Material.REDSTONE_BLOCK, true);
                    block.getRelative(BlockFace.DOWN).setType(blockUnderOriginal, true);
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

                case PISTON_BASE:
                    player.sendMessage(placeWarning);
                    switch (event.getAction()) {

                        case RIGHT_CLICK_BLOCK:
                            switch (block.getData()) {

                                case 0:
                                    block.setData((byte) 8);
                                    break;

                                case 1:
                                    block.setData((byte) 9);
                                    break;

                                case 2:
                                    block.setData((byte) 10);
                                    break;

                                case 3:
                                    block.setData((byte) 11);
                                    break;

                                case 4:
                                    block.setData((byte) 12);
                                    break;

                                case 5:
                                    block.setData((byte) 13);
                                    break;

                            }
                            break;

                        case LEFT_CLICK_BLOCK:
                            block.setTypeId(34);
                            switch (block.getData()) {

                                case 0:
                                    block.setData((byte) 0);
                                    break;

                                case 1:
                                    block.setData((byte) 1);
                                    break;

                                case 2:
                                    block.setData((byte) 2);
                                    break;

                                case 3:
                                    block.setData((byte) 3);
                                    break;

                                case 4:
                                    block.setData((byte) 4);
                                    break;

                                case 5:
                                    block.setData((byte) 5);
                                    break;

                            }
                            break;

                    }
                    break;

                case PISTON_STICKY_BASE:
                    player.sendMessage(placeWarning);
                    switch (event.getAction()) {

                        case RIGHT_CLICK_BLOCK:
                            switch (block.getData()) {

                                case 0:
                                    block.setData((byte) 8);
                                    break;

                                case 1:
                                    block.setData((byte) 9);
                                    break;

                                case 2:
                                    block.setData((byte) 10);
                                    break;

                                case 3:
                                    block.setData((byte) 11);
                                    break;

                                case 4:
                                    block.setData((byte) 12);
                                    break;

                                case 5:
                                    block.setData((byte) 13);
                                    break;

                            }
                            break;

                        case LEFT_CLICK_BLOCK:
                            block.setTypeId(34);
                            switch (block.getData()) {

                                case 0:
                                    block.setData((byte) 8);
                                    break;

                                case 1:
                                    block.setData((byte) 9);
                                    break;

                                case 2:
                                    block.setData((byte) 10);
                                    break;

                                case 3:
                                    block.setData((byte) 11);
                                    break;

                                case 4:
                                    block.setData((byte) 12);
                                    break;

                                case 5:
                                    block.setData((byte) 13);
                                    break;

                            }
                            break;

                    }
                    break;

                case HUGE_MUSHROOM_1:
                case HUGE_MUSHROOM_2:
                    block.setData(block.getData() < 15 ? block.getData() == 10 ?
                            (byte) 14 : (byte) (block.getData() + 1) : (byte) 0);

                    break;

            }

            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, bX, bY, bZ, 10);
            block.getWorld().playSound(block.getLocation(), Sound.UI_BUTTON_CLICK, 0.75f, 0.75f);

        }

    }

    private boolean checkBlock(Player player, Block block) {

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

                    if (item.getItemMeta().getDisplayName().equals(ChatColor.AQUA + "Magical Hoe")) {

                        return true;

                    }

                }

            }

        }

        return false;

    }

}
