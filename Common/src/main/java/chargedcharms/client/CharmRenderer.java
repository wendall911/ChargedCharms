package chargedcharms.client;

import com.mojang.blaze3d.vertex.PoseStack;

import io.wispforest.accessories.api.client.renderers.AccessoryRenderer;
import io.wispforest.accessories.api.client.renderers.SimpleAccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotPath;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import chargedcharms.config.ConfigHandler;

public class CharmRenderer implements SimpleAccessoryRenderer {

    @Override
    public <S extends LivingEntityRenderState> void render(ItemStack stack, SlotPath path, PoseStack poseStack,
               EntityModel<S> model, S renderState, MultiBufferSource buffer, int light, float partialTicks) {
        if (ConfigHandler.Client.showCharms()) {
            Minecraft mc = Minecraft.getInstance();

            align(stack, path, model, renderState, poseStack);
            poseStack.scale(0.45F, 0.45F, 0.45F);
            mc.getItemRenderer().renderStatic(stack, ItemDisplayContext.GUI, light, OverlayTexture.NO_OVERLAY,
                    poseStack, buffer, mc.level, 0);
        }
    }

    @Override
    public <S extends LivingEntityRenderState> void align(ItemStack stack, SlotPath path, EntityModel<S> model,
              S renderState, PoseStack poseStack) {
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