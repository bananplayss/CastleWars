package me.bananplayss.castlewars.core.digging;

import com.cryptomorin.xseries.XPotion;
import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketReceiveEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerAbilities;
import com.github.retrooper.packetevents.wrapper.play.client.WrapperPlayClientPlayerDigging;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerBlockBreakAnimation;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.Tasks;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BannerDiggingManager implements PacketListener {

    private final Map<UUID, BukkitTask> diggingTasks;

    public BannerDiggingManager() {
        this.diggingTasks = new HashMap<>();
    }

    private void startBreaking(Player player, Block block) {
        long breakTime = 5000;
        long ticks = breakTime / 10 / 50;
        long startTime = System.currentTimeMillis();

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), new Runnable() {
            int stage = 0;

            @Override
            public void run() {
                if (stage >= 10) {
//                    block.setType(Material.AIR);
                    player.sendMessage("Banner broken!");

                    BlockBreakEvent event = new BlockBreakEvent(block, player);
                    Bukkit.getPluginManager().callEvent(event);

                    cancelBreaking(player, block);
//                    System.out.println("Eltelt ido: " + (System.currentTimeMillis() - startTime));
                    return;
                }

                sendBreakAnimation(player, block, stage);
                stage++;
            }

        }, 0L, ticks); // 6 tick = 300ms

        diggingTasks.put(player.getUniqueId(), task);
    }


    private void cancelBreaking(Player player, Block block) {
        BukkitTask task = diggingTasks.remove(player.getUniqueId());
        System.out.println("Digging cancelled");

        sendBreakAnimation(player, block, -1);
        if (task != null)
            task.cancel();
    }

    private void sendBreakAnimation(Player player, Block block, int stage) {
        int entityId = PacketEvents.getAPI().getPlayerManager().getUser(player).getEntityId();
        WrapperPlayServerBlockBreakAnimation packet = new WrapperPlayServerBlockBreakAnimation(
                entityId + 100000,
                SpigotConversionUtil.fromBukkitLocation(block.getLocation()).getPosition().toVector3i(),
                (byte) stage
        );

        PacketEvents.getAPI().getPlayerManager().sendPacket(player, packet);
    }


    @Override
    public void onPacketReceive(PacketReceiveEvent event) {
        if (event.getPacketType() != PacketType.Play.Client.PLAYER_DIGGING) return;

        WrapperPlayClientPlayerDigging packet = new WrapperPlayClientPlayerDigging(event);

        Player player = (Player) event.getPlayer();

        if (player == null) return;
        ProfileImpl prof = ProfileCacheImpl.getProfileImpl(player);
        if (prof == null) return;
        if (prof.getCurrentGame() == null) return;

        if (!(prof.getCurrentGame() instanceof FlagGame fg)) return;

        Block block = player.getWorld().getBlockAt(packet.getBlockPosition().getX(), packet.getBlockPosition().getY(), packet.getBlockPosition().getZ());
        switch (packet.getAction()) {
            case START_DIGGING -> {
                if (!block.getType().name().endsWith("_BANNER")) return;
                GameFlagTeam t = fg.getFlagManager().getFlagByBlock(block);
                if (t == null) return;

                event.setCancelled(true);
                if (t != prof.getTeam()) return;
                Tasks.run(() -> player.addPotionEffect(XPotion.MINING_FATIGUE
                        .buildPotionEffect(Integer.MAX_VALUE, 255)
                        .withAmbient(false).withParticles(false).withIcon(false)
                ));

                if (t.hasFlagProtection()) {
                    Tasks.run(() -> player.sendMessage("Védelem alatt van xd"));
                    sendBreakAnimation(player, block, -1);
                    return;
                }

                if (t.getFlagSpawn().getBlockX() == block.getX() && t.getFlagSpawn().getBlockY() == block.getY() && t.getFlagSpawn().getBlockZ() == block.getZ()) {
                    Tasks.run(() -> player.sendMessage("Mar a spawnon van!"));
                    sendBreakAnimation(player, block, -1);
                    return;
                }

                startBreaking(player, block);
            }

            case CANCELLED_DIGGING -> {
//                System.out.println("Potion elvétel ELVIEGL");
                Tasks.run(() -> player.removePotionEffect(XPotion.MINING_FATIGUE.getPotionEffectType()));
                cancelBreaking(player, block);
            }

            case FINISHED_DIGGING -> {
                event.setCancelled(true);
                System.out.println("FINISHED");
                // vanilla kliens jelzi, hogy szerinte kész
                // itt nem kell feltétlen csinálni semmit
            }
        }
    }
}
