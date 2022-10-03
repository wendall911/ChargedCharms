package chargedcharms.platform;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Vector3f;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import chargedcharms.platform.services.IClientPlatform;

public class FabricClientPlatform implements IClientPlatform {

    @SuppressWarnings("unchecked")
    @Override
    public void translateToPosition(LivingEntity livingEntity,
            EntityModel<? extends LivingEntity> model, PoseStack poseStack) {

        if (livingEntity instanceof AbstractClientPlayer player) {
            PlayerModel<AbstractClientPlayer> playerModel = (PlayerModel<AbstractClientPlayer>) model;

            if (player.isCrouching() && !model.riding && !player.isSwimming()) {
                poseStack.translate(0.0F, 0.2F, 0.0F);
                poseStack.mulPose(Vector3f.XP.rotationDegrees(playerModel.body.xRot));
            }
            poseStack.mulPose(Vector3f.YP.rotationDegrees(playerModel.body.yRot));

            float zPos = -0.15F;

            if (!livingEntity.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
                zPos = -0.20F;
            }

            poseStack.translate(0.1F, 0.2F, zPos);
        }
    }

}
