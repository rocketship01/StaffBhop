package dev.rocketship01.staffbhop.util;

import org.bukkit.Input;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public final class Directions {

    private static final double EPSILON = 1.0E-4;

    private Directions() {
    }

    public static Vector fromInput(Player player, Input input) {
        double x = 0;
        double z = 0;

        double yaw = Math.toRadians(player.getYaw());
        double forwardX = -Math.sin(yaw);
        double forwardZ = Math.cos(yaw);

        if (input.isForward())  { x += forwardX; z += forwardZ; }
        if (input.isBackward()) { x -= forwardX; z -= forwardZ; }

        if (input.isRight())    { x -= forwardZ; z += forwardX; }
        if (input.isLeft())     { x += forwardZ; z -= forwardX; }

        double lengthSquared = x * x + z * z;
        if (lengthSquared < EPSILON) {
            return null;
        }

        double length = Math.sqrt(lengthSquared);
        return new Vector(x / length, 0, z / length);
    }
}