package chargedcharms;

import eu.pb4.trinkets.api.client.TrinketRendererRegistry;

import net.minecraft.world.item.Item;

import chargedcharms.client.CharmRenderer;

public class FabricClientHooks {

    public static void registerTrinketRenderer(Item item) {
        TrinketRendererRegistry.registerRenderer(item,
            (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance) -> {
                if (slotReference.inventory().slotType().name().contentEquals("alt_charm")) {
                    //CharmRenderer.render(entity, contextModel, matrices, stack, vertexConsumers, light);
                }
            });
    }

}