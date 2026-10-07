package chargedcharms.client.renderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import chargedcharms.client.CharmLayerDefinitions;
import chargedcharms.client.model.CharmModel;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class CharmRenderer implements ICurioRenderer.HumanoidRender {

    private final Identifier charmTexture;
    private final CharmModel<HumanoidRenderState> model;

    public CharmRenderer(Identifier id) {
        charmTexture = prefix("textures/item/" + id.getPath() + ".png");
        this.model = new CharmModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(CharmLayerDefinitions.CHARM_LAYER));
    }

    @Override
    public HumanoidModel<HumanoidRenderState> getModel(ItemStack stack, SlotContext slotContext) {
      return this.model;
    }

    @Override
    public Identifier getModelTexture(ItemStack stack, SlotContext slotContext) {
        return charmTexture;
    }

}
