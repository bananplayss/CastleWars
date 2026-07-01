package me.bananplayss.castlewars.api.utils.vectors;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Bukkit;

@ToString
@Getter @Setter
public class Vector3i {
    private int x,y,z;

    public Vector3i(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3i add(int x, int y, int z){
        this.y += y;
        this.x += x;
        this.z += z;

        return this;
    }

    public Vector3i multiply(int multiplier){
        this.x *= multiplier;
        this.y *= multiplier;
        this.z *= multiplier;

        return this;
    }

    public static Vector3i fromString(String string){
        String[] args = string.split(",");
        if(args.length != 3) {
            Bukkit.getLogger().severe("Cannot parse " + string + " to Vector3i");
            return null;
        }
        return new Vector3i(Integer.parseInt(args[0].trim()),Integer.parseInt(args[1].trim()),Integer.parseInt(args[2].trim()));
    }
}
