package chargedcharms.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;

import chargedcharms.common.item.ChargedCharmsItems;

public class FabricItemModelProvider extends FabricModelProvider {

    public FabricItemModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(ChargedCharmsItems.regenerationCharm, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ChargedCharmsItems.absorptionCharm, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ChargedCharmsItems.glowupCharm, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ChargedCharmsItems.totemCharm, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ChargedCharmsItems.enchantedTotemCharm, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(ChargedCharmsItems.speedCharm, ModelTemplates.FLAT_ITEM);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {}

}
