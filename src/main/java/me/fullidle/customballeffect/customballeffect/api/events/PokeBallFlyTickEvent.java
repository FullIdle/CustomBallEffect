package me.fullidle.customballeffect.customballeffect.api.events;

import com.pixelmonmod.pixelmon.entities.pokeballs.EntityPokeBall;
import lombok.Getter;
import net.minecraft.entity.player.EntityPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.ArrayList;

@Getter
public class PokeBallFlyTickEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final EntityPlayer player;
    private final EntityPokeBall ball;
    private final ArrayList<String> subEffectNames;

    public PokeBallFlyTickEvent(EntityPlayer player, EntityPokeBall ball, ArrayList<String> subEffectNames){
        this.player = player;
        this.ball = ball;
        this.subEffectNames = subEffectNames;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
