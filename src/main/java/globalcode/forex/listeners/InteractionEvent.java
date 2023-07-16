package globalcode.forex.listeners;

import globalcode.forex.Main;
import globalcode.forex.objects.Selection;
import globalcode.forex.objects.user.User;
import globalcode.forex.objects.user.UserManager;
import globalcode.forex.utils.TextUtil;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractionEvent implements Listener {

    @EventHandler
    public void interact(PlayerInteractEvent e){
        if(!e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.getMaterial(Main.getPlugin().getConfig().getString("wand.item").toUpperCase()))) return;
        if(!e.getPlayer().hasPermission("efekty.region")) return;
        e.setCancelled(true);
        if(e.getAction().equals(Action.LEFT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            User u = UserManager.getUser(e.getPlayer());
            if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                Selection sel = u.getSelection();
                if (sel == null) {
                    sel = new Selection();
                }
                sel.setWorld(e.getPlayer().getWorld());
                sel.setX1(e.getClickedBlock().getX());
                sel.setY1(e.getClickedBlock().getY());
                sel.setZ1(e.getClickedBlock().getZ());
                sel.setCompleted(false);
                u.setSelection(sel);
                e.getPlayer().sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.pos1-selected")));
            } else {
                Selection sel = u.getSelection();
                if (sel == null) {
                    e.getPlayer().sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.pos1-needed")));
                    return;
                }
                sel.setX2(e.getClickedBlock().getX());
                sel.setY2(e.getClickedBlock().getY());
                sel.setZ2(e.getClickedBlock().getZ());
                sel.setCompleted(true);
                u.setSelection(sel);
                e.getPlayer().sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.pos2-selected")));
            }
        }
    }

}
