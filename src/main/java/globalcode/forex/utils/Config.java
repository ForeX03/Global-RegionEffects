package globalcode.forex.utils;

import globalcode.forex.Main;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Set;

public class Config {
    public static Set<String> getSection(String name){
        return Main.getPlugin().getConfig().getConfigurationSection(name).getKeys(false);
    }
    public static HashMap<String, Object> getRegionData(String region){
        HashMap<String, Object> data = new HashMap<>();
        Main.getPlugin().getConfig().getConfigurationSection("regions."+region).getKeys(false).forEach(field ->{
            data.put(field, Main.getPlugin().getConfig().get(field));
        });
        return data;
    }
}
