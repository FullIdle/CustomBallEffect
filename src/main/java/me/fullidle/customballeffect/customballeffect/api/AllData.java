package me.fullidle.customballeffect.customballeffect.api;

import com.pixelmonmod.pixelmon.entities.pokeballs.EntityPokeBall;
import com.pixelmonmod.pixelmon.enums.items.EnumPokeballs;
import com.pixelmonmod.pixelmon.items.ItemPokeball;
import me.fullidle.customballeffect.customballeffect.Main;
import me.fullidle.customballeffect.customballeffect.api.customEffect.ISubEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AllData {
    public static Main plugin;
    public static ArrayList<EntityPokeBall> recordEntityBall = new ArrayList<>();
    public static Map<String, org.bukkit.inventory.ItemStack> conItem = new HashMap<>();
    public static Map<String,ISubEffect> subEffects = new HashMap<>();
    public static ArrayList<String> subCmd = new ArrayList<>(Arrays.asList(
            "help",
            "reload",
            "list",
            "give"
    ));
    public static String[] helpMsg = new String[]{
            "§7help:显示帮助信息",
            "§7reload:重载插件",
            "§7list:列出已注册效果和已配置物品id",
            "§7give <Player> <配置中ID>:给与已经配置好的物品"
    };
    public static ArrayList<String> getItemStackSubEffectNames(ItemStack itemStack){
        ArrayList<String> list = new ArrayList<>();
        NBTTagCompound nbt = itemStack.getTagCompound();
        if (nbt == null||!nbt.hasKey("fiCustomBallEffect")) {
            return list;
        }
        NBTTagList nbtList = (NBTTagList) nbt.getTag("fiCustomBallEffect");
        for (NBTBase nbtBase : nbtList) {
            list.add(((NBTTagString) nbtBase).getString());
        }
        return list;
    }

    /**
     * @apiNote 本插件允许的时候不能立刻执行reload需要等待所有插件都启用后
     */
    public static void reload(){
        reloadConItem();
    }

    private static void reloadConItem(){
        conItem.clear();
        for (String key : plugin.getConfig().getKeys(false)) {
            cacheIdItem(key);
        }
    }

    public static org.bukkit.inventory.ItemStack cacheIdItem (String id){
        if (conItem.get(id) != null){
            return conItem.get(id);
        }
        String type = plugin.getConfig().getString(id + ".type");
        if (type == null){
            return null;
        }
        String name = plugin.getConfig().getString(id + ".name").replace("&","§");
        List<String> subEffect = plugin.getConfig().getStringList(id + ".subeffect");
        List<String> lore = plugin.getConfig().getStringList(id + ".lore").stream()
                .flatMap(s -> {
                    if (s.contains("{effect}")) {
                        return subEffect.stream()
                                .map(string -> s.replace("{effect}",AllData.subEffects.get(string).getInfo()).replace("&", "§"));
                    } else {
                        return Stream.of(s.replace("&", "§"));
                    }
                })
                .collect(Collectors.toList());
        EnumPokeballs enumPokeballs = EnumPokeballs.valueOf(type);
        ItemPokeball item = enumPokeballs.getItem();
        ItemStack itemStack = new ItemStack(item);
        NBTTagList nbtBases = new NBTTagList();
        NBTTagCompound nbt = new NBTTagCompound();
        for (String s : subEffect) {
            nbtBases.appendTag(new NBTTagString(s));
        }
        nbt.setTag("fiCustomBallEffect",nbtBases);
        itemStack.setTagCompound(nbt);
        org.bukkit.inventory.ItemStack bukkitCopy = CraftItemStack.asBukkitCopy((net.minecraft.server.v1_12_R1.ItemStack) (Object)itemStack);
        ItemMeta itemMeta = bukkitCopy.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(lore);
        bukkitCopy.setItemMeta(itemMeta);
        conItem.put(id,bukkitCopy);
        return bukkitCopy;
    }
}
