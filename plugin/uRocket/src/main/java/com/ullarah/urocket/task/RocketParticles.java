package com.ullarah.urocket.task;

import com.ullarah.ulib.function.GamemodeCheck;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Random;
import java.util.UUID;

import static com.ullarah.urocket.RocketFunctions.Variant;
import static com.ullarah.urocket.RocketInit.*;
import static org.bukkit.Material.LEATHER_BOOTS;

public class RocketParticles {

    public void task() {

        Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(getPlugin(),
                () -> Bukkit.getScheduler().runTask(getPlugin(), () -> {

                    if (!rocketUsage.isEmpty() && !rocketVariant.isEmpty()) for (UUID uuid : rocketUsage) {

                        Player player = Bukkit.getPlayer(uuid);

                        if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

                            if (player.isFlying() || rocketVariant.get(player.getUniqueId()) == Variant.RUNNER) {

                                if (!player.isSneaking()) {

                                    if (rocketVariant.containsKey(player.getUniqueId())) {

                                        Variant bootVariant = rocketVariant.get(player.getUniqueId());

                                        if (bootVariant != null) {

                                            float x = (float) player.getLocation().getX();
                                            float y = (float) (player.getLocation().getY() - 1);
                                            float z = (float) player.getLocation().getZ();

                                            float oX = (float) 0.125;
                                            float oY = (float) -0.5;
                                            float oZ = (float) 0.125;

                                            PacketPlayOutWorldParticles packet = null;

                                            if (rocketSprint.containsKey(player.getUniqueId())) {
                                                packet = new PacketPlayOutWorldParticles(
                                                        EnumParticle.SMOKE_LARGE,
                                                        true, x, y, z, oX, oY, oZ, 0, 5, null);
                                            } else {

                                                int sparkColour = new Random().nextInt(25);

                                                switch (bootVariant) {

                                                    case ENDER:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.PORTAL,
                                                                true, x, y, z, oX, oY, oZ, 0, 25, null);
                                                        break;

                                                    case HEALTH:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.CRIT_MAGIC,
                                                                true, x, y, z, oX, oY, oZ, 0, 25, null);
                                                        break;

                                                    case KABOOM:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.EXPLOSION_NORMAL,
                                                                true, x, y, z, oX, oY, oZ, 0, 10, null);
                                                        break;

                                                    case RAINBOW:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.REDSTONE,
                                                                true, x, y, z, oX, oY, oZ,
                                                                sparkColour, sparkColour, null);

                                                        ItemStack rocketBoots = player.getInventory().getBoots();

                                                        if (rocketBoots.getType() == LEATHER_BOOTS) {
                                                            LeatherArmorMeta armorMeta = (LeatherArmorMeta) rocketBoots.getItemMeta();
                                                            armorMeta.setColor(Color.fromRGB(
                                                                    new Random().nextInt(255),
                                                                    new Random().nextInt(255),
                                                                    new Random().nextInt(255)
                                                            ));
                                                            rocketBoots.setItemMeta(armorMeta);
                                                        }
                                                        break;

                                                    case WATER:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.WATER_BUBBLE,
                                                                true, x, y, z, oX, oY, oZ, 0, 25, null);
                                                        break;

                                                    case ZERO:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.ENCHANTMENT_TABLE,
                                                                true, x, y, z, oX, oY, oZ, 0, 25, null);
                                                        break;

                                                    case AGENDA:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.SPELL_WITCH,
                                                                true, x, y, z, oX, oY, oZ,
                                                                sparkColour, sparkColour, null);
                                                        break;

                                                    case MONEY:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.VILLAGER_HAPPY,
                                                                true, x, y, z, oX, oY, oZ, 0, 5, null);
                                                        break;

                                                    case DRUNK:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.SPELL_MOB,
                                                                false, x, (float) (y + 0.85), z,
                                                                .392f, .823f, .321f, 1, 0, null);
                                                        break;

                                                    case BOOST:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.SNOWBALL,
                                                                true, x, y, z, oX, oY, oZ, 0, 5, null);
                                                        break;

                                                    case COAL:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.TOWN_AURA,
                                                                true, x, y, z, oX, oY, oZ, 0, 50, null);
                                                        break;

                                                    case REDSTONE:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.REDSTONE,
                                                                true, x, y, z, oX, oY, oZ, 0, 10, null);
                                                        break;

                                                    case RUNNER:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.EXPLOSION_NORMAL,
                                                                true, x, y, z, oX, oY, oZ, 0, 30, null);
                                                        break;

                                                    case GLOW:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.SUSPENDED_DEPTH,
                                                                true, x, y, z, oX, oY, oZ, 0, 10, null);
                                                        break;

                                                    case ORIGINAL:
                                                        packet = new PacketPlayOutWorldParticles(
                                                                EnumParticle.FLAME,
                                                                true, x, y, z, oX, oY, oZ, 0, 5, null);
                                                        break;

                                        }

                                    }

                                            for (Player serverPlayer : player.getWorld().getPlayers())
                                                ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);

                                }

                            }

                        }

                    }

                        }

            }

                }), 0, 0);

    }

}
