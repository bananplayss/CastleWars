package me.bananplayss.castlewars.core.map;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import lombok.Getter;
import me.bananplayss.castlewars.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Getter
public class ArenaPrefab {
    private File schematicFile;
    private String name;
    private Location origin;
    /*
        Todo: every location, etc meta data from the arena
     */
    public ArenaPrefab(File schematicFile, String name) {
        this.schematicFile = schematicFile;
        this.name = name;
        if (schematicFile == null || !schematicFile.exists()) {
            throw new IllegalArgumentException("Schematic file does not exist.");
        }
    }

    public void buildTo(Location location,Runnable callback) {
        World world = location.getWorld();
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
           try {
               ClipboardFormat format = ClipboardFormats.findByFile(schematicFile);
               ClipboardReader reader = format.getReader(new FileInputStream(schematicFile));
               Clipboard clipboard = reader.read();
               origin = new Location(world,clipboard.getOrigin().x(),clipboard.getOrigin().y(),clipboard.getOrigin().z());
               reader.close();
               com.sk89q.worldedit.world.World adaptedWorld = BukkitAdapter.adapt(world);
               EditSession session = WorldEdit.getInstance().newEditSession(adaptedWorld);
               Operation op = new ClipboardHolder(clipboard)
                       .createPaste(session)
                       .to(BlockVector3.at(location.getX(),location.getY(),location.getZ()))
                       .ignoreAirBlocks(false)
                       .copyEntities(false)
                       .copyBiomes(false)
                       .build();
               Operations.complete(op);
               if (callback != null) {
                   Bukkit.getScheduler().runTask(Main.getInstance(), callback);
               }


           } catch (IOException e) {
               e.printStackTrace();
           }
        });
    }


}
