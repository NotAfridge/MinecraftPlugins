package com.ullarah.urocket.task;

import com.ullarah.ulib.function.GamemodeCheck;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Instrument;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.UUID;

import static com.ullarah.urocket.RocketInit.*;
import static com.ullarah.urocket.RocketVariant.Variant;
import static com.ullarah.urocket.RocketVariant.Variant.NOTE;
import static com.ullarah.urocket.RocketVariant.Variant.SOUND;
import static org.bukkit.Note.Tone;
import static org.bukkit.Note.natural;

public class NotePlaying {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    if (!rocketUsage.isEmpty() && !rocketVariant.isEmpty()) for (UUID uuid : rocketUsage) {

                        Player player = Bukkit.getPlayer(uuid);

                        if (new GamemodeCheck().check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {

                            if (player.isFlying()) {

                                if (!player.isSneaking()) {

                                    if (rocketVariant.containsKey(player.getUniqueId())) {

                                        Variant bootVariant = rocketVariant.get(player.getUniqueId());

                                        switch (bootVariant) {

                                            case NOTE:
                                                String[] tones = {"A", "B", "C", "D", "E", "F", "G"};

                                                int randomPianoOctave = new Random().nextInt(2);
                                                int randomBassOctave = new Random().nextInt(2);
                                                int randomTones = new Random().nextInt(tones.length);

                                                for (Player serverPlayer : player.getWorld().getPlayers()) {

                                                    serverPlayer.playNote(
                                                            player.getLocation(),
                                                            Instrument.PIANO,
                                                            natural(randomPianoOctave, Tone.valueOf(tones[randomTones]))
                                                    );

                                                    serverPlayer.playNote(
                                                            player.getLocation(),
                                                            Instrument.BASS_GUITAR,
                                                            natural(randomBassOctave, Tone.valueOf(tones[randomTones]))
                                                    );

                                                }
                                                break;

                                            case SOUND:
                                                float min = 0.25f;
                                                float max = 1.25f;

                                                Sound[] allSounds = org.bukkit.Sound.values();

                                                Sound randomSound = allSounds[new Random().nextInt(allSounds.length)];
                                                float randomPitch = new Random().nextFloat() * (max - min) + min;
                                                float randomVolume = new Random().nextFloat() * (max - min) + min;

                                                for (Player serverPlayer : player.getWorld().getPlayers()) {

                                                    serverPlayer.playSound(
                                                            player.getLocation(),
                                                            randomSound, randomPitch, randomVolume
                                                    );

                                                }
                                                break;

                                        }

                                        if (bootVariant == NOTE || bootVariant == SOUND) {

                                            float x = (float) player.getLocation().getX();
                                            float y = (float) (player.getLocation().getY() - 1);
                                            float z = (float) player.getLocation().getZ();

                                            float oX = (float) 0.125;
                                            float oY = (float) -0.5;
                                            float oZ = (float) 0.125;

                                            PacketPlayOutWorldParticles packet;

                                            if (rocketSprint.containsKey(player.getUniqueId()))
                                                packet = new PacketPlayOutWorldParticles(EnumParticle.SMOKE_LARGE,
                                                        true, x, y, z, oX, oY, oZ, 0, 5, null);
                                            else packet = new PacketPlayOutWorldParticles(bootVariant.getParticleType(),
                                                    true, x, y, z, oX, oY, oZ,
                                                    bootVariant.getParticleSpeed(),
                                                    bootVariant.getParticleAmount(),
                                                    null);

                                            for (Player serverPlayer : player.getWorld().getPlayers())
                                                ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);

                                        }

                                    }

                                }

                            }

                        }

                    }

                }), 10, 10);

    }

}
