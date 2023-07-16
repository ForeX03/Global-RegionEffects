package globalcode.forex;

import globalcode.forex.commands.EffectRegionCommand;
import globalcode.forex.listeners.InteractionEvent;
import globalcode.forex.listeners.QuitEvent;
import globalcode.forex.objects.region.RegionManager;
import globalcode.forex.tasks.RegionTask;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

@Plugin(name="Global-RegionEffects", version = "1.0-SNAPSHOT")
@Author("ForeX03")
@Commands(@Command(name="effectregion", desc="Komenda do ustawiania regionów"))
public class Main extends JavaPlugin {

    @Getter
    private static Main plugin;
    @Getter
    private static RegionTask regionTask;
    @Override
    public void onEnable(){
        plugin = this;
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        RegionManager.loadRegions();
        regionTask = new RegionTask();
        regionTask.start();
        PluginCommand command = getCommand("effectregion");
        command.setExecutor(new EffectRegionCommand());
        command.setTabCompleter(new EffectRegionCommand());
        Bukkit.getPluginManager().registerEvents(new InteractionEvent(), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvent(), this);
    }

    @Override
    public void onDisable(){
        regionTask.stop();
    }
}
