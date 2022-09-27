package chargedcharms.common;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class VanillaTotemEffectProvider implements ICharmEffectProvider {

    @Override
    public void applyEffects(final LivingEntity livingEntity) {
        livingEntity.setHealth(1.0F);
        livingEntity.removeAllEffects();
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
    }

}
