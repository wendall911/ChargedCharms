package chargedcharms.data;

import java.util.concurrent.CompletableFuture;

import org.jspecify.annotations.NonNull;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.TagBuilder;
import net.minecraft.world.item.Item;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.DataHelper;
import chargedcharms.common.TagManager;
import chargedcharms.data.integration.ModIntegration;

public class CommonItemTagProvider extends FabricTagsProvider<Item> {

    public CommonItemTagProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.ITEM, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        TagBuilder charmTagBuilder = this.getOrCreateRawBuilder(TagManager.Items.CHARGED_CHARMS);
        TagBuilder curiosTagBuilder = this.getOrCreateRawBuilder(TagManager.Items.CURIOS);

        CharmEffectProviders.init();
        CharmEffectProviders.getItems().forEach(loc -> DataHelper.addElement(charmTagBuilder, loc));
        CharmEffectProviders.getItems().forEach(loc -> DataHelper.addElement(curiosTagBuilder, loc));

        this.tag(TagManager.Items.CHARM_FOODS_BLACKLIST)
            .add(ItemIds.ROTTEN_FLESH)
            .add(ItemIds.CHICKEN)
            .add(ItemIds.SPIDER_EYE)
            .add(ItemIds.SUSPICIOUS_STEW)
            .add(ItemIds.PUFFERFISH);

        getOrCreateRawBuilder(TagManager.Items.ENCHANTED_TOTEMS)
            .addOptionalElement(ModIntegration.Items.BMO_ENCHANTED_TOTEM);
    }

}
