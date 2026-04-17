package chargedcharms.data;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.TagBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.DataHelper;
import chargedcharms.common.TagManager;
import chargedcharms.data.integration.ModIntegration;

public class ChargedCharmsItemTagProvider extends IntrinsicHolderTagsProvider<Item> {

    public ChargedCharmsItemTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, Registries.ITEM, lookupProvider, (item) -> item.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagBuilder charmTagBuilder = this.getOrCreateRawBuilder(TagManager.Items.CHARGED_CHARMS);

        CharmEffectProviders.init();
        CharmEffectProviders.getItems().forEach(loc -> DataHelper.addElement(charmTagBuilder, loc));

        this.tag(TagManager.Items.CHARM_FOODS_BLACKLIST)
            .add(Items.ROTTEN_FLESH)
            .add(Items.CHICKEN)
            .add(Items.SPIDER_EYE)
            .add(Items.SUSPICIOUS_STEW)
            .add(Items.PUFFERFISH);

        getOrCreateRawBuilder(TagManager.Items.ENCHANTED_TOTEMS)
            .addOptionalElement(ModIntegration.Items.BMO_ENCHANTED_TOTEM);
    }

}