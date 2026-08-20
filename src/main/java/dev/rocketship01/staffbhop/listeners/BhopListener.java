package dev.rocketship01.staffbhop.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import dev.rocketship01.staffbhop.Main;
import dev.rocketship01.staffbhop.util.BhopToggleState;
import dev.rocketship01.staffbhop.util.Directions;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.util.Vector;

public final class BhopListener implements Listener {

    private static final String PERMISSION = "staffbhop.use";

    private final Main plugin;
    private final BhopToggleState toggleState;

    private final double speed;

    public BhopListener(Main plugin, BhopToggleState toggleState) {
        this.plugin = plugin;
        this.toggleState = toggleState;

        this.speed = clamp(plugin.getConfig().getDouble("bhop.speed", 0.35), 0.0, 2.0);
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    @EventHandler(ignoreCancelled = true)
    public void onJump(PlayerJumpEvent event) {
        Player player = event.getPlayer();

        if (!toggleState.isEnabled(player.getUniqueId())
                || player.getGameMode() != GameMode.CREATIVE
                || player.isGliding()
                || !player.hasPermission(PERMISSION)) {
            return;
        }

        Vector direction = Directions.fromInput(player, player.getCurrentInput());
        if (direction == null) {
            return;
        }

        plugin.getServer().getScheduler().runTask(plugin, () -> applySpeed(player, direction));
    }

    private void applySpeed(Player player, Vector direction) {
        if (!player.isOnline() || player.isDead()
                || player.getGameMode() != GameMode.CREATIVE
                || !toggleState.isEnabled(player.getUniqueId())) {
            return;
        }

        Vector velocity = player.getVelocity();

        player.setVelocity(new Vector(
                direction.getX() * speed,
                velocity.getY(),
                direction.getZ() * speed
        ));
    }
}