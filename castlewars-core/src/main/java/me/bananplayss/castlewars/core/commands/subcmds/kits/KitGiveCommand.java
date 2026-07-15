package me.bananplayss.castlewars.core.commands.subcmds.kits;

import me.bananplayss.castlewars.api.kits.Kit;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.messages.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KitGiveCommand implements SubCommand {
    @Override
    public String getName() {
        return "givekit";
    }

    @Override
    public String getDescription() {
        return "Give kit";
    }

    @Override
    public String getSyntax() {
        return "/castlewars givekit <kit>";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.givekit";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of(Main.getInstance().getKitManager().getKits().keySet().toArray(new String[0]));
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        // castlewars givekit <kit>
        if (!(sender instanceof Player p)) {
            Message.PLAYER_ONLY.builder().send(sender);
            return;
        }

        if(!Main.getInstance().getKitManager().isExists(args[0])) {
            // ToDo: Kit exists message xd
            sender.sendMessage("Kit nem létezik lol");
            return;
        }

        Kit kit = Main.getInstance().getKitManager().getKit(args[0]);
        kit.give(p);

        sender.sendMessage("Kit lehívva;)");
    }
}