package me.fullidle.customballeffect.customballeffect.api;

import me.fullidle.customballeffect.customballeffect.api.customEffect.ISubEffect;

public class CustomBallEffectAPI {
    //写一个给AllData类中的subEffect的列表添加ISubEffect类型的数据的静态方法
    public static void addSubEffects(ISubEffect... subEffect) {
        for (ISubEffect effect : subEffect) {
            AllData.subEffects.put(effect.getName(),effect);
        }
    }
}
