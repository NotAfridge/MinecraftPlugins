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
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketVariant.Variant;
import static com.ullarah.urocket.RocketVariant.Variant.*;
import static org.bukkit.Material.LEATHER_BOOTS;

public class RocketParticles {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                            if (!rocketUsage.isEmpty() && !rocketVariant.isEmpty()) for (UUID uuid : rocketUsage) {

                                Player player = Bukkit.getPlayer(uuid);

                                if (GamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE))
                                    if (player.isFlying() || rocketVariant.get(player.getUniqueId()) == RUNNER)
                                        if (!player.isSneaking()) if (rocketVariant.containsKey(player.getUniqueId())) {

                                            Variant bootVariant = rocketVariant.get(player.getUniqueId());

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

                                                packet = new PacketPlayOutWorldParticles(
                                                        bootVariant.getParticleType(),
                                                        true, x, y, z, oX, oY, oZ,
                                                        bootVariant.getParticleSpeed(),
                                                        bootVariant.getParticleAmount(),
                                                        null);

                                                if (bootVariant.equals(RAINBOW)) {
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
                                                }

                                                if (bootVariant.equals(DRUNK)) {
                                                    packet = new PacketPlayOutWorldParticles(
                                                            bootVariant.getParticleType(),
                                                            false, x, (float) (y + 0.85), z,
                                                            .392f, .823f, .321f,
                                                            bootVariant.getParticleSpeed(),
                                                            bootVariant.getParticleAmount(),
                                                            null);
                                                }

                                            }

                                            for (Player serverPlayer : player.getWorld().getPlayers())
                                                ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);

                                        }

                            }


                        }

                ), 0, 0);

    }

}
