package chargedcharms.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import chargedcharms.client.model.ChargedCharmModel;
import chargedcharms.data.integration.ModIntegration;

import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class CurioCharmRenderer implements ICurioRenderer.HumanoidRender {

    private static final Identifier CHARM_TEXTURE = loc(ModIntegration.CURIOS_MODID, "textures/slot/empty_charm_slot.png");
    private final ChargedCharmModel<HumanoidRenderState> model;

    public CurioCharmRenderer() {
        this.model = new ChargedCharmModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ChargedCharmLayerDefinitions.CHARGED_CHARM));
    }

    @Override
    public HumanoidModel<HumanoidRenderState> getModel(ItemStack stack, SlotContext slotContext) {
        return this.model;
    }

    @Override
    public Identifier getModelTexture(ItemStack stack, SlotContext slotContext) {
        return CHARM_TEXTURE;
    }

}
