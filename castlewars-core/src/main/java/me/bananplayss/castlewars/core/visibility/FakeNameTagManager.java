package me.bananplayss.castlewars.core.visibility;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListener;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.entity.data.EntityData;
import com.github.retrooper.packetevents.protocol.entity.data.EntityDataTypes;
import com.github.retrooper.packetevents.protocol.entity.type.EntityTypes;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.util.Vector3d;
import com.github.retrooper.packetevents.wrapper.play.server.*;
import io.github.retrooper.packetevents.util.SpigotConversionUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public final class FakeNameTagManager implements PacketListener {

    private static final double HEIGHT = 1.8;
    private final AtomicInteger entityId = new AtomicInteger(2_000_000_000);
    private final Map<Player, FakeNameTag> tags = new HashMap<>();

    private final Game game;

    private BukkitTask task;

    public FakeNameTagManager(Game game) {
        this.game = game;

        start();
    }

    public void test(Player owner) {
        FakeNameTag tag = createFakeTag(owner);

        PacketEvents.getAPI().getPlayerManager().sendPacket(owner, createSpawnPacket(tag));
        PacketEvents.getAPI().getPlayerManager().sendPacket(owner, createMetadataPacket(tag));
    }

    public void start() {
        task = Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            for (Map.Entry<Player, FakeNameTag> entry : tags.entrySet()) {

                FakeNameTag tag = entry.getValue();
                Player owner = tag.getOwner();

                Location loc = owner.getLocation().add(0, HEIGHT, 0);
                Location current = owner.getLocation().clone();
                Vector deltaVector = current.toVector().subtract(tag.previousLocation.toVector());
                if (deltaVector.lengthSquared() == 0) {
                    continue;
                }

                WrapperPlayServerEntityRelativeMove packet = new WrapperPlayServerEntityRelativeMove(
                        tag.entityId,
                        deltaVector.getX(),
                        deltaVector.getY(),
                        deltaVector.getZ(),
                        false
                );
                tag.setPreviousLocation(current.clone());
//                WrapperPlayServerEntityTeleport packet = new WrapperPlayServerEntityTeleport(
//                        tag.entityId,
//                        new Vector3d(loc.getX(), loc.getY(), loc.getZ()),
//                        0,
//                        0,
//                        false
//                );


                for (Player viewer : tag.viewers) {
                    PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
                }
                PacketEvents.getAPI().getPlayerManager().sendPacket(owner, packet);


            }
        }, 0L, 1L);
    }

    public FakeNameTag getTag(Player player) {
        return tags.get(player);
    }

    private WrapperPlayServerSpawnEntity createSpawnPacket(FakeNameTag tag) {
        Location loc = tag.owner.getLocation().add(0, HEIGHT, 0);
        return new WrapperPlayServerSpawnEntity(tag.entityId, tag.uuid, EntityTypes.ARMOR_STAND,
                new com.github.retrooper.packetevents.protocol.world.Location(loc.getX(), loc.getY(), loc.getZ(), 0, 0),
                0,
                0,
                null
        );
    }

    private WrapperPlayServerEntityMetadata createMetadataPacket(FakeNameTag tag) {
        List<EntityData<?>> data = List.of(
                new EntityData<>(0, EntityDataTypes.BYTE, (byte) 0x20), // Invisible armorstand
                new EntityData<>(2, EntityDataTypes.OPTIONAL_ADV_COMPONENT, Optional.of(ColorParser.parse("&7" + tag.owner.getName()))), // Name
                new EntityData<>(3, EntityDataTypes.BOOLEAN, true), // Custom name visible
                new EntityData<>(15, EntityDataTypes.BYTE, (byte) 0x10) // Marker armorstand
        );

        return new WrapperPlayServerEntityMetadata(tag.entityId, data);
    }

    private FakeNameTag createFakeTag(Player owner) {
        FakeNameTag tag = new FakeNameTag(entityId.getAndIncrement(), UUID.randomUUID(), owner);
        tags.put(owner, tag);
        return tag;
    }

    public void setSpectate(Player owner) {
        FakeNameTag tag = getTag(owner);
        if (tag == null) {
            tag = createFakeTag(owner);
        }

        tag.viewers.addAll(game.getSpectators());
        spawnNameTag(owner, tag);

        for (Map.Entry<Player, FakeNameTag> alreadySpectate : tags.entrySet()) {
            WrapperPlayServerSpawnEntity packet = createSpawnPacket(alreadySpectate.getValue());
            WrapperPlayServerEntityMetadata metadataPacket = createMetadataPacket(alreadySpectate.getValue());

            PacketEvents.getAPI().getPlayerManager().sendPacket(owner, packet);
            PacketEvents.getAPI().getPlayerManager().sendPacket(owner, metadataPacket);
            alreadySpectate.getValue().viewers.add(owner);
        }
    }

    public void removeSpectate(Player owner) {
        FakeNameTag tag = getTag(owner);
        if (tag == null) return;

        WrapperPlayServerDestroyEntities packet = new WrapperPlayServerDestroyEntities(tag.entityId);
//        PacketEvents.getAPI().getPlayerManager().sendPacket(owner, packet);
        tag.viewers.forEach(viewer -> PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet));

        tags.remove(owner);
    }

