package chargedcharms.data;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.DataHelper;

public class FabricItemTagProvider extends IntrinsicHolderTagsProvider<Item> {

    public FabricItemTagProvider(FabricPackOutput dataGenerator, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(dataGenerator, Registries.ITEM, registriesFuture, (item) -> item.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        TagKey<Item> charms = trinket("charged_charm/charm");
        TagBuilder charmTagBuilder = this.getOrCreateRawBuilder(charms);

        CharmEffectProviders.init();

        CharmEffectProviders.getItems().forEach(loc -> DataHelper.addElement(charmTagBuilder, loc));
    }

    private static Identifier loc(String namespace, String path) {
        return Identifier.fromNamespaceAndPath(namespace, path);
    }

    private static TagKey<Item> trinket(String name) {
        return itemTag(loc("trinkets", name));
    }

    private static TagKey<Item> itemTag (Identifier loc) {
        return TagKey.create(Registries.ITEM, loc);
    }

}