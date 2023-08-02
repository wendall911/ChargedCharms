package chargedcharms.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.Tag;
import net.minecraft.world.item.Items;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.DataHelper;
import chargedcharms.common.TagManager;
import chargedcharms.data.integration.ModIntegration;

public class CommonItemTagProvider extends ItemTagsProvider {

    public CommonItemTagProvider(DataGenerator gen, BlockTagsProvider blockTagsProvider) {
        super(gen, blockTagsProvider);
    }

    @Override
    protected void addTags() {
        Tag.Builder charmTagBuilder = this.getOrCreateRawBuilder(TagManager.Items.CHARGED_CHARMS);

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