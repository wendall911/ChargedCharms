package chargedcharms.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import chargedcharms.ChargedCharms;
import chargedcharms.data.integration.ChargedCharmsCuriosProvider;
import chargedcharms.data.recipe.NeoForgeRecipeProvider;

@EventBusSubscriber(modid = ChargedCharms.MODID)
public class NeoForgeDatagenInitializer {

    @SubscribeEvent
    public static void configureNeoForgeDatagen(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        PackOutput packOutput = gen.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        gen.addProvider(event.includeServer(), new NeoForgeRecipeProvider(packOutput, lookupProvider));
        gen.addProvider(event.includeServer(), new ChargedCharmsCuriosProvider(packOutput, event.getExistingFileHelper(), lookupProvider));
    }

}
