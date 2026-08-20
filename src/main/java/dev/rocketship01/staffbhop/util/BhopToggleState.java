package dev.rocketship01.staffbhop.util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class BhopToggleState {

    private final Set<UUID> enabled = new HashSet<>();

    public boolean isEnabled(UUID uuid) {
        return enabled.contains(uuid);
    }

    public boolean toggle(UUID uuid) {
        if (enabled.remove(uuid)) {
            return false;
        }
        enabled.add(uuid);
        return true;
    }

    public void clear(UUID uuid) {
        enabled.remove(uuid);
    }
}
