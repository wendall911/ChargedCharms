package chargedcharms.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import com.google.common.collect.ImmutableSet;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import technology.roughness.whitenoise.platform.Services;

import chargedcharms.common.effect.AbsorptionEffectProvider;
import chargedcharms.common.effect.GlowUpEffectProvider;
import chargedcharms.common.effect.ICharmEffectProvider;
import chargedcharms.common.effect.integration.BMEnchantedTotemEffectProvider;
import chargedcharms.common.effect.RegenerationEffectProvider;
import chargedcharms.common.effect.SpeedEffectProvider;
import chargedcharms.common.effect.VanillaTotemEffectProvider;
import chargedcharms.common.effect.WaterBreathingEffectProvider;
import chargedcharms.common.item.ChargedCharmsItems;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class CharmEffectProviders {

    private static final Map<Identifier, ICharmEffectProvider> EFFECT_PROVIDERS = new HashMap<>();
    private static Set<Identifier> TOTEMS;
    private static final Predicate<Item> IS_TOTEM = item -> TOTEMS.contains(Services.WN_PLATFORM.getResourceLocation(item));

    public static Predicate<Item> IS_CHARM = item -> EFFECT_PROVIDERS.containsKey(Services.WN_PLATFORM.getResourceLocation(item));

    public static void init() {
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.totemCharmId), new VanillaTotemEffectProvider());
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.enchantedTotemCharmId), new BMEnchantedTotemEffectProvider());

        TOTEMS = ImmutableSet.copyOf(EFFECT_PROVIDERS.keySet());

        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.regenerationCharmId), new RegenerationEffectProvider());
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.absorptionCharmId), new AbsorptionEffectProvider());
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.glowupCharmId), new GlowUpEffectProvider());
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.speedCharmId), new SpeedEffectProvider());
        EFFECT_PROVIDERS.put(prefix(ChargedCharmsItems.waterBreathingCharmId), new WaterBreathingEffectProvider());
    }

    public static Set<Identifier> getItems() {
        return ImmutableSet.copyOf(EFFECT_PROVIDERS.keySet());
    }

    public static Optional<ICharmEffectProvider> getEffectProvider(final Item item) {
        return Optional.ofNullable(EFFECT_PROVIDERS.get(Services.WN_PLATFORM.getResourceLocation(item)));
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
