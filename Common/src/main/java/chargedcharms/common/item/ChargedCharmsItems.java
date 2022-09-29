package chargedcharms.common.item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import chargedcharms.platform.Services;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public final class ChargedCharmsItems {

    private static final Map<ResourceLocation, Item> ALL = new LinkedHashMap<>();

    public static final Item regenerationCharm = make("charged_regeneration_charm", new ChargedCharmBase(getProps().durability(5)));
    public static final Item absorptionCharm = make("charged_absorption_charm", new ChargedCharmBase(getProps().durability(5)));
    public static final Item glowupCharm = make("charged_glowup_charm", new ChargedCharmBase(getProps().durability(5)));
    public static final Item totemCharm = make("charged_totem_charm", new ChargedCharmBase(getProps().durability(5)));
    public static final Item enchantedTotemCharm = make("charged_enchanted_totem_charm", new EnchantedChargedCharmBase(getProps().durability(5)));

    private static <T extends Item> T make(String id, T item) {
        ResourceLocation loc = prefix(id);

        if (ALL.put(loc, item) != null) {
            throw new IllegalArgumentException("Duplicate Item: " + loc);
        }

        return item;
    }

    public static Item.Properties getProps() {
        return Services.PLATFORM.getProps();
    }

    public static void registerItems(BiConsumer<Item, ResourceLocation> consumer) {
        for (Map.Entry<ResourceLocation, Item> entry : ALL.entrySet()) {
            consumer.accept(entry.getValue(), entry.getKey());
        }
    }

}