package globalcode.forex.tasks;

import globalcode.forex.Main;
import globalcode.forex.objects.region.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class RegionTask extends BukkitRunnable {
    public RegionTask(){}

    public void start(){
        runTaskTimer(Main.getPlugin(), 20L, 20L);
    }
    public void stop(){
        cancel();
    }
    @Override
    public void run() {
        Bukkit.getOnlinePlayers().forEach(pl->{
            RegionManager.getRegions().forEach(region ->{
                if(region.isInRegion(pl)){
                    for(Map.Entry<PotionEffectType, Integer> effects : region.getEffects().entrySet()){
                        PotionEffect pe = new PotionEffect(effects.getKey(), 40, effects.getValue());
                        pl.addPotionEffect(pe);
                    }
                }
            });
        });
    }
}
