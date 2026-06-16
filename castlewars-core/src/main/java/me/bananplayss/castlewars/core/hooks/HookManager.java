package me.bananplayss.castlewars.core.hooks;

import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class HookManager {

    private boolean papi;

    public HookManager() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.papi = true;
            Bukkit.getLogger().info("Hooked into PlaceholderAPI");
        }
    }
}
