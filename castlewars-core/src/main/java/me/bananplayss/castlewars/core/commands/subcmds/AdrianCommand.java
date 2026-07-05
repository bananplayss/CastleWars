package me.bananplayss.castlewars.core.commands.subcmds;

import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.effects.BoundingBoxVolumeEffect;
import me.bananplayss.castlewars.core.game.FlagGameImpl;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.utils.LocationUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.List;

public class AdrianCommand implements SubCommand {
    @Override
    public String getName() {
        return "effectTest";
    }

    @Override
    public String getDescription() {
        return "UwUgecfaszgeci";
    }

    @Override
    public String getSyntax() {
        return "/castlewars effectTest ";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.effectTest";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of("köcsög_adrian");
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        Profile prof =  ProfileCacheImpl.getProfileImpl(p);
        if(prof.getCurrentGame() == null) return;

        if(prof.getCurrentGame() instanceof FlagGameImpl fg) {
            GameFlagTeam ft = fg.getTeam(p);


            EnderDragon dragon = p.getWorld().spawn(p.getLocation(), EnderDragon.class);
            dragon.addPassenger(p);

            //dragon.setAI(true);
            dragon.setPhase(EnderDragon.Phase.STRAFING);
            Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
                dragon.setVelocity(p.getEyeLocation().getDirection());
                dragon.setRotation((p.getLocation().getYaw() + 180) % 360, p.getEyeLocation().getPitch());
                Location dragonHead = dragon.getLocation().clone().add(0, 2.5, 0);
                clearBlocksInFront(dragonHead, dragon.getEyeLocation().getDirection(), 5, 3);
                //dragon.getEyeLocation().getDirection().multiply(5);
            },0L,1L);
        }

        //RingEffect effect = new RingEffect(4000,2,p);
        //Main.getInstance().getEffectManager().addLoopEffect(effect);
    }

    public void clearBlocksInFront(Location origin, Vector direction, double length, int radius) {
        World world = origin.getWorld();
        if (world == null) return;

        direction = direction.clone().normalize();

        for (double distance = 1.0; distance <= length; distance += 0.8) {
            Location center = origin.clone().add(direction.clone().multiply(distance));

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {

                        Location blockLoc = center.clone().add(x, y, z);
                        Block block = blockLoc.getBlock();


                        block.setType(Material.AIR, false);
                    }
                }
            }
        }
    }
}
