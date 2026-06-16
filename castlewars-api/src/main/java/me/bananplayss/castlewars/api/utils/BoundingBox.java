package me.bananplayss.castlewars.api.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;

@Getter
@AllArgsConstructor
public class BoundingBox {

    private Vector3i bound1;
    private Vector3i bound2;
}
