package me.fullidle.customballeffect.customballeffect.api.customEffect;

import com.pixelmonmod.pixelmon.client.models.smd.AnimationType;
import com.pixelmonmod.pixelmon.entities.pokeballs.EntityPokeBall;
import net.minecraft.entity.player.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class ThunderEffect implements ISubEffect{

    @Override
    public String getName() {
        return "ThunderEffect";
    }

    @Override
    public String getInfo() {
        return "全程有雷电效果";
    }

    @Override
    public void doEffect(EntityPlayer player, EntityPokeBall ball) {
        UUID uniqueID = ball.getUniqueID();
        Entity entity = Bukkit.getEntity(uniqueID);
        entity.getWorld().strikeLightning(entity.getLocation());
    }

    public static class Hit extends ThunderEffect{
        @Override
        public String getName() {
            return "ThunderEffect.Hit";
        }

        @Override
        public String getInfo() {
            return "命中的后落雷";
        }

        @Override
        public void doEffect(EntityPlayer player, EntityPokeBall ball) {
            if (ball.getAnimation() == AnimationType.BOUNCEOPEN) {
                super.doEffect(player, ball);
            }
        }
    }
}
