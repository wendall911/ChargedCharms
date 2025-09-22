package chargedcharms.data;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import net.minecraft.core.HolderLookup;

import chargedcharms.ChargedCharms;
import chargedcharms.common.item.ChargedCharmsItems;

public class ChargedCharmsLanguageProvider extends FabricLanguageProvider {

    protected ChargedCharmsLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(dataOutput, registryFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        translationBuilder.add("itemGroup." + ChargedCharms.MODID, "Charged Charms");
        addCharmItem(translationBuilder, ChargedCharmsItems.regenerationCharmId, "Regeneration");
        addCharmItem(translationBuilder, ChargedCharmsItems.absorptionCharmId, "Absorption");
        addCharmItem(translationBuilder, ChargedCharmsItems.glowupCharmId, "Glow Up");
        addCharmItem(translationBuilder, ChargedCharmsItems.totemCharmId, "Totem");
        addCharmItem(translationBuilder, ChargedCharmsItems.enchantedTotemCharmId, "Enchanted Totem");
        addCharmItem(translationBuilder, ChargedCharmsItems.speedCharmId, "Speed");
        addCharmItem(translationBuilder, ChargedCharmsItems.waterBreathingCharmId, "Water Breathing");
        translationBuilder.add("accessories.slot.charged_charm", "Charged Charm");
        translationBuilder.add("tooltip.charged_charm.charges", "Charges");
    }

    private void addCharmItem(TranslationBuilder builder, String id, String name) {
        builder.add("item." + ChargedCharms.MODID + "." + id, "Charged " + name + " Charm");
    }

}
