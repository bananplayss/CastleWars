package me.bananplayss.castlewars.core.files;

import lombok.Getter;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.KobaFile;

@Getter
public class FileManager {

    private final KobaFile config;
    private final KobaFile messages;

    public FileManager() {
        this.config = new KobaFile(Main.getInstance(), "config.yml");
        this.messages = new KobaFile(Main.getInstance(), "messages.yml");
    }
}
