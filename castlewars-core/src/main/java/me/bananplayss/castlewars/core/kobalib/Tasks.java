package me.bananplayss.castlewars.core.kobalib;

import me.bananplayss.castlewars.core.Main;
import org.bukkit.Bukkit;

public class Tasks {

    public static void run(Runnable runnable) {
        Bukkit.getScheduler().runTask(Main.getInstance(), runnable);
    }
}
