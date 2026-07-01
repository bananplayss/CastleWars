package me.bananplayss.castlewars.core.effects;

import me.bananplayss.castlewars.api.effects.CustomEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;

public class BleedEffect extends CustomEffect {

    public BleedEffect(long duration) {
        super(null,duration);
    }

    @Override
    public void apply(Location spawnLocation) {

        //Particle.DustOptions dustOptions = new Particle.DustOptions(org.bukkit.Color.RED, 1);

        //Material.STONE.createBlockData();
//        //getSpawnLocation().getWorld().spawnParticle(Particle.BLOCK_DUST, getSpawnLocation(), 10,0,0,0,0.08, dustOptions);
        spawnLocation.getWorld().spawnParticle(Particle.BLOCK_DUST, spawnLocation, 50,0,0,0,1, Material.REDSTONE_BLOCK.createBlockData());
    }

    @Override
    public void end() {

    }
}
