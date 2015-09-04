package com.ullarah.ulib.function;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class FakeExplosion {

    public static void create(Integer size, Location location, ExplosionType explosion) {

        float eX = (float) location.getX();
        float eY = (float) location.getY();
        float eZ = (float) location.getZ();

        location.getWorld().playSound(location, Sound.EXPLODE, 1.0f, 0.5f);

        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(
                EnumParticle.valueOf(explosion.type), false, eX, eY, eZ, 0, 0, 0, size, size, null);

        for (Player serverPlayer : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) serverPlayer).getHandle().playerConnection.sendPacket(packet);
        }

    }

    public enum ExplosionType {

        NORMAL("EXPLOSION_NORMAL"),
        LARGE("EXPLOSION_LARGE"),
        HUGE("EXPLOSION_HUGE");

        private final String type;

        private ExplosionType(String getType) {
            type = getType;
        }

        public String toString() {
            return type;
        }

    }

}
