package chargedcharms.data.integration;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import top.theillusivec4.curios.api.CuriosDataProvider;

import chargedcharms.ChargedCharms;

public class ChargedCharmsCuriosProvider extends CuriosDataProvider {

    public static final Identifier CHARM_VALIDATOR = Identifier.fromNamespaceAndPath("curios", "tag");
    public static final Identifier CHARGED_CHARM_SLOT_ICON = Identifier.fromNamespaceAndPath(ChargedCharms.MODID, "slot/empty_charged_charm_slot");

    public ChargedCharmsCuriosProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(ChargedCharms.MODID, packOutput, lookupProvider);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        createSlot("charged_charm")
            .size(2)
            .order(200)
            .addValidator(CHARM_VALIDATOR)
            .icon(CHARGED_CHARM_SLOT_ICON);
        createEntities("charged_charm")
            .addPlayer()
            .addSlots("charged_charm");
    }

}
