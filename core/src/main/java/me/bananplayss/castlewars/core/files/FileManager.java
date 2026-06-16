package me.bananplayss.castlewars.core.files;

import lombok.Getter;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.arena.BaseArenaImpl;
import me.bananplayss.castlewars.core.kobalib.KobaFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
public class FileManager {

    private final KobaFile config;
    private final KobaFile messages;

    public FileManager() {
        this.config = new KobaFile(Main.getInstance(), "config.yml");
        this.messages = new KobaFile(Main.getInstance(), "messages.yml");

        new KobaFile(Main.getInstance(), "arenas", "_example_ctf.yml");
        new KobaFile(Main.getInstance(), "arenas", "_example_ctz.yml");
        new KobaFile(Main.getInstance(), "arenas", "_example_btn.yml");
    }

    public void loadArenas() {
        File f = new File(Main.getInstance().getDataFolder(),"arenas");
        for (File file : f.listFiles()) {
            if(!file.getName().startsWith("_")) continue;
            if(!file.getName().endsWith(".yml")) continue;
            KobaFile kobaFile = new KobaFile(Main.getInstance(),file);
            Main.getInstance().getArenaManager().registerArena(new BaseArenaImpl(kobaFile));
        }
    }
}
