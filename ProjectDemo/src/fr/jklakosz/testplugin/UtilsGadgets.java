package fr.jklakosz.testplugin;

import net.minecraft.server.v1_15_R1.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_15_R1.ParticleType;
import net.minecraft.server.v1_15_R1.Particles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Cow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class UtilsGadgets {

    private static void playParticle(ParticleType type, Location loc) {
        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(type, true, loc.getX(), loc.getY(), loc.getZ(), 0f, 0f, 0f, 0, 1);
        for (Player p : Bukkit.getOnlinePlayers())
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    public static void executeSaber(Player p) {
        List<Location> parts = new ArrayList<>();

        float radius = 0.67f;
        int numberOfParticlesPerLap = 30;
        int numberOfLap = 2;
        float yStep = 0.03f;

        for (float lap = 0; lap < numberOfLap; lap++) {
            for (int i = 0; i < numberOfParticlesPerLap; i++) {
                double part = ((Math.PI * 2) / numberOfParticlesPerLap) * i;

                parts.add(new Location(
                        p.getWorld(),
                        Math.cos(part) * radius,
                        yStep * numberOfParticlesPerLap * lap + yStep * i,
                        Math.sin(part) * radius
                ));
            }
        }

        new BukkitRunnable() {
            int id = 0;

            @Override
            public void run() {
                if (id >= parts.size()) {
                    cancel();
                    return;
                }
                for (int i = 0; i < 2; i++)
                    playParticle(Particles.NOTE, p.getLocation().add(parts.get(id++)));
            }
        }.runTaskTimer(Main.getInstance(), 0, 1);
    }

    public static void executeCowGun(Player p) {
        Cow cow = (Cow) p.getWorld().spawnEntity(p.getLocation(), EntityType.COW);
        cow.setVelocity(p.getLocation().getDirection().normalize().multiply(4));
    }

}
