package me.bananplayss.castlewars.core.kobalib.database;

import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;

import java.util.UUID;

public interface IDatabase {

    /**
     * This method must be called with async!
     */
    ProfileImpl loadProfile(UUID uuid);
}
