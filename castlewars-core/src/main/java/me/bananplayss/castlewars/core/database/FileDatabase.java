package me.bananplayss.castlewars.core.database;

import me.bananplayss.castlewars.core.kobalib.database.IDatabase;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;

import java.util.UUID;

public class FileDatabase implements IDatabase {
    @Override
    public ProfileImpl loadProfile(UUID uuid) {
        return new ProfileImpl(uuid);
    }
}
