package chargedcharms.common.effect.integration;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import chargedcharms.common.effect.ICharmEffectProvider;

public class BMEnchantedTotemEffectProvider implements ICharmEffectProvider {

    @Override
    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.setHealth(livingEntity.getMaxHealth() / 2F);
        livingEntity.removeAllEffects();
        livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 500, 1));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 1200, 3));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 2000, 0));
        livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 2000, 0));
    }

}
