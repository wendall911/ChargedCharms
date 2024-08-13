package chargedcharms;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.mojang.serialization.Codec;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;

import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurio;

import chargedcharms.client.CurioCharmRenderer;
import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.crafting.ChargedCharmsCrafting;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.config.ConfigHandler;
import chargedcharms.data.recipe.ConfigResourceCondition;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.platform.Services;

@Mod(ChargedCharms.MODID)
@Mod.EventBusSubscriber(modid = ChargedCharms.MODID)
public class ChargedCharmsNeoForge {

    public ChargedCharmsNeoForge(IEventBus eventBus) {
        ChargedCharms.init();
        registryInit(eventBus);
        eventBus.addListener(this::registerCapabilities);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::buildCreativeTabContents);
    }

    private void clientSetup(final FMLClientSetupEvent evt) {
        for (ResourceLocation loc : CharmEffectProviders.getItems()) {
            Item item = BuiltInRegistries.ITEM.get(loc);

            CuriosRendererRegistry.register(item, CurioCharmRenderer::new);
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

    private void registerCapabilities(final RegisterCapabilitiesEvent evt) {
        for (Item item : BuiltInRegistries.ITEM) {

            if (CharmEffectProviders.IS_CHARM.test(item)) {
                evt.registerItem(CuriosCapability.ITEM, (stack, ctx) -> new ICurio() {
                    @Override
                    public ItemStack getStack() {
                        return stack;
                    }

                    @Override
                    public boolean canEquipFromUse(SlotContext ctx) {
                        return true;
                    }
                }, item);
            }
        }
    }

    private static void registryInit(IEventBus eventBus) {
        DeferredRegister<Codec<? extends ICondition>> CONDITION_SERIALIZERS = deferred(NeoForgeRegistries.Keys.CONDITION_CODECS);

        bind(eventBus, Registries.ITEM, ChargedCharmsItems::registerItems);
        bind(eventBus, Registries.RECIPE_SERIALIZER, ChargedCharmsCrafting::registerRecipeSerializers);
        CONDITION_SERIALIZERS.register(eventBus);
        CONDITION_SERIALIZERS.register(ConfigResourceCondition.ID, () -> ConfigResourceCondition.CODEC);
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

    private static <T> DeferredRegister<T> deferred(ResourceKey<Registry<T>> key) {
        return deferred(key, ChargedCharms.MODID);
    }

    private static <T> DeferredRegister<T> deferred(ResourceKey<Registry<T>> key, String modid) {
        return DeferredRegister.create(key, modid);
    }

}
