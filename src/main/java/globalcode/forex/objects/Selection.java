package globalcode.forex.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.World;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Selection {
    World world;
    Location loc1;
    Location loc2;
    boolean completed;

    public Location getPos1(){
        return loc1;
    }
    public Location getPos2(){
        return loc2;
    }

}
