package dev.rocketship01.staffbhop.listeners;

import dev.rocketship01.staffbhop.Main;
import dev.rocketship01.staffbhop.util.BhopToggleState;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public final class ToggleListener implements Listener {

    private static final String PERMISSION = "staffbhop.use";

    private final Main plugin;
    private final BhopToggleState toggleState;

    public ToggleListener(Main plugin, BhopToggleState toggleState) {
        this.plugin = plugin;
        this.toggleState = toggleState;
    }

    @EventHandler
    public void onSwapHands(PlayerSwapHandItemsEvent event) {
        if (isMode("SWAP_HANDS")) {
            handleToggle(event.getPlayer(), event);
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if (isMode("DROP_ITEM")) {
            handleToggle(event.getPlayer(), event);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        toggleState.clear(event.getPlayer().getUniqueId());
    }

    private void handleToggle(Player player, Cancellable event) {
        if (!player.hasPermission(PERMISSION)) {
            return;
        }
        event.setCancelled(true);

        boolean nowEnabled = toggleState.toggle(player.getUniqueId());
        String raw = plugin.getConfig().getString(
                nowEnabled ? "messages.enabled" : "messages.disabled", "");

        if (raw != null && !raw.isEmpty()) {
            player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(raw));
        }
    }

    private boolean isMode(String mode) {
        return mode.equalsIgnoreCase(plugin.getConfig().getString("toggle.mode", "SWAP_HANDS"));
    }
}