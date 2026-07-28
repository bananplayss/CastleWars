package me.bananplayss.castlewars.core.commands;

import me.bananplayss.castlewars.core.commands.subcmds.*;
import me.bananplayss.castlewars.core.commands.subcmds.kits.*;
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
        this.subCommands.add(new JoinGameCommand());
        this.subCommands.add(new CalculateCoordsCommand());
        this.subCommands.add(new AdrianCommand());
        this.subCommands.add(new LeaveGameCommand());

        this.subCommands.add(new ReloadCommand());

        this.subCommands.add(new KitCreateCommand());
        this.subCommands.add(new KitDeleteCommand());
        this.subCommands.add(new KitEditCommand());
        this.subCommands.add(new KitGiveCommand());
        this.subCommands.add(new KitPreviewCommand());
    }
}
