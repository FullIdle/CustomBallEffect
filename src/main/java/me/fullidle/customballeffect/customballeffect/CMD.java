package me.fullidle.customballeffect.customballeffect;

import me.fullidle.customballeffect.customballeffect.api.AllData;
import me.fullidle.customballeffect.customballeffect.api.customEffect.ISubEffect;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CMD implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1){
            sender.sendMessage(AllData.helpMsg);
            return false;
        }
        if (AllData.subCmd.contains(args[0].toLowerCase())) {
            switch (args[0].toLowerCase()) {
                case "reload": {
                    AllData.plugin.reloadConfig();
                    sender.sendMessage("§a重载成功!");
                    break;
                }
                case "list": {
                    sender.sendMessage("§a所有已注册的效果如下:");
                    for (ISubEffect s : AllData.subEffects.values()) {
                        sender.sendMessage("§7- " + s.getName() + "||info" + s.getInfo());
                    }
                    sender.sendMessage("§a所已配置的物品id如下:");
                    for (ItemStack value : AllData.conItem.values()) {
                        sender.sendMessage("§7- " + value.getType().toString());
                    }
                    sender.sendMessage("§8如果内容太多超出屏幕了找作者(作者:我懒得写,除非有人真写了很多配置和用了很多附属不然我不加翻页)");
                    break;
                }
                case "give":{
                    if (args.length < 3){
                        sender.sendMessage(AllData.helpMsg);
                        return false;
                    }
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        sender.sendMessage("§c玩家不在线!");
                        return false;
                    }
                    ItemStack clone = AllData.cacheIdItem(args[2]);
                    if (clone == null) {
                        sender.sendMessage("§c没有这个物品!");
                        return false;
                    }
                    clone = clone.clone();
                    player.getInventory().addItem(clone);
                    sender.sendMessage("§a已给与!");
                    break;
                }
                default: {
                    sender.sendMessage(AllData.helpMsg);
                    break;
                }
            }
        }else{
            sender.sendMessage(AllData.helpMsg);
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 1)return AllData.subCmd;
        if (args.length == 1)return AllData.subCmd.stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
        if (args.length == 2&&args[0].equalsIgnoreCase("give")) {
            List<String> onlinePlayerName = Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
            if (args[0].isEmpty()){
                return onlinePlayerName;
            }else{
                return onlinePlayerName.stream().filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
            }
        }
        if (args.length == 3){
            List<String> collect = new ArrayList<>(AllData.plugin.getConfig().getKeys(false));
            if (args[2].isEmpty()) {
                return collect;
            }else{
                return collect.stream().filter(s -> s.startsWith(args[2])).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }
}
