package dev.rocketship01.staffbhop;

import dev.rocketship01.staffbhop.listeners.ToggleListener;
import dev.rocketship01.staffbhop.util.BhopToggleState;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {

    private final BhopToggleState toggleState = new BhopToggleState();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager()
                .registerEvents(new ToggleListener(this, toggleState), this);
    }

    @Override
    public void onDisable() {
        toggleState.clearAll();
    }

    public BhopToggleState getToggleState() {
        return toggleState;
    }

}
