package chargedcharms.data;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

import chargedcharms.common.item.ChargedCharmsItems;

public class ChargedCharmsItemModelProvider extends FabricModelProvider {

    public ChargedCharmsItemModelProvider(FabricPackOutput output) {
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
        itemModelGenerator.generateFlatItem(ChargedCharmsItems.waterBreathingCharm, ModelTemplates.FLAT_ITEM);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {}

}
