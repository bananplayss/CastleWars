package me.bananplayss.castlewars.core.commands.subcmds;

import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.effects.BoundingBoxEffect;
import me.bananplayss.castlewars.core.effects.RingEffect;
import me.bananplayss.castlewars.core.game.FlagGameImpl;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

            double w = ft.getBoundingBox().getMaxX() - ft.getBoundingBox().getMinX();
            double h = ft.getBoundingBox().getMaxY() - ft.getBoundingBox().getMinY();
            double d = ft.getBoundingBox().getMaxZ() - ft.getBoundingBox().getMinZ();

            BoundingBoxEffect effect = new BoundingBoxEffect(Color.GREEN, 1000, w, h, d, 0.20, null);
            effect.setLooping(true);
            Main.getInstance().getEffectManager().addLoopEffect(effect, ft.getBoundingBox().getCenter().toLocation(p.getWorld()));
            Bukkit.broadcastMessage(ft.getBoundingBox().toString());
        }

        //RingEffect effect = new RingEffect(4000,2,p);
        //Main.getInstance().getEffectManager().addLoopEffect(effect);
    }
}
