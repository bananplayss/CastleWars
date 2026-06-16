package me.bananplayss.castlewars.api.game.flags;

import lombok.Getter;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
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

    //            ki        melyik csapat flagjét
    private final Map<Player, GameFlagTeam> stolenFlags; // viszik

    private final Map<Location, GameFlagTeam> blockFlags; // letéve blockra

    public GameFlagManager() {
        this.stolenFlags = new HashMap<>();
        this.blockFlags = new HashMap<>();
    }

    public GameFlagTeam getFlagByBlock(@NotNull Block block) {
        if(this.blockFlags.containsKey(block.getLocation())) {
            return this.blockFlags.get(block.getLocation());
        }
        return null;
//        for (Map.Entry<Location, GameFlagTeam> entry : this.blockFlags.entrySet()) {
//
//        }
    }

    public void pickup(Player player, Block block) {
        GameFlagTeam team = this.getFlagByBlock(block);
        if(team != null) {
            this.blockFlags.remove(block.getLocation());
            block.setType(Material.AIR);
            player.getInventory().addItem(team.getTeam().getBannerItem());
        }
    }

    public void drop(Player player) {
        if(!this.stolenFlags.containsKey(player)) return;

        GameFlagTeam stolenFlag = this.stolenFlags.get(player);
        ItemStack banner = stolenFlag.getTeam().getBannerItem();

        long blockSearch = System.currentTimeMillis();
        for (int i = player.getLocation().getBlockY(); i > -64; i--) {
            Block block = player.getWorld().getBlockAt(player.getLocation().getBlockX(), i, player.getLocation().getBlockZ());
            if(block.isSolid()) {
                block.setType(banner.getType());

                Banner bannerState = (Banner) block.getState();
                BannerMeta meta = (BannerMeta) banner.getItemMeta();

                bannerState.setPatterns(meta.getPatterns());
                break;
            }
        }
        System.out.println("Block search took: " + (System.currentTimeMillis() - blockSearch));
    }
}
