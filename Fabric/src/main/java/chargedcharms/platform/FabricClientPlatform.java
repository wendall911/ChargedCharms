package chargedcharms.platform;

import com.mojang.blaze3d.vertex.PoseStack;

import dev.emi.trinkets.api.client.TrinketRenderer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;

import chargedcharms.platform.services.IClientPlatform;

public class FabricClientPlatform implements IClientPlatform {

    @SuppressWarnings("unchecked")
    @Override
    public void translateToPosition(LivingEntity livingEntity,
            EntityModel<? extends LivingEntity> model, PoseStack poseStack) {

        if (livingEntity instanceof AbstractClientPlayer player) {
            TrinketRenderer.translateToChest(poseStack, (PlayerModel<AbstractClientPlayer>) model,
                player);
        }
    }

}
