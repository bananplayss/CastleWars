package me.bananplayss.castlewars.core.commands.subcmds;

import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StartGameCommand implements SubCommand {
    @Override
    public String getName() {
        return "start";
    }

    @Override
    public String getDescription() {
        return "Game indítás";
    }

    @Override
    public String getSyntax() {
        return "/castlewars start <arena>";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.start";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        BaseArena arena = Main.getInstance().getArenaManager().getArena(args[0]);
        Game g = Main.getInstance().getGameManager().createGame(arena);
        System.out.println(g.join((Player) sender));

        System.out.println("Létrehozva game: " + g.getId());
    }
}
