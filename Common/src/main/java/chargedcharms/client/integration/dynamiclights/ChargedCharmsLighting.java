package chargedcharms.client.integration.dynamiclights;

import org.jspecify.annotations.NonNull;

import dev.lambdaurora.lambdynlights.api.DynamicLightsContext;
import dev.lambdaurora.lambdynlights.api.DynamicLightsInitializer;
import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;

import chargedcharms.common.item.ChargedCharmsItems;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class ChargedCharmsLighting implements DynamicLightsInitializer {

    public static final EntityLuminance.Type GlowUpCharmLuminance = EntityLuminance.Type.register(prefix(ChargedCharmsItems.glowupCharmId), GlowUpCharmEntity.MAP_CODEC);

    @Override
    public void onInitializeDynamicLights(@NonNull DynamicLightsContext dynamicLightsContext) {}

}
