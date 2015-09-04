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

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(), new Runnable() {

            public void run() {

                Bukkit.getScheduler().runTask(getPlugin(), new Runnable() {

                    @Override
                    public void run() {

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
                                                int bootHealthOriginal = (195 - bootDurability);
                                                int bootHealthNew = ((195 - bootDurability) + bootRepair);

                                                SignText.changeAllCheck(beaconSign, 0, "[Repair Status]", false,
                                                        new String[]{"[Repair Status]", "----------",
                                                                standBoots.getItemMeta().getLore().get(0),
                                                                String.valueOf(bootHealthNew)});

                                                if (bootHealthOriginal <= 194) {

                                                    standBoots.setDurability((short) (bootDurability - bootRepair));
                                                    stand.getEquipment().setBoots(standBoots);

                                                    if (bootHealthNew > 195) {

                                                        SignText.changeAllCheck(beaconSign, 0, "[Repair Status]", false,
                                                                new String[]{"[Repair Status]", "----------",
                                                                        standBoots.getItemMeta().getLore().get(0),
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
                                                    SignText.clearLines(beaconSign, new Integer[]{2, 3});
                                                    rocketRepairStand.remove(stand.getUniqueId());
                                                }

                                            }

                                        } else {
                                            SignText.changeLine(beaconSign, new Integer[]{2, 3},
                                                    new String[]{"Repair Tank", "Empty"});
                                            rocketRepairStand.remove(stand.getUniqueId());
                                        }

                                    }

                                }

                            }
                        }

                    }

                });

            }

        }, 0, 600);

    }

}
