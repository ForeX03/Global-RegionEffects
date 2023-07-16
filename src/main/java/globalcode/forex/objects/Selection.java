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
    Integer x1;
    Integer x2;
    Integer y1;
    Integer y2;
    Integer z1;
    Integer z2;
    boolean completed;

    public Location getPos1(){
        return new Location(world, x1, y1, z1);
    }
    public Location getPos2(){
        return new Location(world, x2, y2, z2);
    }

}
