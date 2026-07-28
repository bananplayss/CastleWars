package me.bananplayss.castlewars.core.commands.subcmds;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.player.User;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityRelativeMove;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerSpawnEntity;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.effects.EffectManagerImpl;
import me.bananplayss.castlewars.core.effects.FlagProtectionEffect;
import me.bananplayss.castlewars.core.effects.RingFillEffect;
import me.bananplayss.castlewars.core.game.GameSpectateManagerImpl;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import me.bananplayss.castlewars.core.visibility.FakeNameTagManager;
import org.bukkit.Bukkit;
import org.bukkit.Color;
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

        ProfileImpl prof = ProfileCacheImpl.getProfileImpl(p);

       // ((GameSpectateManagerImpl) prof.getCurrentGame().getSpectateManager()).getNameTagManager().test(p);
        Main.getInstance().getEffectManager().addLoopEffect(new FlagProtectionEffect(5000, 250).end(eff -> System.out.println("End xd")), p.getLocation());
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
