package chargedcharms.common.item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import io.wispforest.accessories.api.components.AccessoriesDataComponents;
import io.wispforest.accessories.api.components.AccessorySlotValidationComponent;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
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
        new ChargedCharmBase(getProps(regenerationCharmId).durability(ConfigHandler.Common.regenCharges()))
    );
    public static final Item absorptionCharm = make(
        absorptionCharmId,
        new ChargedCharmBase(getProps(absorptionCharmId).durability(ConfigHandler.Common.absorptionCharges()))
    );
    public static final Item glowupCharm = make(
        glowupCharmId,
        new ChargedCharmBase(getProps(glowupCharmId).durability(ConfigHandler.Common.glowUpCharges()))
    );
    public static final Item totemCharm = make(
        totemCharmId,
        new ChargedCharmBase(getProps(totemCharmId).durability(ConfigHandler.Common.totemCharges()))
    );
    public static final Item enchantedTotemCharm = make(
        enchantedTotemCharmId,
        new EnchantedChargedCharmBase(getProps(enchantedTotemCharmId).durability(ConfigHandler.Common.totemCharges()))
    );
    public static final Item speedCharm = make(
        speedCharmId,
        new ChargedCharmBase(getProps(speedCharmId).durability(ConfigHandler.Common.speedCharges()))
    );
    public static final Item waterBreathingCharm = make(
        waterBreathingCharmId,
        new ChargedCharmBase(getProps(waterBreathingCharmId).durability(ConfigHandler.Common.waterBreathingCharges()))
    );

    private static <T extends Item> T make(String id, T item) {
        ResourceLocation loc = prefix(id);

        if (ALL.put(loc, item) != null) {
            throw new IllegalArgumentException("Duplicate Item: " + loc);
        }

        return item;
    }

    public static Item.Properties getProps(String id) {
        return new Item.Properties()
            .setId(ResourceKey.create(Registries.ITEM, prefix(id)))
            .component(
                AccessoriesDataComponents.SLOT_VALIDATION,
                AccessorySlotValidationComponent.EMPTY.addValidSlot("charged_charm")
            );
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
