package chargedcharms;

import java.util.Map;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import chargedcharms.client.CharmRenderer;
import chargedcharms.common.item.ChargedCharmsItems;

public class ChargedCharmsClient {

    public static void init() {
        for (Map.Entry<ResourceLocation, Item> entry : ChargedCharmsItems.getAll().entrySet()) {
            AccessoriesRendererRegistry.bindItemToRenderer(entry.getValue(), entry.getKey(), CharmRenderer::new);
        }
    }

}
