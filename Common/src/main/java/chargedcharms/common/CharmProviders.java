package chargedcharms.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableSet;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import chargedcharms.common.integration.BMEnchantedTotemEffectProvider;
import chargedcharms.platform.Services;

public class CharmProviders {

    private static final Map<ResourceLocation, ICharmEffectProvider> EFFECT_PROVIDERS = new HashMap<>();
    private static Set<ResourceLocation> TOTEMS;

    public static Predicate<Item> IS_CHARM = item -> {
        ResourceLocation loc = Services.PLATFORM.getResourceLocation(item);

        return EFFECT_PROVIDERS.containsKey(loc) && EFFECT_PROVIDERS.get(loc) != null;
    };

    public static void init() {
        boolean bmLoaded = Services.PLATFORM.isModLoaded("biomemakeover");
        boolean addTotems = !Services.PLATFORM.isModLoaded("charmofundying");

        EFFECT_PROVIDERS.put(
            new ResourceLocation("minecraft", "totem_of_undying"),
            addTotems ? new VanillaTotemEffectProvider() : null
        );

        EFFECT_PROVIDERS.put(
            new ResourceLocation("biomemakeover", "enchanted_totem"),
            bmLoaded && addTotems ? new BMEnchantedTotemEffectProvider() : null
        );

        TOTEMS = ImmutableSet.copyOf(EFFECT_PROVIDERS.keySet());
    }

    public static Set<ResourceLocation> getItems() {
        return ImmutableSet.copyOf(EFFECT_PROVIDERS.keySet());
    }

    public static Optional<ICharmEffectProvider> getEffectProvider(final Item item) {
        return Optional.ofNullable(EFFECT_PROVIDERS.get(Services.PLATFORM.getResourceLocation(item)));
    }

    public static boolean hasTotem(ItemStack stack) {
        return TOTEMS.contains(Services.PLATFORM.getResourceLocation(stack.getItem()));
    }

}
