package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.data.FlyLockout;
import com.ullarah.urocket.data.RocketPlayer;
import com.ullarah.urocket.function.GamemodeCheck;
import com.ullarah.urocket.init.RocketVariant;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Random;

public class NotePlaying {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {

                    for (Player player : RocketInit.getPlayers()) {
                        processNoteTask(player);
                    }

                }), 10, 10);

    }

    private void processNoteTask(Player player) {
        GamemodeCheck gamemodeCheck = new GamemodeCheck();
        if (!gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE)) {
            return;
        }

        RocketPlayer rp = RocketInit.getPlayer(player);
        if (player.isFlying() && rp.isUsingBoots()) {

            if (!player.isSneaking()) {

                if (rp.getBootData() != null) {

                    RocketVariant.Variant variant = rp.getBootData().getVariant();
                    switch (variant) {

                        case NOTE:
                            String[] tones = {"A", "B", "C", "D", "E", "F", "G"};

                            int randomPianoOctave = new Random().nextInt(2);
                            int randomBassOctave = new Random().nextInt(2);
                            int randomTones = new Random().nextInt(tones.length);

                            for (Player serverPlayer : player.getWorld().getPlayers()) {

                                serverPlayer.playNote(
                                        player.getLocation(),
                                        Instrument.PIANO,
                                        Note.natural(randomPianoOctave, Note.Tone.valueOf(tones[randomTones]))
                                );

                                serverPlayer.playNote(
                                        player.getLocation(),
                                        Instrument.BASS_GUITAR,
                                        Note.natural(randomBassOctave, Note.Tone.valueOf(tones[randomTones]))
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

                    if (variant == RocketVariant.Variant.NOTE
                            || variant == RocketVariant.Variant.SOUND) {

                        float x = (float) player.getLocation().getX();
                        float y = (float) (player.getLocation().getY() - 1);
                        float z = (float) player.getLocation().getZ();

                        float oX = (float) 0.125;
                        float oY = (float) -0.5;
                        float oZ = (float) 0.125;

                        int amount = 5;
                        int speed = 0;
                        if (rp.getLockouts().getSprintLock() == FlyLockout.Sprint.NONE) {
                            amount = variant.getParticleAmount();
                            speed = variant.getParticleSpeed();
                        }

                        player.getWorld().spawnParticle(Particle.SMOKE_LARGE, x, y, z, amount, oX, oY, oZ, speed);

                    }

                }

            }

        }
    }

}
