package chargedcharms;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;

import net.minecraft.world.item.Item;

import chargedcharms.client.CharmRenderer;

public class FabricClientHooks {

    public static void registerTrinketRenderer(Item item) {
        TrinketRendererRegistry.registerRenderer(item,
            (stack, slotReference, contextModel, matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch) -> {
                if (slotReference.inventory().getSlotType().getName().contentEquals("alt_charm")) {
                    CharmRenderer.render(entity, contextModel, matrices, stack, vertexConsumers, light);
                }
            });
    }

}