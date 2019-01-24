package pl.jasmc.jashub.particles;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleHandler {

    public static Map<UUID, ParticleType> playersParticles = new HashMap<>();

    public static ParticleType getParticle(final Player player) {
        if(!playersParticles.containsKey(player.getUniqueId())) {
            return ParticleType.NONE;
        }
        return  playersParticles.get(player.getUniqueId());
    }

    public static void activateParticle(Player p, ParticleType type) {
        playersParticles.put(p.getUniqueId(), type);
    }

    public static void deactivateParticle(Player p) {
        if(playersParticles.containsKey(p.getUniqueId())) {
            playersParticles.remove(p.getUniqueId());
        }
    }
}
