package chargedcharms.common.effect;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import chargedcharms.config.ConfigHandler;

public class AbsorptionEffectProvider implements ICharmEffectProvider {

    public static final ArrayList<DamageSource> invalidDamageSources = new ArrayList<>(Arrays.asList(
            DamageSource.FALL,
            DamageSource.IN_WALL,
            DamageSource.CRAMMING,
            DamageSource.OUT_OF_WORLD,
            DamageSource.OUT_OF_WORLD,
            DamageSource.FALLING_BLOCK,
            DamageSource.DRAGON_BREATH
    ));

    @Override
    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.addEffect(
            new MobEffectInstance(
                MobEffects.ABSORPTION, ConfigHandler.Common.absorptionDuration() * 20,
                ConfigHandler.Common.absorptionAmplifier()
            )
        );
    }

}