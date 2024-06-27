package chargedcharms.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.neoforged.neoforge.common.data.ExistingFileHelper;

import chargedcharms.ChargedCharms;
import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.TagManager;

public class NeoForgeItemTagProvider extends ItemTagsProvider {

    public NeoForgeItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, TagsProvider<Block> blockTagProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTagProvider.contentsGetter(), modId, existingFileHelper);
    }

    @Override
    public String getName() {
        return ChargedCharms.MOD_NAME + " - Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider lookupProvider) {
        TagBuilder charmTagBuilder = this.getOrCreateRawBuilder(getTagKey(loc("curios", "charged_charm")));
        CharmEffectProviders.getItems().forEach(loc -> tag(TagManager.Items.CURIOS).addOptional(loc));

        ChargedCharms.LOGGER.warn("got here, now wtf?");
    }

    private static ResourceLocation loc(String namespace, String path) {
        return ResourceLocation.fromNamespaceAndPath(namespace, path);
    }

    private static TagKey<Item> getTagKey(ResourceLocation loc) {
        return TagKey.create(Registries.ITEM, loc);
    }

}
