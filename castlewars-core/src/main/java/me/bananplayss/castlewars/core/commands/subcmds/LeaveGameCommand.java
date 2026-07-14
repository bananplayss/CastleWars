package me.bananplayss.castlewars.core.commands.subcmds;

import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class LeaveGameCommand implements SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Game leave";
    }

    @Override
    public String getSyntax() {
        return "/castlewars leave";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.leave";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        ProfileImpl prof = ProfileCacheImpl.getProfileImpl((Player) sender);
        prof.getCurrentGame().leave((Player) sender);
    }
}