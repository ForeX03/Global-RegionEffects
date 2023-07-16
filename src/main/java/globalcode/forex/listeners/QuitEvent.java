package globalcode.forex.listeners;

import globalcode.forex.objects.user.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void quit(PlayerQuitEvent e){
        UserManager.removeUser(e.getPlayer());
    }
}
