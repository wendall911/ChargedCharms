package chargedcharms.data.integration;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import dev.lambdaurora.lambdynlights.api.data.ItemLightSourceDataProvider;
import dev.lambdaurora.lambdynlights.api.item.ItemLuminance;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import chargedcharms.ChargedCharms;
import chargedcharms.common.item.ChargedCharmsItems;

public class DynamicLightsDataProvider extends ItemLightSourceDataProvider {

    public DynamicLightsDataProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryProvider) {
        super(packOutput, registryProvider, ChargedCharms.MODID);
    }

    @Override
    protected void generate(@NonNull Context context) {
        context.add(ChargedCharmsItems.glowupCharm, ItemLuminance.of(15), false);
    }

}
