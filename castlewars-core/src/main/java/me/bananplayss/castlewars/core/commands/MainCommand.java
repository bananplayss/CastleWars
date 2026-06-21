package me.bananplayss.castlewars.core.commands;

import me.bananplayss.castlewars.core.commands.subcmds.DropFlagTestCommand;
import me.bananplayss.castlewars.core.commands.subcmds.StartGameCommand;
import me.bananplayss.castlewars.core.kobalib.commands.BaseCommand;
import me.bananplayss.castlewars.core.kobalib.commands.argumentMatchers.ContainingAllCharsOfStringArgumentMatcher;
import me.bananplayss.castlewars.core.messages.Message;

public class MainCommand extends BaseCommand {

    public MainCommand() {
        super(Message.NO_PERMISSION.getMessage(), new ContainingAllCharsOfStringArgumentMatcher());
    }

    @Override
    protected void registerSubCommands() {
        this.subCommands.add(new StartGameCommand());
        this.subCommands.add(new DropFlagTestCommand());
    }
}
