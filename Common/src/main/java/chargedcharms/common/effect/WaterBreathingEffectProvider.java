package chargedcharms.common.effect;

import net.minecraft.world.entity.LivingEntity;

public class WaterBreathingEffectProvider implements ICharmEffectProvider {

    @Override
    public void applyEffects(LivingEntity livingEntity) {
        livingEntity.setAirSupply(livingEntity.getMaxAirSupply());
    }

}
