package chargedcharms;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import top.theillusivec4.curios.api.client.ICurioRenderer;

import chargedcharms.client.CharmLayerDefinitions;
import chargedcharms.client.renderer.CharmRenderer;
import chargedcharms.client.model.CharmModel;
import chargedcharms.common.item.ChargedCharmsItems;

public class ChargedCharmsClientNeoForge {

    public void init(IEventBus bus) {
        bus.addListener(this::clientSetup);
        bus.addListener(this::registerLayers);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        ChargedCharmsItems.getAll().forEach((id, item) -> {
            ICurioRenderer.register(item, () -> new CharmRenderer(id));
        });
    }

    private void registerLayers(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CharmLayerDefinitions.CHARM_LAYER, CharmModel::createLayer);
    }

}
