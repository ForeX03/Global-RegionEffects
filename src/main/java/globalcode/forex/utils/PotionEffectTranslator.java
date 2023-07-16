package globalcode.forex.utils;

import lombok.Getter;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;


public class PotionEffectTranslator {

    @Getter
    private static HashMap<String, PotionEffectType> map = new HashMap<String, PotionEffectType>(){{
        this.put("speed", PotionEffectType.SPEED);
        this.put("slowness", PotionEffectType.SLOW);
        this.put("haste", PotionEffectType.FAST_DIGGING);
        this.put("mining_fatigue", PotionEffectType.SLOW_DIGGING);
        this.put("strength", PotionEffectType.INCREASE_DAMAGE);
        this.put("instant_health", PotionEffectType.HEAL);
        this.put("instant_damage", PotionEffectType.HARM);
        this.put("jump_boost", PotionEffectType.JUMP);
        this.put("nausea", PotionEffectType.CONFUSION);
        this.put("regeneration", PotionEffectType.REGENERATION);
        this.put("resistance", PotionEffectType.DAMAGE_RESISTANCE);
        this.put("fire_resistance", PotionEffectType.FIRE_RESISTANCE);
        this.put("water_breathing", PotionEffectType.WATER_BREATHING);
        this.put("invisibility", PotionEffectType.INVISIBILITY);
        this.put("blindness", PotionEffectType.BLINDNESS);
        this.put("night_vision", PotionEffectType.NIGHT_VISION);
        this.put("hunger", PotionEffectType.HUNGER);
        this.put("weakness", PotionEffectType.WEAKNESS);
        this.put("poison", PotionEffectType.POISON);
        this.put("wither", PotionEffectType.WITHER);
        this.put("health_boost", PotionEffectType.HEALTH_BOOST);
        this.put("absorption", PotionEffectType.ABSORPTION);
        this.put("saturation", PotionEffectType.SATURATION);
        this.put("glowing", PotionEffectType.GLOWING);
        this.put("levitation", PotionEffectType.LEVITATION);
        this.put("luck", PotionEffectType.LUCK);
        this.put("bad_luck", PotionEffectType.UNLUCK);

    }};
    public static PotionEffectType translateToPotion(String s){
        return map.getOrDefault(s, null);
    }
    public static String translateToString(PotionEffectType pet){
        AtomicReference<String> effectname = new AtomicReference<>("");
        map.forEach((name, value)->{
            if(value.equals(pet)){
                effectname.set(name);
            }
        });
        return effectname.get();
    }
}