//    public void hideNameTag(Player owner, Player viewer) {
//        FakeNameTag tag = getTag(owner);
//        if (tag == null) return;
//
//        WrapperPlayServerDestroyEntities packet = new WrapperPlayServerDestroyEntities(tag.entityId);
//        PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
//
//        tag.viewers.remove(viewer);
//    }
//
//    public void destroyNameTag(Player owner) {
//        FakeNameTag tag = getTag(owner);
//        if (tag == null) return;
//
//        WrapperPlayServerDestroyEntities packet = new WrapperPlayServerDestroyEntities(tag.entityId);
//        tag.viewers.forEach(viewer -> {
//            PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
//        });
//
//        tags.remove(owner);
//    }

    private void spawnNameTag(Player owner, FakeNameTag tag) {
        com.github.retrooper.packetevents.protocol.world.Location packetLoc = SpigotConversionUtil.fromBukkitLocation(owner.getLocation());
        WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity(tag.entityId, tag.uuid, EntityTypes.ARMOR_STAND,
                new com.github.retrooper.packetevents.protocol.world.Location(packetLoc.getX(), packetLoc.getY(), packetLoc.getZ(), 0, 0),
                0,
                0,
                null
        );

        WrapperPlayServerEntityMetadata metadataPacket = createMetadataPacket(tag);
        for (Player viewer : game.getSpectators()) {
            PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
            PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, metadataPacket);
        }

//        PacketEvents.getAPI().getPlayerManager().sendPacket(owner, packet);
//        PacketEvents.getAPI().getPlayerManager().sendPacket(owner, metadataPacket);
    }

//    private void spawnNameTag(Game game, Player player) {
//        game.getAllPlayers().stream().filter(p -> game.getSpectateManager().isSpectating(p)).forEach(viewer -> {
//            // New show to old
//            PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
//            PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, metadataPacket);
//
//            System.out.println("Spawned nametag for: " + viewer.getName() + " to " + player.getName());
//            if (viewer == player) return;
//            // Old show to new
//            FakeNameTag otherSpectateTag = getTag(viewer);
//            if (otherSpectateTag != null) {
//                System.out.println("Spawned nametag for: " + player.getName() + " to " + otherSpectateTag.getOwner().getName());
//                otherSpectateTag.spawnNameTag(player, packetLoc);
//            }
//        });
//    }

    @Getter
    @AllArgsConstructor
    public static final class FakeNameTag {
        private final int entityId;
        private final UUID uuid;
        private final Player owner;
        @Setter
        private Location previousLocation;
        private final List<Player> viewers;

        public FakeNameTag(int entityId, UUID uuid, Player owner) {
            this.entityId = entityId;
            this.uuid = uuid;
            this.owner = owner;
            this.viewers = new ArrayList<>();
            this.previousLocation = owner.getLocation();
        }

//        public void spawnNameTag(Player target) {
//            com.github.retrooper.packetevents.protocol.world.Location loc = SpigotConversionUtil.fromBukkitLocation(owner.getLocation());
//            this.spawnNameTag(target, loc);
//        }
//
//        public void spawnNameTag(Player viewer, com.github.retrooper.packetevents.protocol.world.Location location) {
//            WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity(this.entityId, this.uuid, EntityTypes.ARMOR_STAND,
//                    new com.github.retrooper.packetevents.protocol.world.Location(location.getX(), location.getY(), location.getZ(), 0, 0),
//                    0,
//                    0,
//                    null
//            );
//
//
//            PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
//            updateMetadata(viewer);
//        }
//
//        private void updateMetadata(Player viewer) {
//            PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, createMetadataPacket(this));
//        }

//        public void destroy() {
//            WrapperPlayServerDestroyEntities packet = new WrapperPlayServerDestroyEntities(this.entityId);
//            PacketEvents.getAPI().getPlayerManager().sendPacket(this.owner, packet);
//            for (Player viewer : this.viewers) {
//                PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
//            }
//            this.viewers.clear();
//        }

        public void destroy(Player viewer) {
            WrapperPlayServerDestroyEntities packet = new WrapperPlayServerDestroyEntities(this.entityId);
            PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, packet);
            this.viewers.remove(viewer);
        }
    }
}