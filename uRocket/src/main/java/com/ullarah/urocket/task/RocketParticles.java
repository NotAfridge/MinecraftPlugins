package com.ullarah.urocket.task;

import com.ullarah.urocket.RocketInit;
import com.ullarah.urocket.function.GamemodeCheck;
import com.ullarah.urocket.init.RocketVariant;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.plugin.Plugin;

import java.util.Random;
import java.util.UUID;

public class RocketParticles {

    public void task() {

        Plugin plugin = Bukkit.getPluginManager().getPlugin(RocketInit.pluginName);

        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin,
                () -> plugin.getServer().getScheduler().runTask(plugin, () -> {
                            if (!RocketInit.rocketUsage.isEmpty() && !RocketInit.rocketVariant.isEmpty())
                                for (UUID uuid : RocketInit.rocketUsage) {
                                    processPlayer(Bukkit.getPlayer(uuid));
                                }
                        }
                ), 0, 0);
    }

    private void processPlayer(Player player) {
        GamemodeCheck gamemodeCheck = new GamemodeCheck();
        if (!gamemodeCheck.check(player, GameMode.SURVIVAL, GameMode.ADVENTURE))
            return;

        if (player.isSneaking())
            return;

        if (!player.isFlying() && RocketInit.rocketVariant.get(player.getUniqueId()) != RocketVariant.Variant.RUNNER)
            return;

        if (!RocketInit.rocketVariant.containsKey(player.getUniqueId()))
            return;

        RocketVariant.Variant bootVariant = RocketInit.rocketVariant.get(player.getUniqueId());
        boolean showParticle = true;

        Particle particle = bootVariant.getParticleType();

        float x = (float) player.getLocation().getX();
        float y = (float) (player.getLocation().getY() - 1);
        float z = (float) player.getLocation().getZ();

        float oX = (float) 0.125;
        float oY = (float) -0.5;
        float oZ = (float) 0.125;

        int amount = bootVariant.getParticleSpeed();
        double speed = bootVariant.getParticleSpeed();

        Particle.DustOptions data = null;
        if (particle == Particle.REDSTONE) {
            data = new Particle.DustOptions(Color.RED, 1);
        }

        switch (bootVariant) {

            case DRUNK:
                y += 0.85;
                oX = 0.392f;
                oY = 0.823f;
                oZ = 0.321f;
                break;

            case RAINBOW:
                ItemStack rocketBoots = player.getInventory().getBoots();
                Color color = Color.fromRGB(
                        new Random().nextInt(255),
                        new Random().nextInt(255),
                        new Random().nextInt(255)
                );
                data = new Particle.DustOptions(color, 1);
                if (rocketBoots.getType() == Material.LEATHER_BOOTS) {
                    LeatherArmorMeta armorMeta = (LeatherArmorMeta) rocketBoots.getItemMeta();
                    armorMeta.setColor(color);
                    rocketBoots.setItemMeta(armorMeta);
                }
                break;

            case RUNNER:
                particle = Particle.SMOKE_LARGE;
                speed = 0;
                amount = 5;
                break;

            case STEALTH:
                showParticle = false;
                break;

        }

        if (showParticle) {
            player.getWorld().spawnParticle(particle, x, y, z, amount, oX, oY, oZ, speed, data);
        }

    }

}
