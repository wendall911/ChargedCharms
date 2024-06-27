package chargedcharms.data;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.DataHelper;

public class FabricItemTagProvider extends ItemTagsProvider {

    public FabricItemTagProvider(FabricDataOutput dataGenerator, CompletableFuture<HolderLookup.Provider> registriesFuture, CompletableFuture<TagLookup<Block>> blockTagsProvider) {
        super(dataGenerator, registriesFuture, blockTagsProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagKey<Item> charms = trinket("charged_charm/charm");
        TagBuilder charmTagBuilder = this.getOrCreateRawBuilder(charms);

        CharmEffectProviders.init();

        CharmEffectProviders.getItems().forEach(loc -> DataHelper.addElement(charmTagBuilder, loc));
    }

    private static ResourceLocation loc(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    private static TagKey<Item> trinket(String name) {
        return itemTag(loc("trinkets", name));
    }

    private static TagKey<Item> itemTag (ResourceLocation loc) {
        return TagKey.create(Registries.ITEM, loc);
    }

}