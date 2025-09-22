package chargedcharms;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import technology.roughness.whitenoise.platform.Services;

//import chargedcharms.client.integration.arsnouveau.ArsDynamicLightsModule;
//import chargedcharms.client.integration.ryoamiclights.RyoamicDynamicLightsModule;
import chargedcharms.common.component.ChargedCharmsComponents;
import chargedcharms.common.crafting.ChargedCharmsCrafting;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.config.ConfigHandler;
import chargedcharms.data.recipe.ConfigResourceCondition;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.registries.ChargedCharmsNeoForgeRegistries;

@Mod(ChargedCharms.MODID)
@EventBusSubscriber(modid = ChargedCharms.MODID)
public class ChargedCharmsNeoForge {

    public ChargedCharmsNeoForge(IEventBus eventBus) {
        ChargedCharms.initConfig();
        registryInit(eventBus);
        ChargedCharms.init();
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::buildCreativeTabContents);
    }

    private void clientSetup(final FMLClientSetupEvent evt) {
        ChargedCharmsClient.init();

        if (Services.PLATFORM.isModLoaded(ModIntegration.ARS_MODID)) {
            //ArsDynamicLightsModule.setup();
        }

        if (Services.PLATFORM.isModLoaded(ModIntegration.RYOAMIC_MODID)) {
            //RyoamicDynamicLightsModule.setup();
        }
    }

    private void buildCreativeTabContents(BuildCreativeModeTabContentsEvent evt) {
        if (evt.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            for (Map.Entry<ResourceLocation, Item> entry : ChargedCharmsItems.getAll().entrySet()) {
                Item charm = entry.getValue();

                if (charm == ChargedCharmsItems.enchantedTotemCharm && !Services.PLATFORM.isModLoaded(ModIntegration.BMO_MODID)) {
                    charm = null;
                }

                if (charm != null) {
                    evt.accept(new ItemStack(charm));
                }
            }
        }
    }

    private static void registryInit(IEventBus eventBus) {
        bind(eventBus, Registries.ITEM, ChargedCharmsItems::registerItems);
        bind(eventBus, Registries.RECIPE_SERIALIZER, ChargedCharmsCrafting::registerRecipeSerializers);
        ChargedCharmsNeoForgeRegistries.CONDITION_SERIALIZERS_DEFERRED_REGISTER.register(eventBus);
        ChargedCharmsNeoForgeRegistries.CONDITION_SERIALIZERS_DEFERRED_REGISTER.register(ConfigResourceCondition.ID, () -> ConfigResourceCondition.CODEC);
        ChargedCharmsNeoForgeRegistries.COMPONENT_TYPE_DEFERRED_REGISTER.register(eventBus);
        ChargedCharmsComponents.registerDataComponents();
    }

    @SubscribeEvent
    public static void initConfig(final ServerStartingEvent event) {
        ConfigHandler.init();
    }

    private static <T> void bind(IEventBus eventBus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        eventBus.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

}
