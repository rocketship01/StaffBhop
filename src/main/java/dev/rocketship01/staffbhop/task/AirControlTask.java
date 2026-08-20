package dev.rocketship01.staffbhop.task;

import dev.rocketship01.staffbhop.Main;
import dev.rocketship01.staffbhop.util.BhopToggleState;
import dev.rocketship01.staffbhop.util.Directions;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;

public final class AirControlTask extends BukkitRunnable {

    private static final String PERMISSION = "staffbhop.use";

    private final Main plugin;
    private final BhopToggleState toggleState;

    private final double speed;
    private final double airControl;

    public AirControlTask(Main plugin, BhopToggleState toggleState) {
        this.plugin = plugin;
        this.toggleState = toggleState;

        var config = plugin.getConfig();
        this.speed = clamp(config.getDouble("bhop.speed", 0.35), 0.0, 2.0);
        this.airControl = clamp(config.getDouble("bhop.air-control", 0.4), 0.0, 1.0);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @Override
    public void run() {
        if (airControl <= 0.0 || toggleState.active().isEmpty()) {
            return;
        }

        var server = plugin.getServer();

        for (UUID uuid : toggleState.active()) {
            Player player = server.getPlayer(uuid);
            if (player != null) {
                steer(player);
            }
        }
    }

    private void steer(Player player) {
        if (player.getGameMode() != GameMode.CREATIVE) {
            return;
        }
        if (player.isOnGround() || player.isFlying()
                || player.isGliding() || player.isInsideVehicle()
                || player.isDead()) {
            return;
        }
        if (!player.hasPermission(PERMISSION)) {
            return;
        }

        Vector direction = Directions.fromInput(player, player.getCurrentInput());
        if (direction == null) {
            return;
        }

        Vector velocity = player.getVelocity();

        double targetX = direction.getX() * speed;
        double targetZ = direction.getZ() * speed;

        double newX = velocity.getX() + (targetX - velocity.getX()) * airControl;
        double newZ = velocity.getZ() + (targetZ - velocity.getZ()) * airControl;

        player.setVelocity(new Vector(newX, velocity.getY(), newZ));
    }
}