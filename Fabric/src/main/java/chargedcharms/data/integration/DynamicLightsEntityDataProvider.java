package chargedcharms.data.integration;

import java.util.concurrent.CompletableFuture;

import dev.lambdaurora.lambdynlights.api.data.EntityLightSourceDataProvider;

import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.EntityType;

import chargedcharms.ChargedCharms;
import chargedcharms.client.integration.dynamiclights.GlowUpCharmEntity;

public class DynamicLightsEntityDataProvider extends EntityLightSourceDataProvider {

    public DynamicLightsEntityDataProvider(PackOutput packOutput, CompletableFuture<Provider> registryProvider) {
        super(packOutput, registryProvider, ChargedCharms.MODID);
    }

    @Override
    protected void generate(Context context) {
        context.add(EntityType.PLAYER, GlowUpCharmEntity.INSTANCE);
    }

}
