package chargedcharms.data;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.DataHelper;

public class FabricItemTagProvider extends ItemTagsProvider {

    public FabricItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider) {
        super(dataGenerator, blockTagsProvider);
    }

    @Override
    protected void addTags() {
        TagKey<Item> charms = trinket("charged_charm/charm");
        TagKey<Item> altCharm = trinket("charged_charm/alt_charm");
        Tag.Builder charmTagBuilder = this.getOrCreateRawBuilder(charms);
        Tag.Builder altCharmTagBuilder = this.getOrCreateRawBuilder(altCharm);

        CharmEffectProviders.getItems().forEach(loc -> DataHelper.addElement(charmTagBuilder, loc));

        CharmEffectProviders.getItems().forEach(loc -> DataHelper.addElement(altCharmTagBuilder, loc));
    }

    private static ResourceLocation loc(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    private static TagKey<Item> trinket(String name) {
        return itemTag(loc("trinkets", name));
    }

    private static TagKey<Item> itemTag (ResourceLocation loc) {
        return TagKey.create(Registry.ITEM_REGISTRY, loc);
    }

}