package me.bananplayss.castlewars.core.database;

import me.bananplayss.castlewars.core.kobalib.database.IDatabase;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import org.bukkit.event.Listener;

import java.util.UUID;

public class SQLiteDatabase implements IDatabase {

    
    @Override
    public ProfileImpl loadProfile(UUID uuid) {
        return null;
    }
}
