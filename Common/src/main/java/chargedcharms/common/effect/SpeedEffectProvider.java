package chargedcharms.common.effect;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import chargedcharms.config.ConfigHandler;

public class SpeedEffectProvider implements ICharmEffectProvider {

    @Override
    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.addEffect(
            new MobEffectInstance(
                MobEffects.MOVEMENT_SPEED,
                ConfigHandler.Common.speedDuration() * 20
            )
        );
    }

}
