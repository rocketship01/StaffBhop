package dev.rocketship01.staffbhop.listeners;

import dev.rocketship01.staffbhop.Main;
import dev.rocketship01.staffbhop.util.BhopToggleState;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public final class ToggleListener implements Listener {

    private static final String PERMISSION = "staffbhop.use";

    private final BhopToggleState toggleState;

    private final Component enabledMessage;
    private final Component disabledMessage;

    public ToggleListener(Main plugin, BhopToggleState toggleState) {
        this.toggleState = toggleState;

        var config = plugin.getConfig();
        this.enabledMessage = parse(config.getString("messages.enabled"));
        this.disabledMessage = parse(config.getString("messages.disabled"));
    }

    private static Component parse(String raw) {
        if (raw == null || raw.isEmpty()) {
            return null;
        }
        return LegacyComponentSerializer.legacyAmpersand().deserialize(raw);
    }

    @EventHandler(ignoreCancelled = true)
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission(PERMISSION)) {
            return;
        }
        event.setCancelled(true);

        player.updateInventory();

        boolean nowEnabled = toggleState.toggle(player.getUniqueId());
        Component message = nowEnabled ? enabledMessage : disabledMessage;

        if (message != null) {
            player.sendMessage(message);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        toggleState.clear(event.getPlayer().getUniqueId());
    }
}