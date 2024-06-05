package chargedcharms;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.mojang.serialization.Codec;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurio;

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
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::buildCreativeTabContents);
    }

    private void setup(final FMLCommonSetupEvent evt) {
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::attachCapabilities);
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

    private void attachCapabilities(AttachCapabilitiesEvent<ItemStack> evt) {
        if (!CharmEffectProviders.IS_CHARM.test(evt.getObject().getItem())) {
            return;
        }

        ICurio curio = new ICurio() {
            @Override
            public ItemStack getStack() {
                return evt.getObject();
            }

            @Override
            public boolean canEquipFromUse(SlotContext ctx) {
                return true;
            }
        };

        ICapabilityProvider provider = new ICapabilityProvider() {
            private final LazyOptional<ICurio> curioOpt = LazyOptional.of(() -> curio);

            @NotNull
            @Override
            public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap,
                     @NotNull Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
            }
        };

        evt.addCapability(CuriosCapability.ID_ITEM, provider);
    }

    private void registryInit(IEventBus eventBus) {
        DeferredRegister<Codec<? extends ICondition>> CONDITION_SERIALIZERS = deferred(ForgeRegistries.Keys.CONDITION_SERIALIZERS);

        bind(eventBus, ForgeRegistries.ITEMS.getRegistryKey(), ChargedCharmsItems::registerItems);
        bind(eventBus, ForgeRegistries.RECIPE_SERIALIZERS.getRegistryKey(), ChargedCharmsCrafting::registerRecipeSerializers);
        CONDITION_SERIALIZERS.register(eventBus);
        CONDITION_SERIALIZERS.register(ConfigResourceCondition.ID, () -> ConfigResourceCondition.CODEC);
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
