package me.bananplayss.castlewars.core.kobalib;

import lombok.AccessLevel;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Getter
public class KobaFile {

    @Getter(AccessLevel.NONE) private final JavaPlugin plugin;
    private final File cfg;

    private FileConfiguration config;
    private final DefaultConfigUtils defaultConfig;

    public KobaFile(JavaPlugin plugin, String ymlFile) {
        this.plugin = plugin;
        this.cfg = new File(plugin.getDataFolder(), ymlFile);
        setup();

        this.defaultConfig = new DefaultConfigUtils(this);
    }

    public KobaFile(JavaPlugin plugin, String folder, String file) {
        this.plugin = plugin;
        this.cfg = new File(plugin.getDataFolder(), folder + "/" + file);
        setup();

        this.defaultConfig = new DefaultConfigUtils(this);
    }

    public KobaFile(JavaPlugin plugin, File file) {
        this(plugin, file.getPath()
                .replace(plugin.getDataFolder().getPath(), "")
//                .replace("\\", File.separator)
        );
    }

    public void setup() {
        if (!cfg.exists()) {
            try {
                cfg.getParentFile().mkdirs();
                cfg.createNewFile();

                String path = cfg.getPath()
                        .replace(plugin.getDataFolder().getPath() + File.separator, "")
                        .replace("\\", File.separator)
                        .replace(File.separator, "/");
                InputStream in = this.plugin.getResource(path);
                FileOutputStream out = new FileOutputStream(cfg);

                if (in != null) {
                    try {
                        int n;
                        while ((n = in.read()) != -1) {
                            out.write(n);
                        }
                    } finally {
                        if (in != null) {
                            in.close();
                        }
                        if (out != null) {
                            out.close();
                        }
                    }
                }

            } catch (IOException e) {
            }
        }
        config = YamlConfiguration.loadConfiguration(cfg);
    }

    public void save() {
        try {
            config.save(cfg);
        } catch (IOException e) {
            System.out.println("Can't save language file");
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(cfg);
    }

    public List<String> getStringAsList(String... paths) {
        List<String> list = new ArrayList<>();

        for (String path : paths) {
            if(!this.config.contains(path)) continue;

            if(!this.config.getStringList(path).isEmpty()) {
                list.addAll(this.config.getStringList(path));
                continue;
            }
            list.add(this.config.getString(path));
        }

        return list;
    }

    public void delete() {
        cfg.delete();
    }

    public String getName() {
        return cfg.getName().replace(".yml", "");
    }
}
