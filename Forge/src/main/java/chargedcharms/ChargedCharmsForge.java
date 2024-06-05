package chargedcharms;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import chargedcharms.common.component.ChargedCharmsComponents;
import chargedcharms.registries.ChargedCharmsForgeRegistries;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import chargedcharms.client.CurioCharmRenderer;
import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.crafting.ChargedCharmsCrafting;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.data.recipe.ConfigResourceCondition;
import chargedcharms.data.integration.ModIntegration;
import chargedcharms.platform.Services;

@Mod(ChargedCharms.MODID)
public class ChargedCharmsForge {

    public ChargedCharmsForge() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ChargedCharms.init();
        registryInit(eventBus);
        eventBus.addListener(this::registerCapabilities);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::buildCreativeTabContents);
    }

    private void clientSetup(final FMLClientSetupEvent evt) {
        for (ResourceLocation loc : CharmEffectProviders.getItems()) {
            Item item = ForgeRegistries.ITEMS.getValue(loc);

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

    private void registerCapabilities(final RegisterCapabilitiesEvent event) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (CharmEffectProviders.IS_CHARM.test(item)) {
                CuriosApi.registerCurio(item, new ICurioItem() {
                    @Override
                    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
                        return true;
                    }
                });
            }
        }
    }

    private void registryInit(IEventBus eventBus) {
        bind(eventBus, ForgeRegistries.ITEMS.getRegistryKey(), ChargedCharmsItems::registerItems);
        bind(eventBus, ForgeRegistries.RECIPE_SERIALIZERS.getRegistryKey(), ChargedCharmsCrafting::registerRecipeSerializers);
        ChargedCharmsForgeRegistries.CONDITION_SERIALIZERS_DEFERRED_REGISTER.register(eventBus);
        ChargedCharmsForgeRegistries.CONDITION_SERIALIZERS_DEFERRED_REGISTER.register(ConfigResourceCondition.ID, () -> ConfigResourceCondition.CODEC);
        ChargedCharmsForgeRegistries.COMPONENT_TYPE_DEFERRED_REGISTER.register(eventBus);
        ChargedCharmsComponents.registerDataComponents();
    }

    private static <T> void bind(IEventBus eventBus, ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        eventBus.addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

}
