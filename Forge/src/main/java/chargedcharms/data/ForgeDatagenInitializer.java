package chargedcharms.data;

import java.util.Collections;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
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
        ExistingFileHelper disabledHelper = new ExistingFileHelper(Collections.emptyList(), Collections.emptySet(), false, null, null);
        ForgeBlockTagsProvider blockTagsProvider = new ForgeBlockTagsProvider(gen, disabledHelper);

        gen.addProvider(new ForgeItemTagProvider(gen, blockTagsProvider, ChargedCharms.MODID, disabledHelper));
        gen.addProvider(new ForgeRecipeProvider(gen));
    }

}
