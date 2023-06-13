package chargedcharms;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import chargedcharms.data.integration.ModIntegration;
import chargedcharms.platform.Services;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.type.capability.ICurio;

import chargedcharms.client.CurioCharmRenderer;
import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.crafting.ChargedCharmsCrafting;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.data.recipe.ConfigResourceCondition;

import static chargedcharms.util.ResourceLocationHelper.prefix;

@Mod(ChargedCharms.MODID)
public class ChargedCharmsForge {

    public static final ResourceLocation EMPTY_CHARGED_CHARM_SLOT = prefix("item/empty_charged_charm_slot");

    public ChargedCharmsForge() {
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ChargedCharms.init();
        registryInit();
        eventBus.addListener(this::setup);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::enqueue);
        eventBus.addListener(this::buildCreativeTabContents);
    }

    private void setup(final FMLCommonSetupEvent evt) {
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::attachCapabilities);
    }

    private void clientSetup(final FMLClientSetupEvent evt) {
        for (ResourceLocation loc : CharmEffectProviders.getItems()) {
            Item item = ForgeRegistries.ITEMS.getValue(loc);

            if (item != null && item != Items.AIR) {
                CuriosRendererRegistry.register(item, CurioCharmRenderer::new);
            }
        }
    }

    private void enqueue(final InterModEnqueueEvent evt) {
        InterModComms.sendTo(
            CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE,
            () -> new SlotTypeMessage.Builder("charged_charm").icon(EMPTY_CHARGED_CHARM_SLOT).size(2).build()
        );
    }

    private void buildCreativeTabContents(CreativeModeTabEvent.BuildContents evt) {
        if (evt.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
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

            @Nonnull
            @Override
            public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap,
                    @Nullable Direction side) {
                return CuriosCapability.ITEM.orEmpty(cap, curioOpt);
            }
        };

        evt.addCapability(CuriosCapability.ID_ITEM, provider);
    }

    private void registryInit() {
        bind(ForgeRegistries.ITEMS.getRegistryKey(), ChargedCharmsItems::registerItems);

        bind(ForgeRegistries.RECIPE_SERIALIZERS.getRegistryKey(), ChargedCharmsCrafting::registerRecipeSerializers);
        bind(ForgeRegistries.RECIPE_SERIALIZERS.getRegistryKey(), ConfigResourceCondition::init);
    }

    private static <T> void bind(ResourceKey<Registry<T>> registry, Consumer<BiConsumer<T, ResourceLocation>> source) {
        FMLJavaModLoadingContext.get().getModEventBus().addListener((RegisterEvent event) -> {
            if (registry.equals(event.getRegistryKey())) {
                source.accept((t, rl) -> event.register(registry, rl, () -> t));
            }
        });
    }

}