package chargedcharms.data;

import java.util.concurrent.CompletableFuture;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import net.minecraft.core.HolderLookup;

import chargedcharms.ChargedCharms;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.common.Translations;

public class ChargedCharmsLanguageProvider extends FabricLanguageProvider {

    protected ChargedCharmsLanguageProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryFuture) {
        super(dataOutput, registryFuture);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder builder) {
        builder.add("itemGroup." + ChargedCharms.MODID, "Charged Charms");
        addCharmItem(builder, ChargedCharmsItems.regenerationCharmId, "Regeneration");
        addCharmItem(builder, ChargedCharmsItems.absorptionCharmId, "Absorption");
        addCharmItem(builder, ChargedCharmsItems.glowupCharmId, "Glow Up");
        addCharmItem(builder, ChargedCharmsItems.totemCharmId, "Totem");
        addCharmItem(builder, ChargedCharmsItems.enchantedTotemCharmId, "Enchanted Totem");
        addCharmItem(builder, ChargedCharmsItems.speedCharmId, "Speed");
        addCharmItem(builder, ChargedCharmsItems.waterBreathingCharmId, "Water Breathing");
        builder.add("accessories.slot.charged_charm", "Charged Charm");
        builder.add("tooltip.charged_charm.charges", "Charges");
        addTranslationTitle(builder, "Charged Charms");
        addTranslation(builder, "rendering");
        addTranslation(builder, "showcharms");
        addTranslation(builder, "charms");
        addTranslation(builder, "disableregencharm");
        addTranslation(builder, "disableabsorptioncharm");
        addTranslation(builder, "disableglowupcharm");
        addTranslation(builder, "disabletotemcharm");
        addTranslation(builder, "disableenchtotemcharm");
        addTranslation(builder, "disablespeedcharm");
        addTranslation(builder, "disablewaterbreathingcharm");
        addTranslation(builder, "tweaks");
        addTranslation(builder, "absorptioncooldown");
        addTranslation(builder, "absorptionduration");
        addTranslation(builder, "absorptionamplifier");
        addTranslation(builder, "absorptioncharges");
        addTranslation(builder, "regenpercentage");
        addTranslation(builder, "regenduration");
        addTranslation(builder, "regenamplifier");
        addTranslation(builder, "regencharges");
        addTranslation(builder, "glowupduration");
        addTranslation(builder, "glowupcharges");
        addTranslation(builder, "speedduration");
        addTranslation(builder, "speedcooldown");
        addTranslation(builder, "speedcharges");
        addTranslation(builder, "totemcharges");
        addTranslation(builder, "airremaining");
        addTranslation(builder, "waterbreathingcharges");
    }

    private void addCharmItem(TranslationBuilder builder, String id, String name) {
        builder.add("item." + ChargedCharms.MODID + "." + id, "Charged " + name + " Charm");
    }

    private void addTranslationTitle(TranslationBuilder builder, String title) {
        builder.add(ChargedCharms.MODID + ".configuration.title", title);
    }

    private void addTranslation(TranslationBuilder builder, String id) {
        addTranslationName(builder, id);
        addTranslationDescription(builder, id);
    }

    private void addTranslationName(TranslationBuilder builder, String id) {
        builder.add(ChargedCharms.MODID + ".configuration." + id + ".name", Translations.get(id + ".title"));
    }

    private void addTranslationDescription(TranslationBuilder builder, String id) {
        builder.add(ChargedCharms.MODID + ".configuration." + id + ".description", Translations.get(id));
    }

    private void addTranslationDescription(TranslationBuilder builder, String id, String key) {
        builder.add(ChargedCharms.MODID + ".configuration." + id + ".description", Translations.get(key));
    }

}
