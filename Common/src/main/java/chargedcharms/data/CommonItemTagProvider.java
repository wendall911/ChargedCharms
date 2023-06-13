package chargedcharms.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.world.item.Items;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.DataHelper;
import chargedcharms.common.TagManager;
import chargedcharms.data.integration.ModIntegration;
import net.minecraft.world.level.block.Block;

public class CommonItemTagProvider extends ItemTagsProvider {

    public CommonItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTagsProvider) {
        super(packOutput, lookupProvider, blockTagsProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagBuilder charmTagBuilder = this.getOrCreateRawBuilder(TagManager.Items.CHARGED_CHARMS);

        CharmEffectProviders.getItems().forEach(loc -> DataHelper.addElement(charmTagBuilder, loc));

        this.tag(TagManager.Items.CHARM_FOODS_BLACKLIST)
            .add(Items.ROTTEN_FLESH)
            .add(Items.CHICKEN)
            .add(Items.SPIDER_EYE)
            .add(Items.SUSPICIOUS_STEW)
            .add(Items.PUFFERFISH);

        this.tag(TagManager.Items.ENCHANTED_TOTEMS)
            .addOptional(ModIntegration.Items.BMO_ENCHANTED_TOTEM);
    }

}