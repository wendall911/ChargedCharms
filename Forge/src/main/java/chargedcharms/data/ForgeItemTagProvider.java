package chargedcharms.data;

import java.util.Collections;
import java.util.Objects;

import javax.annotation.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import chargedcharms.ChargedCharms;
import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.DataHelper;

public class ForgeItemTagProvider extends ItemTagsProvider {

    public ForgeItemTagProvider(DataGenerator gen, BlockTagsProvider blockTags, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(gen, blockTags, modId, existingFileHelper);
    }

    @Override
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Item Tags";
    }

    @Override
    protected void addTags() {
        TagBuilder charmTagBuilder = this.getOrCreateRawBuilder(getTagKey(loc("curios", "charged_charm")));

        CharmEffectProviders.getItems().forEach(loc -> DataHelper.addElement(charmTagBuilder, loc));
    }

    private static ResourceLocation loc(String namespace, String path) {
        return new ResourceLocation(namespace, path);
    }

    private static TagKey<Item> getTagKey(ResourceLocation loc) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createOptionalTagKey(loc, Collections.emptySet());
    }

}