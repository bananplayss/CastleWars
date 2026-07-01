package me.bananplayss.castlewars.core.effects;

import lombok.Getter;
import me.bananplayss.castlewars.api.effects.CustomEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

@Getter
public class RingEffect extends CustomEffect {
    private int radius;
    private Entity attach;
    public RingEffect(Color baseColor,long duration, int radius,@Nullable Entity attach) {
        super(baseColor,duration);
        this.radius = radius;
        this.attach = attach;
    }

    @Override
    public void apply(Location spawnLocation) {
        if(attach != null) spawnLocation = attach.getLocation();
        //Particle.DustOptions dustOptions = new Particle.DustOptions(org.bukkit.Color.RED, 1);

        //Material.STONE.createBlockData();
//        //getSpawnLocation().getWorld().spawnParticle(Particle.BLOCK_DUST, getSpawnLocation(), 10,0,0,0,0.08, dustOptions);
        Particle.DustOptions dust = new Particle.DustOptions(this.getBaseColor(), 0.66f);
        for (double angle = 0; angle < Math.PI * 2; angle += Math.PI / 32) {
            double x = (radius * Math.cos(angle));
            double z = (radius * Math.sin(angle));
            spawnLocation.getWorld().spawnParticle(Particle.REDSTONE, spawnLocation.clone().add(x, 0, z), 0,0,0,0,0.08, dust);
        }
    }

    @Override
    public void end() {

    }
}
