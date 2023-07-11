package me.fortibrine.justpass;

import me.fortibrine.justpass.commands.CommandGenPass;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class JustPass extends JavaPlugin {

    @Override
    public void onEnable() {
        File config = new File(this.getDataFolder() + File.separator + "config.yml");
        if (!config.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }

        this.getCommand("genpass").setExecutor(new CommandGenPass(this));
    }

}
