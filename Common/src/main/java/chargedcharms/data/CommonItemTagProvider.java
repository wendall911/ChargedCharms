package chargedcharms.data;

import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import chargedcharms.ChargedCharms;
import chargedcharms.common.CharmProviders;
import chargedcharms.common.DataHelper;

public class CommonItemTagProvider extends ItemTagsProvider {

    public CommonItemTagProvider(DataGenerator gen, BlockTagsProvider blockTagsProvider) {
        super(gen, blockTagsProvider);
    }

    @Override
    protected void addTags() {
        TagBuilder charmTagBuilder = this.getOrCreateRawBuilder(itemTag(new ResourceLocation(ChargedCharms.MODID, "charged_charms")));

        CharmProviders.getItems().forEach(loc -> DataHelper.addElement(charmTagBuilder, loc));
    }

    private static TagKey<Item> itemTag (ResourceLocation loc) {
        return TagKey.create(Registry.ITEM_REGISTRY, loc);
    }

}
