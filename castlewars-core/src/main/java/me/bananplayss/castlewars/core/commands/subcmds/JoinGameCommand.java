package me.bananplayss.castlewars.core.commands.subcmds;

import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class JoinGameCommand implements SubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Game join";
    }

    @Override
    public String getSyntax() {
        return "/castlewars join <arena>";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.join";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Game g = Main.getInstance().getGameManager().KURVAANYAD();
        g.join((Player) sender);
    }
}