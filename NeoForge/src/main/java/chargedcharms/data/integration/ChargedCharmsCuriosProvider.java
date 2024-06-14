package chargedcharms.data.integration;

import chargedcharms.ChargedCharms;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class ChargedCharmsCuriosProvider extends CuriosDataProvider {

    public static final ResourceLocation CHARM_VALIDATOR = new ResourceLocation("curios", "tag");
    public static final ResourceLocation CHARGED_CHARM_SLOT_ICON = new ResourceLocation(ChargedCharms.MODID, "slot/empty_charged_charm_slot");

    public ChargedCharmsCuriosProvider(PackOutput packOutput, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(ChargedCharms.MODID, packOutput, fileHelper, lookupProvider);
    }

    @Override
    public void generate(HolderLookup.Provider provider, ExistingFileHelper existingFileHelper) {
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
