package chargedcharms.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.util.CharmHelper;

@Mixin(Player.class)
public abstract class MixinPlayer {

    @Inject(at = @At(value = "HEAD"), method = "attack")
    private void onPlayerAttack(Entity target, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (player instanceof ServerPlayer sp) {
            if (target instanceof LivingEntity livingEntity && !livingEntity.hasEffect(MobEffects.GLOWING)) {
                CharmHelper.triggerCharm(sp, livingEntity, ChargedCharmsItems.glowupCharm);
            }
        }
    }

}
