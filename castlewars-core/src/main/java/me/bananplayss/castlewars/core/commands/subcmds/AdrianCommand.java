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
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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

//        Profile prof =  ProfileCacheImpl.getProfileImpl(p);
//        if(prof.getCurrentGame() == null) return;

        Kys(args[0]).thenAcceptAsync((e) -> {
            p.getLocation().getBlock().setType(Material.STONE);
            System.out.println(e);
        }, r -> Bukkit.getScheduler().runTask(Main.getInstance(), r));
    }

    public CompletableFuture<String> Kys(String s) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return s;
        });
    }
}
