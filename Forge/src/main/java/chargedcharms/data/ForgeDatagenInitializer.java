package chargedcharms.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import chargedcharms.ChargedCharms;
import chargedcharms.data.recipe.ForgeRecipeProvider;

@Mod.EventBusSubscriber(modid = ChargedCharms.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ForgeDatagenInitializer {

    @SubscribeEvent
    public static void configureForgeDatagen(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();

        gen.addProvider(new ForgeRecipeProvider(gen));
    }

}
