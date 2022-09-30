package chargedcharms.common.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class RegenerationEffectProvider implements ICharmEffectProvider {

    @Override
    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 0));
    }

}
