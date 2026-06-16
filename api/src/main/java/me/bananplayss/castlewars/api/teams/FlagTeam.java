package me.bananplayss.castlewars.api.teams;

import lombok.Getter;
import me.bananplayss.castlewars.api.utils.BoundingBox;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import me.bananplayss.castlewars.api.utils.vectors.VectorLocation;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.BlockFace;
import org.bukkit.block.banner.Pattern;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BannerMeta;

import java.util.List;

@Getter
public class FlagTeam extends AbstractTeam {

    private final Vector3i flagVector;
    private final BlockFace rotation;

    private final DyeColor baseColor;
    private final List<Pattern> patterns;

    private final ItemStack bannerItem;

    public FlagTeam(String key, String displayName, String color, VectorLocation spawn, BoundingBox boundingBox, Vector3i flagVector, BlockFace rotation, DyeColor baseColor, List<Pattern> patterns, ItemStack bannerItem) {
        super(key, displayName, color, spawn, boundingBox);
        this.flagVector = flagVector;
        this.rotation = rotation;
        this.baseColor = baseColor;
        this.patterns = patterns;
        this.bannerItem = bannerItem;
    }
}