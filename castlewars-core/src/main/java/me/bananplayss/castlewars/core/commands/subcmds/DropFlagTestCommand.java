package me.bananplayss.castlewars.core.commands.subcmds;

import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.core.game.FlagGameImpl;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class DropFlagTestCommand implements SubCommand {
    @Override
    public String getName() {
        return "drop";
    }

    @Override
    public String getDescription() {
        return "Flag drop";
    }

    @Override
    public String getSyntax() {
        return "/castlewars drop";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.drop";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Player p = (Player) sender;

        Profile prof = ProfileCacheImpl.getProfileImpl(p);
        Game game = prof.getCurrentGame();
        if(game == null) return;

        if(game instanceof FlagGameImpl fg) {
            fg.getFlagManager().drop(p);

            p.setGlowing(false);
            p.getInventory().setHelmet(null);
        }
    }
}