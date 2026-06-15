package me.bananplayss.castlewars.core.profiles;

import lombok.Getter;
import me.bananplayss.castlewars.api.profiles.Profile;

import java.util.UUID;

@Getter
public class ProfileImpl implements Profile {

    private final UUID uniqueId;

    public ProfileImpl(UUID uniqueId) {
        this.uniqueId = uniqueId;
    }
}
