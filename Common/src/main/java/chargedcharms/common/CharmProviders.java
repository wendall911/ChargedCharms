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

import chargedcharms.common.effect.AbsorptionEffectProvider;
import chargedcharms.common.effect.GlowUpEffectProvider;
import chargedcharms.common.effect.ICharmEffectProvider;
import chargedcharms.common.effect.integration.BMEnchantedTotemEffectProvider;
import chargedcharms.common.effect.RegenerationEffectProvider;
import chargedcharms.common.effect.VanillaTotemEffectProvider;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.platform.Services;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class CharmProviders {

    private static final Map<ResourceLocation, ICharmEffectProvider> EFFECT_PROVIDERS = new HashMap<>();
    private static Set<ResourceLocation> TOTEMS;
    private static final Predicate<Item> IS_TOTEM = item -> TOTEMS.contains(Services.PLATFORM.getResourceLocation(item));

    public static Predicate<Item> IS_CHARM = item -> EFFECT_PROVIDERS.containsKey(Services.PLATFORM.getResourceLocation(item));

    public static void init() {
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.totemCharmId), new VanillaTotemEffectProvider());
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.enchantedTotemCharmId), new BMEnchantedTotemEffectProvider());

        TOTEMS = ImmutableSet.copyOf(EFFECT_PROVIDERS.keySet());

        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.regenerationCharmId), new RegenerationEffectProvider());
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.absorptionCharmId), new AbsorptionEffectProvider());
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.glowupCharmId), new GlowUpEffectProvider());
    }

    public static Set<ResourceLocation> getItems() {
        return ImmutableSet.copyOf(EFFECT_PROVIDERS.keySet());
    }

    public static Optional<ICharmEffectProvider> getEffectProvider(final Item item) {
        return Optional.ofNullable(EFFECT_PROVIDERS.get(Services.PLATFORM.getResourceLocation(item)));
    }

    public static boolean hasTotem(ItemStack stack) {
        if (IS_TOTEM.test(stack.getItem())) {
            return stack.getDamageValue() < stack.getMaxDamage();
        }

        return false;
    }

    public static boolean hasChargedCharm(ItemStack stack, Item charm) {
        return stack.is(charm) && hasCharge(stack);
    }

    private static boolean hasCharge(ItemStack stack) {
        return !stack.isEmpty() && (stack.getDamageValue() < stack.getMaxDamage());
    }

}