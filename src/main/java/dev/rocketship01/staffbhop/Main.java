package dev.rocketship01.staffbhop;

import dev.rocketship01.staffbhop.listeners.BhopListener;
import dev.rocketship01.staffbhop.listeners.ToggleListener;
import dev.rocketship01.staffbhop.task.AirControlTask;
import dev.rocketship01.staffbhop.util.BhopToggleState;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {

    private final BhopToggleState toggleState = new BhopToggleState();

    private AirControlTask airControlTask;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        var pm = getServer().getPluginManager();
                pm.registerEvents(new ToggleListener(this, toggleState), this);
                pm.registerEvents(new BhopListener(this, toggleState), this);

        airControlTask = new AirControlTask(this, toggleState);
        airControlTask.runTaskTimer(this, 1L, 1L);
    }

    @Override
    public void onDisable() {
        if (airControlTask != null) {
            airControlTask.cancel();
        }
        toggleState.clearAll();
    }

}
