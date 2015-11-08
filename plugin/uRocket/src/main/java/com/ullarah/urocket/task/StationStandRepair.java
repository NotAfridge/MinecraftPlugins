package com.ullarah.urocket.task;

import com.ullarah.ulib.function.RomanNumeralToInteger;
import com.ullarah.ulib.function.SignText;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.ullarah.urocket.RocketInit.getPlugin;
import static com.ullarah.urocket.RocketInit.rocketRepairStand;

public class StationStandRepair {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketRepairStand.isEmpty()) {

                        for (Map.Entry<UUID, Location> repairStand : rocketRepairStand.entrySet()) {

                            Chunk chunk = repairStand.getValue().getWorld().getChunkAt(repairStand.getValue());

                            if (chunk.isLoaded()) {

                                Set<LivingEntity> repairStands = new HashSet<>();

                                for (Entity entity : chunk.getEntities())
                                    if (entity instanceof ArmorStand) repairStands.add((LivingEntity) entity);

                                for (LivingEntity stand : repairStands) {

                                    Location beaconSign = new Location(
                                            stand.getLocation().getWorld(),
                                            stand.getLocation().getX(),
                                            (stand.getLocation().getY() - 1),
                                            stand.getLocation().getZ());

                                    if (stand.getWorld().getBlockAt(
                                            stand.getLocation().getBlockX(),
                                            stand.getLocation().getBlockY() - 2,
                                            stand.getLocation().getBlockZ())
                                            .getType() == Material.BURNING_FURNACE) {

                                        ItemStack standBoots = stand.getEquipment().getBoots();

                                        if (standBoots.hasItemMeta()) {

                                            String bootLore = standBoots.getItemMeta().getLore().get(0);
                                            Integer bootRepair;

                                            Integer powerLevel = RomanNumeralToInteger.decode(
                                                    bootLore.replaceFirst(
                                                            ChatColor.YELLOW + "Rocket Level ", ""));

                                            switch (powerLevel) {

                                                case 1:
                                                    bootRepair = 5;
                                                    break;

                                                case 2:
                                                    bootRepair = 4;
                                                    break;

                                                case 3:
                                                    bootRepair = 3;
                                                    break;

                                                case 4:
                                                    bootRepair = 2;
                                                    break;

                                                case 5:
                                                    bootRepair = new Random().nextInt(9) + 1;
                                                    break;

                                                default:
                                                    bootRepair = 0;
                                                    break;

                                            }

                                            short bootDurability = standBoots.getDurability();

                                            int bootMaterialDurability = 0;

                                            switch (standBoots.getType()) {

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

                                            int bootHealthOriginal = (bootMaterialDurability - bootDurability);
                                            int bootHealthNew = ((bootMaterialDurability - bootDurability) + bootRepair);

                                            String bootType = ChatColor.stripColor(standBoots.getItemMeta().getLore().get(0));

                                            SignText.changeAllCheck(beaconSign, 0, "[Repair Status]", false,
                                                    new String[]{
                                                            "[Repair Status]",
                                                            ChatColor.STRIKETHROUGH + "--------------",
                                                            ChatColor.RED + bootType,
                                                            String.valueOf(bootHealthNew) + " / " + bootMaterialDurability});

                                            if (bootHealthOriginal <= (bootMaterialDurability - 1)) {

                                                standBoots.setDurability((short) (bootDurability - bootRepair));
                                                stand.getEquipment().setBoots(standBoots);

                                                if (bootHealthNew > bootMaterialDurability) {

                                                    SignText.changeAllCheck(beaconSign, 0, "[Repair Status]", false,
                                                            new String[]{
                                                                    "[Repair Status]",
                                                                    ChatColor.STRIKETHROUGH + "--------------",
                                                                    ChatColor.RED + bootType,
                                                                    "Fully Restored"});

                                                    rocketRepairStand.remove(stand.getUniqueId());

                                                } else {

                                                    float x = (float) stand.getLocation().getX();
                                                    float y = (float) stand.getLocation().getY();
                                                    float z = (float) stand.getLocation().getZ();

                                                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                                                            EnumParticle.PORTAL, true, x, y, z, 0, 0, 0, 1, 500, null);

                                                    for (Player serverPlayer : stand.getWorld().getPlayers())
                                                        ((CraftPlayer) serverPlayer).getHandle()
                                                                .playerConnection.sendPacket(packet);

                                                }

                                            } else {
                                                SignText.clearLine(beaconSign, new Integer[]{2, 3});
                                                rocketRepairStand.remove(stand.getUniqueId());
                                            }

                                        }

                                    } else {
                                        SignText.changeLine(beaconSign,
                                                new HashMap<Integer, String>() {{
                                                    put(2, "Repair Tank");
                                                    put(3, "Empty");
                                                }});
                                        rocketRepairStand.remove(stand.getUniqueId());
                                    }

                                }

                            }

                        }
                    }

                }), 0, 600);

    }

}
