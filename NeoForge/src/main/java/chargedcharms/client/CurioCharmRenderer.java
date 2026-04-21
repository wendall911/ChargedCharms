package chargedcharms.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import chargedcharms.client.model.ChargedCharmModel;

import static chargedcharms.util.ResourceLocationHelper.prefix;

public class CurioCharmRenderer implements ICurioRenderer.HumanoidRender {

    private final Identifier charmTexture;
    private final ChargedCharmModel<HumanoidRenderState> model;

    public CurioCharmRenderer(ModelLayerLocation location) {
        this.charmTexture = prefix("textures/entity/" + location.model().getPath() + ".png");
        this.model = new ChargedCharmModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(location));
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
