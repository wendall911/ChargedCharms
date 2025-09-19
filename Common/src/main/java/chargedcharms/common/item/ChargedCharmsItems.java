package chargedcharms.common.item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import chargedcharms.config.ConfigHandler;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public final class ChargedCharmsItems {

    private static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();

    public static final String regenerationCharmId = "charged_regeneration_charm";
    public static final String absorptionCharmId = "charged_absorption_charm";
    public static final String glowupCharmId = "charged_glowup_charm";
    public static final String totemCharmId = "charged_totem_charm";
    public static final String enchantedTotemCharmId = "charged_enchanted_totem_charm";
    public static final String speedCharmId = "charged_speed_charm";
    public static final String waterBreathingCharmId = "charged_water_breathing_charm";
    
    public static final Item regenerationCharm = make(
        regenerationCharmId,
        new ChargedCharmBase(getProps().durability(ConfigHandler.Common.regenCharges()))
    );
    public static final Item absorptionCharm = make(
        absorptionCharmId,
        new ChargedCharmBase(getProps().durability(ConfigHandler.Common.absorptionCharges()))
    );
    public static final Item glowupCharm = make(
        glowupCharmId,
        new ChargedCharmBase(getProps().durability(ConfigHandler.Common.glowUpCharges()))
    );
    public static final Item totemCharm = make(
        totemCharmId,
        new ChargedCharmBase(getProps().durability(ConfigHandler.Common.totemCharges()))
    );
    public static final Item enchantedTotemCharm = make(
        enchantedTotemCharmId,
        new EnchantedChargedCharmBase(getProps().durability(ConfigHandler.Common.totemCharges()))
    );
    public static final Item speedCharm = make(
        speedCharmId,
        new ChargedCharmBase(getProps().durability(ConfigHandler.Common.speedCharges()))
    );
    public static final Item waterBreathingCharm = make(
        waterBreathingCharmId,
        new ChargedCharmBase(getProps().durability(ConfigHandler.Common.waterBreathingCharges()))
    );

    private static <T extends Item> T make(String id, T item) {
        ResourceLocation loc = prefix(id);

        if (ALL.put(loc, item) != null) {
            throw new IllegalArgumentException("Duplicate Item: " + loc);
        }

        return item;
    }

    public static Item.Properties getProps() {
        return new Item.Properties();
    }

    public static void registerItems(BiConsumer<Item, ResourceLocation> consumer) {
        for (Map.Entry<ResourceLocation, Item> entry : ALL.entrySet()) {
            consumer.accept(entry.getValue(), entry.getKey());
        }
    }

    public static Map<ResourceLocation, Item> getAll() {
        return ALL;
    }

}
