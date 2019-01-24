package pl.jasmc.jashub.particles;

import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;
import pl.jasmc.jashub.listeners.custom.events.UpdateEvent;
import pl.jasmc.jashub.util.UtilVelocity;

public class UnlockedParticle implements Listener {

    static float radius;
    static int particles;
    static float height;
    static int step;
    static double angularVelocityX;

    static {
        radius = 2.0f;
        particles = 15;
        height = 0.0f;
        step = 0;
        angularVelocityX = 0.07853981633974483;
    }


    @EventHandler
    public void playParticle(final UpdateEvent event) {

        for(Player p : Bukkit.getOnlinePlayers()) {
            if(ParticleHandler.getParticle(p) == ParticleType.UNLOCKED && p.isValid()) {
                final double xRotation = step * angularVelocityX;
                final Location location;
                final Location l2 = location = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ());
                l2.add(0.0, (double)height, 0.0);
                for (int k = 0; k < particles; ++k) {
                    final double x = Math.cos(k * 3.141592653589793 / 4.0) * radius;
                    final double y = height;
                    final double z = Math.sin(k * 3.141592653589793 / 4.0) * radius;
                    final Vector v = new Vector(x, y, z);
                    UtilVelocity.rotateVector(v, 0.0, -xRotation, 0.0);
                    location.add(v);
                    // new UtilParticle(location, ParticleEffect.SNOW_SHOVEL, 1).playParticle();
                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.VILLAGER_HAPPY, true, (float) (location.getX()), (float) (location.getY()), (float) (location.getZ()), 0, 0, 0, 0, 1);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
                    }

                    location.subtract(v);
                }
                if (step % 2 == 0) {
                    height += 0.1f;
                    if (height > 1.9) {

                        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.HEART, true, (float) (location.getX()), (float) (location.getY() + 2.5), (float) (location.getZ()), 0, 0, 0, 0, 20);
                        for (Player online : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) online).getHandle().playerConnection.sendPacket(packet);
                        }
                        height = 0.0f;
                    }
                    radius -= 0.1f;
                    if (radius < 0.1f) {
                        radius = 2.0f;
                    }
                }


            }
        }

    }




}
