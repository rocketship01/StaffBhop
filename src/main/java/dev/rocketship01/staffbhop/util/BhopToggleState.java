package dev.rocketship01.staffbhop.util;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class BhopToggleState {

    private final Set<UUID> enabled = ConcurrentHashMap.newKeySet();

    private final Set<UUID> activeView = Collections.unmodifiableSet(enabled);

    public boolean isEnabled(UUID uuid) {
        return uuid != null && enabled.contains(uuid);
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

    public void clearAll() {

        enabled.clear();
    }

    public Set<UUID> active(){

        return activeView;
    }
}
