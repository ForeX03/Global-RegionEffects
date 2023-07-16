package globalcode.forex.objects.region;

import globalcode.forex.Main;
import globalcode.forex.utils.PotionEffectTranslator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class RegionManager {
    private static HashMap<String, Region> regions = new HashMap<>();

    public static Region getRegion(String name){
        if(regions.containsKey(name)) return regions.get(name);
        return null;
    }
    public static Region createRegion(String name, String world, Integer x1, Integer y1, Integer z1, Integer x2, Integer y2, Integer z2, HashMap<PotionEffectType, Integer> effects){
        Location loc1 = new Location(Bukkit.getWorld(world), x1, y1, z1);
        Location loc2 = new Location(Bukkit.getWorld(world), x2, y2, z2);
        Region region = new Region(name, Bukkit.getWorld(world), loc1, loc2, effects);
        region.saveRegion();
        regions.put(name, region);
        return region;
    }
    public static Region createRegion(String name, String world, Location loc1, Location loc2, HashMap<PotionEffectType, Integer> effects){
        Region region = new Region(name, Bukkit.getWorld(world), loc1, loc2, effects);
        region.saveRegion();
        regions.put(name, region);
        return region;
    }
    public static List<Region> getRegions(){
        List<Region> regionList = new ArrayList<>(regions.values());
        return regionList;
    }
    public static boolean removeRegion(String name){
        if(regions.containsKey(name)){
            Main.getPlugin().getConfig().set("regions."+name, null);
            Main.getPlugin().saveConfig();
            regions.remove(name);
            return true;
        } else {
            return false;
        }
    }
    public static void loadRegions(){
        int i = 0;
        if(Main.getPlugin().getConfig().getConfigurationSection("regions")==null) return;
        for(String s : Main.getPlugin().getConfig().getConfigurationSection("regions").getKeys(false)){
            Main.getPlugin().getLogger().log(Level.INFO, "Ładowanie regionu "+s+"...");
            String world = Main.getPlugin().getConfig().getString("regions."+s+".world");
            String[] sloc1 = Main.getPlugin().getConfig().getString("regions."+s+".pos1").split(":");
            String[] sloc2 = Main.getPlugin().getConfig().getString("regions."+s+".pos2").split(":");
            Location loc1 = new Location(Bukkit.getWorld(world), Integer.parseInt(sloc1[0]), Integer.parseInt(sloc1[1]), Integer.parseInt(sloc1[2]));
            Location loc2 = new Location(Bukkit.getWorld(world), Integer.parseInt(sloc2[0]), Integer.parseInt(sloc2[1]), Integer.parseInt(sloc2[2]));
            HashMap<PotionEffectType, Integer> effects = new HashMap<>();
            for(String effectlist : Main.getPlugin().getConfig().getStringList("regions."+s+".effects")){
                String[] spliter = effectlist.split(" ");
                effects.put(PotionEffectTranslator.translateToPotion(spliter[0]), Integer.parseInt(spliter[1])-1);
            }
            createRegion(s, world, loc1, loc2, effects);
            Main.getPlugin().getLogger().log(Level.INFO, "Region "+s+" załadowany pomyślnie!");
            i++;
        }
        Main.getPlugin().getLogger().log(Level.INFO, "Załadowano "+i+" regionów");
    }
}
