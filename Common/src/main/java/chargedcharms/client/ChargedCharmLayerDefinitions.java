package chargedcharms.client;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.world.item.Item;

import chargedcharms.common.item.ChargedCharmsItems;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class ChargedCharmLayerDefinitions {

    public static final Map<Item, ModelLayerLocation> ALL = new HashMap<>();

    public static final ModelLayerLocation CHARGED_ABSORPTION_CHARM = createModelLayerLocation(ChargedCharmsItems.absorptionCharm, ChargedCharmsItems.absorptionCharmId);
    public static final ModelLayerLocation CHARGED_REGENERATION_CHARM = createModelLayerLocation(ChargedCharmsItems.regenerationCharm, ChargedCharmsItems.regenerationCharmId);
    public static final ModelLayerLocation CHARGED_GLOWUP_CHARM = createModelLayerLocation(ChargedCharmsItems.glowupCharm, ChargedCharmsItems.glowupCharmId);
    public static final ModelLayerLocation CHARGED_SPEED_CHARM = createModelLayerLocation(ChargedCharmsItems.speedCharm, ChargedCharmsItems.speedCharmId);
    public static final ModelLayerLocation CHARGED_TOTEM_CHARM = createModelLayerLocation(ChargedCharmsItems.totemCharm, ChargedCharmsItems.totemCharmId);
    public static final ModelLayerLocation CHARGED_ENHANCED_TOTEM_CHARM = createModelLayerLocation(ChargedCharmsItems.enchantedTotemCharm, ChargedCharmsItems.enchantedTotemCharmId);
    public static final ModelLayerLocation CHARGED_WATERBREATHING_CHARM = createModelLayerLocation(ChargedCharmsItems.waterBreathingCharm, ChargedCharmsItems.waterBreathingCharmId);

    private static ModelLayerLocation createModelLayerLocation(Item item, String id) {
        ModelLayerLocation location = new ModelLayerLocation(prefix(id), "charged_charm");
        ALL.put(item, location);

        return location;
    }

}
