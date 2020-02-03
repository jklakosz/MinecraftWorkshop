package fr.jklakosz.testplugin;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main instance;

    @Override
    public void onEnable() {
        Main.instance = this;

        // Register event listener
        this.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
    }

    public static Main getInstance() {
        return instance;
    }

}
