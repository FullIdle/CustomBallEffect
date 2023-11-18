package me.fullidle.customballeffect.customballeffect.api.customEffect;

import com.pixelmonmod.pixelmon.entities.pokeballs.EntityPokeBall;
import net.minecraft.entity.player.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class MyEffect implements ISubEffect{
    @Override
    public String getName() {
        return "MyEffect";
    }

    @Override
    public String getInfo() {
        return "本插件自带的一个效果";
    }

    @Override
    public void doEffect(EntityPlayer player, EntityPokeBall ball) {
        UUID uniqueID = ball.getUniqueID();
        Entity entity = Bukkit.getEntity(uniqueID);
        entity.getWorld().playEffect(entity.getLocation(), Effect.SMOKE, 1);
    }
}
