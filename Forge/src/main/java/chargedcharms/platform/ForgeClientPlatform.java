package chargedcharms.platform;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import top.theillusivec4.curios.api.client.ICurioRenderer;

import chargedcharms.platform.services.IClientPlatform;

public class ForgeClientPlatform implements IClientPlatform {

    @Override
    public void translateToPosition(LivingEntity livingEntity,
            EntityModel<? extends LivingEntity> model, PoseStack poseStack) {
        ICurioRenderer.translateIfSneaking(poseStack, livingEntity);
        ICurioRenderer.rotateIfSneaking(poseStack, livingEntity);
        float zPos = -0.15F;

        if (!livingEntity.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
            zPos = -0.20F;
        }

        poseStack.translate(0.1F, 0.2F, zPos);
    }

}