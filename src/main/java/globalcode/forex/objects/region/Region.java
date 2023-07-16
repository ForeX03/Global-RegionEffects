package globalcode.forex.objects.region;

import globalcode.forex.Main;
import globalcode.forex.utils.PotionEffectTranslator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Getter
@Setter
public class Region {
    String name;
    World world;
    Location loc1;
    Location loc2;
    HashMap<PotionEffectType, Integer> effects;

    public Region(String name, World world, Location loc1, Location loc2, HashMap<PotionEffectType, Integer> effects) {
        this.name = name;
        this.world = world;
        this.effects = effects;
        this.loc1 = loc1;
        this.loc2 = loc2;
    }

    public boolean isInRegion(Player p){
        Location pLoc = p.getLocation();
        int minX = Math.min(loc1.getBlockX(),loc2.getBlockX());
        int minY = Math.min(loc1.getBlockY(),loc2.getBlockY());
        int minZ = Math.min(loc1.getBlockZ(),loc2.getBlockZ());
        int maxX = Math.max(loc1.getBlockX(),loc2.getBlockX());
        int maxY = Math.max(loc1.getBlockY(),loc2.getBlockY());
        int maxZ = Math.max(loc1.getBlockZ(),loc2.getBlockZ());
        return pLoc.getWorld().equals(this.world) &&
                pLoc.getX()>=minX &&
                pLoc.getY()>=minY &&
                pLoc.getZ()>=minZ &&

                pLoc.getX()<=maxX &&
                pLoc.getY()<=maxY &&
                pLoc.getZ()<=maxZ;
    }

    public void saveRegion(){
        Main.getPlugin().getConfig().set("regions."+this.name+".world", this.world.getName());
        Main.getPlugin().getConfig().set("regions."+this.name+".pos1", this.loc1.getBlockX()+":"+this.loc1.getBlockY()+":"+this.loc1.getBlockZ());
        Main.getPlugin().getConfig().set("regions."+this.name+".pos2", this.loc2.getBlockX()+":"+this.loc2.getBlockY()+":"+this.loc2.getBlockZ());
        List<String> efekty = new ArrayList<>();
        for(PotionEffectType s : effects.keySet()){
            efekty.add(PotionEffectTranslator.translateToString(s) +" "+ (effects.get(s)+1));
        }
        Main.getPlugin().getConfig().set("regions."+this.name+".effects", efekty);
        Main.getPlugin().saveConfig();
    }
}
