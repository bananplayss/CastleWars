package me.bananplayss.castlewars.core.commands.subcmds;

import me.bananplayss.castlewars.api.arena.BaseArena;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.arena.BaseArenaImpl;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.messages.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand implements SubCommand {
    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reload configuration files";
    }

    @Override
    public String getSyntax() {
        return "/castlewars reload";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.reload";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Main.getInstance().getFileManager().reloadAll();
        Message.reloadAll();

        Main.getInstance().getConfigData().reload();
//        Main.getInstance().getKitManager().load();

        for (BaseArena value : Main.getInstance().getArenaManager().getArenas().values()) {
            value.reload();
        }

        Main.getInstance().getFileManager().loadArenas();

        Message.RELOADED.builder().send(sender);
    }
}