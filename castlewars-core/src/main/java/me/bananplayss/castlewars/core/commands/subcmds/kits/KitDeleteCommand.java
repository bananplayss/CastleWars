package me.bananplayss.castlewars.core.commands.subcmds.kits;

import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.messages.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitDeleteCommand implements SubCommand {
    @Override
    public String getName() {
        return "deletekit";
    }

    @Override
    public String getDescription() {
        return "Delete kit";
    }

    @Override
    public String getSyntax() {
        return "/castlewars deletekit <kit>";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.deletekit";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of(Main.getInstance().getKitManager().getKits().keySet().toArray(new String[0]));
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        // castlewars deletekit <kit>
        if (!(sender instanceof Player p)) {
            Message.PLAYER_ONLY.builder().send(sender);
            return;
        }

        if(!Main.getInstance().getKitManager().isExists(args[0])) {
            // ToDo: Kit exists message xd
            sender.sendMessage("Kit nem létezik lol");
            return;
        }

        Main.getInstance().getKitManager().deleteKit(args[0]);

        Main.getInstance().getFileManager().getKits().getConfig().set(args[0], null);
        Main.getInstance().getFileManager().getKits().save();
        sender.sendMessage("Kit törölve ;)");
    }
}