package me.bananplayss.castlewars.api.game.flags;

import lombok.Getter;
import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.api.effects.EffectManager;
import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.api.utils.Utils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Banner;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Getter
public class GameFlagManager {

    private final FlagGame flagGame;
    //            ki        melyik csapat flagjét
    private final Map<Player, GameFlagTeam> carriedFlags; // viszik
    private final Map<Location, GameFlagTeam> blockFlags; // letéve blockra
    private final Map<Location, Block> replacedBlocks; // leteszi a bannert pl fúre, vízbe, akkor

    public GameFlagManager(FlagGame flagGame) {
        this.flagGame = flagGame;

        this.carriedFlags = new HashMap<>();
        this.blockFlags = new HashMap<>();
        this.replacedBlocks = new HashMap<>();
    }

    public GameFlagTeam getFlagByBlock(@NotNull Block block) {
        if(!isFlag(block)) return null;
        return this.blockFlags.get(block.getLocation());
//        for (Map.Entry<Location, GameFlagTeam> entry : this.blockFlags.entrySet()) {
//
//        }
    }

    public GameFlagTeam canPickupAndGetTeam(Profile profile, Block block) {
        GameFlagTeam t = this.getFlagByBlock(block);
        if(t == null) return null;
        if(profile.getCurrentGame() == null) return null;
        if(profile.getTeam() == null) return null;
        return profile.getTeam().getTeam().getKey().equals(t.getTeam().getKey()) ? null : t;
    }

    /**
     * Törli a zászlót a földről, és carried flagba átteszi csak.
     * @param block
     * @return a felvett zászló csapatát
     */
    public GameFlagTeam pickup(Player p, Block block) {
        GameFlagTeam team = this.getFlagByBlock(block);
        if(team != null) {
            this.blockFlags.remove(block.getLocation());
            this.carriedFlags.put(p, team);
            block.setType(Material.AIR);
            team.setFlagState(GameFlagTeam.FlagState.CARRYING);
            CastleWarsAPI.EFFECT_MANAGER.get().removeLoopEffect(team.getRingEffect());
        }

        return team;
    }

    private void resetFlag(GameFlagTeam team) {
        team.setFlagState(GameFlagTeam.FlagState.SPAWN);
        CastleWarsAPI.EFFECT_MANAGER.get().removeLoopEffect(team.getRingEffect());
        this.flagGame.placeBanner(team, team.getFlagSpawn(), null);
        this.blockFlags.put(team.getFlagSpawn(), team);

    }

    public void resetFlag(Player player, GameFlagTeam team) {
        resetFlag(team);
        this.carriedFlags.remove(player);
    }

    public void resetFlag(Block block, GameFlagTeam team) {
        this.blockFlags.remove(block.getLocation());
        resetFlag(team);
    }

    public GameFlagTeam drop(Player player) {
        if(!this.carriedFlags.containsKey(player)) return null;

        GameFlagTeam stolenFlag = this.carriedFlags.get(player);

        long blockSearch = System.nanoTime();
        Location emptyLoc = Utils.findNearestBannerLocation(player.getLocation(), 5);
        if(!this.flagGame.getGame().isInside(emptyLoc)) {
//            emptyLoc = stolenFlag.getFlagSpawn();
            resetFlag(player, stolenFlag);
            System.out.println("Outside game, using flag spawn");
            return stolenFlag;
        }
        if(emptyLoc == null) {
//            emptyLoc = stolenFlag.getFlagSpawn();
            resetFlag(player, stolenFlag);
            System.out.println("Nem talál helyet, using flag spawn");
            return stolenFlag;
        }

        this.flagGame.placeBanner(stolenFlag, emptyLoc, Utils.getNearestBlockFace(player));

        this.carriedFlags.remove(player);
        this.blockFlags.put(emptyLoc, stolenFlag);
        stolenFlag.setFlagState(GameFlagTeam.FlagState.DROPPED);
        System.out.println("Block search took: " + (System.nanoTime() - blockSearch));
        return stolenFlag;
    }

    public Location getFlagLocation(GameFlagTeam team) {
        for (Map.Entry<Location, GameFlagTeam> entry : this.blockFlags.entrySet()) {
            if(entry.getValue().equals(team)) return entry.getKey();
        }

        return null;
    }

    public boolean isFlag(Block block) {
        if(!block.getType().name().contains("_BANNER")) return false;

        if(!this.blockFlags.containsKey(block.getLocation())) return false;

        return true;
    }
}
