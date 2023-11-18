package me.fullidle.customballeffect.customballeffect;

import me.fullidle.customballeffect.customballeffect.api.AllData;
import me.fullidle.customballeffect.customballeffect.api.CustomBallEffectAPI;
import me.fullidle.customballeffect.customballeffect.api.customEffect.MyEffect;
import me.fullidle.customballeffect.customballeffect.api.customEffect.ThunderEffect;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    /*快乐的写插件*/
    @Override
    public void onEnable() {
        AllData.plugin = this;

        CustomBallEffectAPI.addSubEffects(new MyEffect(),new ThunderEffect(),new ThunderEffect.Hit());

        PluginCommand command = getCommand("customballeffect");
        CMD cmd = new CMD();
        command.setExecutor(cmd);
        command.setTabCompleter(cmd);
        getServer().getPluginManager().registerEvents(new CustomBallEffectListener(),this);
        getLogger().info("§a插件载入成功");
    }

    @Override
    public void reloadConfig() {
        saveDefaultConfig();
        super.reloadConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("§c插件卸载成功");
    }
}
