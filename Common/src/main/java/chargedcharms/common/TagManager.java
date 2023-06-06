package chargedcharms.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class TagManager {

    public static class Items {

        public static final TagKey<Item> CHARGED_CHARMS = itemTag(prefix("charged_charms"));
        public static final TagKey<Item> CHARM_FOODS_BLACKLIST = itemTag(prefix("charm_foods_blacklist"));
        public static final TagKey<Item> ENCHANTED_TOTEMS = itemTag(prefix("enchanted_totems"));

        private static TagKey<Item> itemTag (ResourceLocation loc) {
            return TagKey.create(Registries.ITEM, loc);
        }
    }

}