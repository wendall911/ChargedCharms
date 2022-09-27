package chargedcharms.platform.services;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.LivingEntity;

public interface IClientPlatform {

    void translateToPosition(LivingEntity livingEntity, EntityModel<? extends LivingEntity> model,
            PoseStack poseStack);

}
