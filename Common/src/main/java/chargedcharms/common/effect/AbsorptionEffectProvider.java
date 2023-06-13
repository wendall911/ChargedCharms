package chargedcharms.common.effect;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import chargedcharms.config.ConfigHandler;

public class AbsorptionEffectProvider implements ICharmEffectProvider {

    public static final ArrayList<ResourceKey<DamageType>> invalidDamageSources = new ArrayList<>(Arrays.asList(
            DamageTypes.FALL,
            DamageTypes.IN_WALL,
            DamageTypes.CRAMMING,
            DamageTypes.FELL_OUT_OF_WORLD,
            DamageTypes.DRAGON_BREATH
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