package dev.rocketship01.staffbhop.util;

import org.bukkit.Input;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class Directions {

    private static final double EPSILON = 1.0E-4;

    private Directions() {
    }

    public static Vector fromInput(Player player, Input input) {
        Vector forward = player.getLocation().getDirection().setY(0);
        if (forward.lengthSquared() < EPSILON) {
            return null;
        }
        forward.normalize();

        Vector right = new Vector(-forward.getZ(), 0, forward.getX());
        Vector direction = new Vector();

        if (input.isForward())  direction.add(forward);
        if (input.isBackward()) direction.subtract(forward);
        if (input.isRight())    direction.add(right);
        if (input.isLeft())     direction.subtract(right);

        if (direction.lengthSquared() < EPSILON) {
            return null;
        }
        return direction.normalize();
    }
}