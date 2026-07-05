package me.bananplayss.castlewars.api.profiles;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileStatistics {
    private final Profile profile;

    private int kills, deaths, matches, win, bannerCapture;

    public ProfileStatistics(Profile profile) {
        this.profile = profile;
    }

    public void addKill() {
        this.kills++;
    }

    public void addDeath() {
        this.deaths++;
    }
    public void addMatch() {
        this.matches++;
    }

    public void addWin() {
        this.win++;
    }

    public void addBannerCapture() {
        this.bannerCapture++;
    }
}
