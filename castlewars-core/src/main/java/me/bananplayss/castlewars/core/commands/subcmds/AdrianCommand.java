package me.bananplayss.castlewars.core.commands.subcmds;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.protocol.component.builtin.TypedEntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataType;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.protocol.potion.PotionTypes;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityEffect;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityMetadata;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfo;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.visibility.PlayerVisibilityManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class AdrianCommand implements SubCommand, PacketListener {
    @Override
    public String getName() {
        return "effectTest";
    }

    @Override
    public String getDescription() {
        return "UwUgecfaszgeci";
    }

    @Override
    public String getSyntax() {
        return "/castlewars effectTest ";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.effectTest";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of("köcsög_adrian");
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Player p = (Player) sender;
        Player target = Bukkit.getPlayer(args[1]);

        User user = PacketEvents.getAPI().getPlayerManager().getUser(p);


//        EntityData<Byte> a = new EntityData<>(0, EntityDataTypes.BYTE, (byte) 0x20);
//        WrapperPlayServerEntityMetadata packet = new WrapperPlayServerEntityMetadata(
//                user.getEntityId(),
//                List.of(a)
//        );

//        PlayerVisibilityManager visibilityManager = new PlayerVisibilityManager(Main.getInstance());
//        visibilityManager.hide(p, target, "ASD");

//        WrapperPlayServerEntityEffect packet = new WrapperPlayServerEntityEffect(
//                user.getEntityId(),
//                PotionTypes.INVISIBILITY,
//                1,
//                100,
//                (byte) 0x04
//        );
//
//        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//            PacketEvents.getAPI().getPlayerManager().sendPacket(onlinePlayer, packet);
//        }
    }
}
