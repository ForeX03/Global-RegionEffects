package globalcode.forex.commands;

import globalcode.forex.Main;
import globalcode.forex.objects.Selection;
import globalcode.forex.objects.region.Region;
import globalcode.forex.objects.region.RegionManager;
import globalcode.forex.objects.user.UserManager;
import globalcode.forex.utils.PotionEffectTranslator;
import globalcode.forex.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;

public class EffectRegionCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String lab, String[] args) {
        if(cs instanceof Player){
            Player p = (Player) cs;
            if(!p.hasPermission("efekty.effectregion")){
                p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.nopermission")));
                return false;
            }
            if(args.length>=1){
                if(args[0].equalsIgnoreCase("wand")) {
                    if (!p.hasPermission("efekty.wand")) {
                        p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.nopermission")));
                        return false;
                    }
                    p.getInventory().addItem(new ItemStack(Material.getMaterial(Main.getPlugin().getConfig().getString("wand.item").toUpperCase())));
                    p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.wand-given")));
                    return false;
                } else if(args[0].equalsIgnoreCase("create")){
                    if(!(args.length>=3)){
                        p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.command-usage-create")));
                        return false;
                    }
                    String name = args[1];
                    ArrayList<String> regions = new ArrayList<>(RegionManager.getRegions().stream().map(Region::getName).collect(Collectors.toList()));
                    if(regions.contains(name)){
                        p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.region-exists")));
                        return false;
                    }
                    String[] enchantsplit = Arrays.copyOfRange(args, 2, args.length);
                    HashMap<PotionEffectType, Integer> enchants = new HashMap<>();
                    for(String effect : enchantsplit){
                        PotionEffectType pet;
                        int potionStrength = 0;
                        if(!effect.contains(":")){
                            pet = PotionEffectTranslator.translateToPotion(effect);
                        } else {

                            String[] spliter = effect.split(":");
                            try {
                                Integer.parseInt(spliter[1]);
                            } catch (Exception e) {
                                p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.integer-error")));
                                return false;
                            }
                            pet = PotionEffectTranslator.translateToPotion(spliter[0]);
                            if (pet == null) {
                                String list = "";
                                Set<String> lista = PotionEffectTranslator.getMap().keySet();
                                Iterator<String> listaI = lista.iterator();
                                for (int i = 0; i < lista.size() - 1; i++) {
                                    list += TextUtil.fixColor("&f" + listaI.next() + "&7, ");
                                }
                                list += TextUtil.fixColor("&f" + listaI.next());
                                p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.effect-list")).replace("[list]", list));
                                return false;
                            }
                            potionStrength = Integer.parseInt(spliter[1])-1;
                        }
                        enchants.put(pet, potionStrength);
                    }
                    Selection sel = UserManager.getUser(p).getSelection();
                    if(sel==null){
                        p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.no-region-selected")));
                        return false;
                    }
                    if(!sel.isCompleted()){
                        p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.pos2-needed")));
                        return false;
                    }
                    RegionManager.createRegion(name, sel.getWorld().getName(), sel.getPos1(), sel.getPos2(), enchants);
                    p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.region-created").replace("[name]", name)));
                } else if(args[0].equalsIgnoreCase("delete")){
                    if(args.length!=2){
                        p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.command-usage-delete")));
                        return false;
                    }
                    String name = args[1];
                    if(RegionManager.removeRegion(name)){
                        p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.region-deleted").replace("[name]", name)));
                    } else {
                        p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.no-region").replace("[name]", name)));
                    }
                } else if (args[0].equalsIgnoreCase("reload")) {
                    Main.getPlugin().reloadConfig();
                    p.sendMessage(TextUtil.fixColor(Main.getPlugin().getConfig().getString("messages.config-reloaded")));
                }
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender cs, Command cmd, String lab, String[] args) {
        if(cs instanceof Player){
            Player p = (Player) cs;
            if(p.hasPermission("efekty.effect")){
                if(args.length==1){
                    List<String> finalL = new ArrayList<>();
                    List<String> lista = Arrays.asList(
                            "delete",
                            "create",
                            "wand",
                            "reload"
                    );
                    for(String s : lista){
                        if(s.startsWith(args[0].toLowerCase())){
                            finalL.add(s);
                        }
                    }
                    return finalL;
                } else if(args.length==2){
                    if(args[0].toLowerCase().equalsIgnoreCase("create")){
                        return Collections.singletonList("nazwa regionu");
                    }
                    if(args[0].toLowerCase().equalsIgnoreCase("delete")){
                        List<String> finalL = new ArrayList<>();
                        ArrayList<String> regions = new ArrayList<>(RegionManager.getRegions().stream().map(Region::getName).collect(Collectors.toList()));
                        for(String s : regions){
                            if(s.startsWith(args[1].toLowerCase())){
                                finalL.add(s);
                            }
                        }
                        return finalL;
                    }

                } else if(args.length>=3){
                    if(args[0].toLowerCase().equalsIgnoreCase("create")){
                        List<String> finalL = new ArrayList<>();
                        for(String s : PotionEffectTranslator.getMap().keySet()) if(s.startsWith(args[args.length-1].toLowerCase())) finalL.add(s);
                        if(args.length>3) for(String s : Arrays.copyOfRange(args, 2, args.length))if(finalL.contains(s.split(":")[0]))finalL.remove(s.split(":")[0]);
                        return finalL;
                    }
                }
            }
        }
        return Collections.singletonList("");
    }
}
