package me.bananplayss.castlewars.core.visibility;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerFlying;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockBreakAnimation;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityRelativeMove;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntityTeleport;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import me.bananplayss.castlewars.core.game.GameSpectateManagerImpl;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class MovePacketListener implements PacketListener {


    @Override
    public void onPacketSend(PacketSendEvent event) {
        if (event.getPacketType() != PacketType.Play.Server.BLOCK_BREAK_ANIMATION) {
            return;
        }

        if (event.getPlayer() == null) return;
        WrapperPlayServerBlockBreakAnimation packet = new WrapperPlayServerBlockBreakAnimation(event);

        ProfileImpl prof = ProfileCacheImpl.getProfileImpl(event.getUser().getUUID());
        if (prof == null) return;
        if (prof.getCurrentGame() == null) return;
        Player original = prof.getCurrentGame().getAllPlayers().stream().filter(p -> p.getEntityId()==packet.getEntityId()).findFirst().orElse(null);
        if(original == null) return;


        if(prof.getCurrentGame().getSpectateManager().isSpectating(original)) {
            event.setCancelled(true);
        }
    }


}