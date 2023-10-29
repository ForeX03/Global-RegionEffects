package globalcode.forex.objects.user;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class UserManager {

    private static HashMap<String, User> users = new HashMap<>();
    public static void registerNew(Player p){
        User user = new User(p.getName(), null);
        users.put(p.getName(), user);
    }
    public static User getUser(Player p){
        if(users.containsKey(p.getName())) return users.get(p.getName());
        registerNew(p);
        return users.get(p.getName());
    }
    public static void removeUser(Player p){
        users.remove(p.getName());
    }
}
