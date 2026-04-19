package chargedcharms;

import java.util.function.BiConsumer;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;

import chargedcharms.common.component.ChargedCharmsComponents;
import chargedcharms.common.crafting.ChargedCharmsCrafting;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.data.recipe.ConfigResourceCondition;

public class ChargedCharmsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        registryInit();
    }

    private void registryInit() {
        ChargedCharmsItems.registerItems(bind(BuiltInRegistries.ITEM));
        ChargedCharmsCrafting.registerRecipeSerializers(bind(BuiltInRegistries.RECIPE_SERIALIZER));
        ChargedCharmsComponents.registerDataComponents();
        ConfigResourceCondition.register();
    }

    private static <T> BiConsumer<T, Identifier> bind(Registry<? super T> registry) {
        return (t, id) -> Registry.register(registry, id, t);
    }

}
