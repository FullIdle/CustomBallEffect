package me.fullidle.customballeffect.customballeffect.api.customEffect;

import com.pixelmonmod.pixelmon.entities.pokeballs.EntityPokeBall;
import net.minecraft.entity.player.EntityPlayer;

public interface ISubEffect {
    String getName();
    String getInfo();
    void doEffect(EntityPlayer player, EntityPokeBall ball);
}
