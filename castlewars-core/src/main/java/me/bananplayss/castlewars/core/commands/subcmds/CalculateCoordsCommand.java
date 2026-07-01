package me.bananplayss.castlewars.core.commands.subcmds;

import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CalculateCoordsCommand implements SubCommand {
    @Override
    public String getName() {
        return "calculatecoords";
    }

    @Override
    public String getDescription() {
        return "Calculate schematic coords";
    }

    @Override
    public String getSyntax() {
        return "/castlewars calculatecoords <origin_x> <origin_y> <origin_z> [--target]";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.calculatecoords";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Location location = p.getLocation();
        if(args.length == 4 && args[3].equalsIgnoreCase("--target")) {
            location = p.getTargetBlockExact(5).getLocation();
        }

        int originX = Integer.parseInt(args[0]);
        int originY = Integer.parseInt(args[1]);
        int originZ = Integer.parseInt(args[2]);

        int x = location.getBlockX() - originX;
        int y = location.getBlockY() - originY;
        int z = location.getBlockZ() - originZ;

        String coord = x + ", " + y + ", " + z;
        p.sendMessage(ColorParser.parse(
                "<click:copy_to_clipboard:" + coord +">Click here to copy</click>"
        ));
    }
}
