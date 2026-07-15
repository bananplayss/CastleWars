package me.bananplayss.castlewars.core.commands.subcmds.kits;

import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.messages.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitEditCommand implements SubCommand {
    @Override
    public String getName() {
        return "editkit";
    }

    @Override
    public String getDescription() {
        return "Edit kit";
    }

    @Override
    public String getSyntax() {
        return "/castlewars editkit <kit>";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.editkit";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of(Main.getInstance().getKitManager().getKits().keySet().toArray(new String[0]));
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        // castlewars editkit <kit>
        if (!(sender instanceof Player p)) {
            Message.PLAYER_ONLY.builder().send(sender);
            return;
        }

        if(!Main.getInstance().getKitManager().isExists(args[0])) {
            // ToDo: Kit exists message xd
            sender.sendMessage("Kit nem létezik lol");
            return;
        }

        ItemStack[] items = p.getInventory().getContents();
        Main.getInstance().getKitManager().createKit(p, args[0]);

        Main.getInstance().getFileManager().getKits().getConfig().set(args[0], items);
        Main.getInstance().getFileManager().getKits().save();
        sender.sendMessage("Kit overrideolva ;)");
    }
}