package me.bananplayss.castlewars.api.utils.vectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.Nullable;

@Getter
@Setter
@AllArgsConstructor
public class VectorLocation {
    private double x, y, z;
    private float yaw, pitch;

    public static @Nullable VectorLocation fromString(@Nullable String input) {
        String[] args = input.split(",");
        if(args.length != 5) {
            Bukkit.getLogger().severe("Cannot parse " + input + " to Vector3i");
            return null;
        }

        return new VectorLocation(
                Double.parseDouble(args[0].trim()),
                Double.parseDouble(args[1].trim()),
                Double.parseDouble(args[2].trim()),
                Float.parseFloat(args[3].trim()),
                Float.parseFloat(args[4].trim())
        );
    }
}
