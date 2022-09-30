package chargedcharms.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.util.CharmHelper;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer {

    private int counter = 0;

    @Inject(at = @At(value = "TAIL"), method = "doTick")
    private void monitorDoTick(CallbackInfo ci) {
        ServerPlayer sp = (ServerPlayer) (Object) this;

        // Check every 20 ticks
        if (counter % 20 == 0 && sp.getHealth() <= 6.0 && !sp.hasEffect(MobEffects.REGENERATION)) {
            CharmHelper.triggerCharm(sp, sp, ChargedCharmsItems.regenerationCharm);
        }

        if (counter % 100 == 0) {
            CharmHelper.chargeSolarCharm(sp, ChargedCharmsItems.glowupCharm);
        }

        counter++;
    }

    @Inject(at = @At(value = "HEAD"), method = "onEnterCombat")
    private void onPlayerEnterCombat(CallbackInfo ci) {
        ServerPlayer sp = (ServerPlayer) (Object) this;

        if (!sp.hasEffect(MobEffects.ABSORPTION)) {
            CharmHelper.triggerCharm(sp, sp, ChargedCharmsItems.absorptionCharm);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "attack")
    private void onPlayerAttack(Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity livingEntity && !livingEntity.hasEffect(MobEffects.GLOWING)) {
            ServerPlayer sp = (ServerPlayer) (Object) this;

            CharmHelper.triggerCharm(sp, livingEntity, ChargedCharmsItems.glowupCharm);
        }
    }

}