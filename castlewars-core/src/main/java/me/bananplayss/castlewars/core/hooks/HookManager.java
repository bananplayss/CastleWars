package me.bananplayss.castlewars.core.hooks;

import lombok.Getter;
import org.bukkit.Bukkit;

@Getter
public class HookManager {

    private boolean papi;
    private boolean packetEvents;
    private boolean tab;

    public HookManager() {
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.papi = true;
            Bukkit.getLogger().info("Hooked into PlaceholderAPI");
        }

        if(Bukkit.getPluginManager().getPlugin("PacketEvents") != null) {
            this.packetEvents = true;
            Bukkit.getLogger().info("Hooked into PacketEvents");
        }

        if(Bukkit.getPluginManager().getPlugin("TAB") != null) {
            this.tab = true;
            Bukkit.getLogger().info("Hooked into TAB");
        }
    }
}
