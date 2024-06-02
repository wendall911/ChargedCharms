package chargedcharms.data;

import net.minecraft.data.DataGenerator;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import chargedcharms.ChargedCharms;
import chargedcharms.data.recipe.NeoForgeRecipeProvider;

@EventBusSubscriber(modid = ChargedCharms.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NeoForgeDatagenInitializer {

    @SubscribeEvent
    public static void configureNeoForgeDatagen(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(event.includeServer(), new NeoForgeRecipeProvider(gen.getPackOutput(), event.getLookupProvider()));
    }

}
