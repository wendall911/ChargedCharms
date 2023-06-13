package chargedcharms.client;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import chargedcharms.config.ConfigHandler;
import chargedcharms.platform.Services;

public class CharmRenderer {

    public static void render(LivingEntity livingEntity, EntityModel<? extends LivingEntity> model,
            PoseStack poseStack, ItemStack stack, MultiBufferSource buffer,
            int light) {
        if (ConfigHandler.Client.showCharms()) {
            Services.CLIENT_PLATFORM.translateToPosition(livingEntity, model, poseStack);
            poseStack.scale(0.25F, 0.25F, 0.25F);
            poseStack.mulPose(Direction.DOWN.getRotation());
            Minecraft.getInstance().getItemRenderer()
                    .renderStatic(stack, ItemDisplayContext.NONE, light, OverlayTexture.NO_OVERLAY,
                            poseStack, buffer, livingEntity.getLevel(), 0);
        }
    }

}