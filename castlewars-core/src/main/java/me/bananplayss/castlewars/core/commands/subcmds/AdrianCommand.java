package me.bananplayss.castlewars.core.commands.subcmds;

import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.effects.BoundingBoxVolumeEffect;
import me.bananplayss.castlewars.core.game.FlagGameImpl;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

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
            if(args.length != 0) {
                //Bukkit.broadcastMessage(LocationUtils.isInsideBlockOnly(p.getLocation(), ft.getCorner1(), ft.getCorner2(), true) +"");
            }
            BoundingBox box = BoundingBox.of(ft.getCorner1(), ft.getCorner2());
            BoundingBoxVolumeEffect e = new BoundingBoxVolumeEffect(Particle.VILLAGER_HAPPY,6000,box,1);
            e.setLooping(true);
            Location loc = box.getCenter().toLocation(p.getWorld());
            loc.setY(box.getMinY());
            Main.getInstance().getEffectManager().addLoopEffect(e, loc);
//            double w = ft.getBoundingBox().getMaxX() - ft.getBoundingBox().getMinX();
//            double h = ft.getBoundingBox().getMaxY() - ft.getBoundingBox().getMinY();
//            double d = ft.getBoundingBox().getMaxZ() - ft.getBoundingBox().getMinZ();
//            Location loc = ft.getBoundingBox().getCenter().toLocation(p.getWorld());
//            loc.setY(ft.getBoundingBox().getMinY());
            //tesó ez miért szar ennyire xddd ez jó ig. csak majd kéne csinálni, hogy ne block szélén menjen hanem közepén mert szemmel nem látod hogy most hogy van a szélén a blocknak /melyik blocknak xd
            //varja tesó a hight is el van kurva gg
//            BoundingBoxEffect effect = new BoundingBoxEffect(Color.GREEN, 1000, w, h, d, 0.20, null);
//            effect.setLooping(true);
//            Main.getInstance().getEffectManager().addLoopEffect(effect, loc);

        }

        //RingEffect effect = new RingEffect(4000,2,p);
        //Main.getInstance().getEffectManager().addLoopEffect(effect);
    }
}
