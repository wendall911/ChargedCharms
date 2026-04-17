package chargedcharms.client;

import com.mojang.blaze3d.vertex.PoseStack;

import io.wispforest.accessories.api.client.AccessoryRenderState;
import io.wispforest.accessories.api.client.renderers.AccessoryRenderer;
import io.wispforest.accessories.api.client.renderers.SimpleAccessoryRenderer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import chargedcharms.config.ConfigHandler;

public class CharmRenderer implements SimpleAccessoryRenderer {

    @Override
    public <S extends LivingEntityRenderState> void renderStack(AccessoryRenderState accessoryState,
            S entityState, EntityModel<S> model, PoseStack poseStack, SubmitNodeCollector collector,
            ItemStack stack, ItemStackRenderState stackRenderState, int light) {
        if (ConfigHandler.Client.showCharms()) {
            poseStack.scale(0.45F, 0.45F, 0.45F);
            stackRenderState.submit(poseStack, collector, light, OverlayTexture.NO_OVERLAY, 0);
        }
    }

    @Override
    public <S extends LivingEntityRenderState> void align(AccessoryRenderState accessoryRenderState, S entityState,
            EntityModel<S> model, PoseStack poseStack) {
        if ((model instanceof HumanoidModel<? extends HumanoidRenderState> humanoidModel)) {
            Minecraft mc = Minecraft.getInstance();
            float zPos = 1.1F;

            if (mc.player != null && !mc.player.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
                zPos = 1.6F;
            }

            AccessoryRenderer.transformToModelPart(poseStack, humanoidModel.body, -0.50, 0.40, zPos);
        }
    }

}
