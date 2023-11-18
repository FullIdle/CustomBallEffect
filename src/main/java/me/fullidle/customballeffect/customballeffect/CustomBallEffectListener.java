package me.fullidle.customballeffect.customballeffect;

import com.pixelmonmod.pixelmon.api.events.ThrowPokeballEvent;
import com.pixelmonmod.pixelmon.entities.pokeballs.EntityPokeBall;
import me.fullidle.customballeffect.customballeffect.api.AllData;
import me.fullidle.customballeffect.customballeffect.api.customEffect.ISubEffect;
import me.fullidle.customballeffect.customballeffect.api.events.PokeBallFlyTickEvent;
import me.fullidle.ficore.ficore.common.api.event.ForgeEvent;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.ThrowableImpactEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class CustomBallEffectListener implements Listener {
    @EventHandler
    public void onForge(ForgeEvent event){
        if (event.getForgeEvent() instanceof ThrowPokeballEvent){
            ThrowPokeballEvent e = (ThrowPokeballEvent) event.getForgeEvent();
            ArrayList<String> list = AllData.getItemStackSubEffectNames(e.itemStack);
            if (list.isEmpty())return;
            Bukkit.getScheduler().runTaskLater(AllData.plugin,()->{
                Optional<Entity> ball = e.player.world.loadedEntityList.stream().filter(en->en instanceof EntityPokeBall
                        && ((EntityPokeBall) en).getThrower().getUniqueID().equals(e.player.getUniqueID())
                        && !AllData.recordEntityBall.contains((EntityPokeBall) en)).findFirst();
                if (!ball.isPresent()) {
                    return;
                }
                EntityPokeBall pokeBall = (EntityPokeBall) ball.get();
                AllData.recordEntityBall.add(pokeBall);
                BukkitRunnable runnable = new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!pokeBall.isEntityAlive()){cancel();AllData.recordEntityBall.remove(pokeBall);}else{Bukkit.getPluginManager().callEvent(new PokeBallFlyTickEvent(e.player,pokeBall,list));}
                    }
                };
                runnable.runTaskTimer(AllData.plugin,0,1);
            },1);
        }
    }
    @EventHandler
    public void pokeBallFlyTick(PokeBallFlyTickEvent e){
        for (String sub : e.getSubEffectNames()) {
            ISubEffect effect = AllData.subEffects.get(sub);
            if (effect != null) {
                effect.doEffect(e.getPlayer(),e.getBall());
            }
        }
    }
}
