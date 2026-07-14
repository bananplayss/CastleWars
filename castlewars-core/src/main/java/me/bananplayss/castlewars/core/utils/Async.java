package me.bananplayss.castlewars.core.utils;

import me.bananplayss.castlewars.core.Main;
import org.bukkit.Bukkit;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Async {

    public static CompletableFuture<Void> run(Runnable runnable) {
        return CompletableFuture.runAsync(runnable);
    }

    public static <T> CompletableFuture<T> supply(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier);
    }

    public static void sync(Runnable runnable) {
        Bukkit.getScheduler().runTask(Main.getInstance(), runnable);
    }

    public static <T> CompletableFuture<T> execute(Supplier<T> supplier, Consumer<T> consumer) {
        CompletableFuture<T> result = new CompletableFuture<>();

        CompletableFuture.supplyAsync(supplier).thenAccept(value -> {
            Bukkit.getScheduler().runTask(Main.getInstance(), () -> {
                consumer.accept(value);
                result.complete(value);
            });
        });

        return result;
    }
}
