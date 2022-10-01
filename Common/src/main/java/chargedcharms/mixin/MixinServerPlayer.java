package chargedcharms.mixin;

import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import chargedcharms.common.effect.AbsorptionEffectProvider;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.util.CharmHelper;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer {

    private int counter = 0;
    private final Map<UUID, Long> coolDownTracker = Maps.newHashMap();

    @Inject(at = @At(value = "TAIL"), method = "doTick")
    private void monitorDoTick(CallbackInfo ci) {
        ServerPlayer sp = (ServerPlayer) (Object) this;

        // Check every 20 ticks
        if (counter % 20 == 0 && needsHealing(sp) && !sp.hasEffect(MobEffects.REGENERATION)) {
            CharmHelper.triggerCharm(sp, sp, ChargedCharmsItems.regenerationCharm);
        }

        if (counter % 100 == 0) {
            CharmHelper.chargeSolarCharm(sp, ChargedCharmsItems.glowupCharm);
        }

        counter++;
    }

    @Inject(at = @At(value = "HEAD"), method = "attack")
    private void onPlayerAttack(Entity target, CallbackInfo ci) {
        if (target instanceof LivingEntity livingEntity && !livingEntity.hasEffect(MobEffects.GLOWING)) {
            ServerPlayer sp = (ServerPlayer) (Object) this;

            CharmHelper.triggerCharm(sp, livingEntity, ChargedCharmsItems.glowupCharm);
        }
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;isInvulnerableTo(Lnet/minecraft/world/damagesource/DamageSource;)Z"), method = "hurt")
    private void onPlayerHurt(DamageSource damageSource, float amount, CallbackInfoReturnable<Boolean> cir) {
        ServerPlayer sp = (ServerPlayer) (Object) this;

        if (!sp.isInvulnerableTo(damageSource) && isValidDamageSource(damageSource)) {
            if (!sp.hasEffect(MobEffects.ABSORPTION)) {
                ItemStack charmStack = CharmHelper.getCharm(sp, ChargedCharmsItems.absorptionCharm);

                if (!charmStack.isEmpty() && canTrigger(sp)) {
                    CharmHelper.triggerCharm(sp, charmStack);
                }
            }
        }
    }

    private boolean isValidDamageSource(DamageSource damageSource) {
        return !AbsorptionEffectProvider.invalidDamageSources.contains(damageSource);
    }

    private boolean canTrigger(LivingEntity livingEntity) {
        long now = System.currentTimeMillis();
        UUID uuid = livingEntity.getUUID();
        long lastTime = coolDownTracker.getOrDefault(uuid, now);
        long cooldown = 20000;
        long elapsed = now - lastTime;

        if (elapsed == 0 || elapsed > cooldown) {
            coolDownTracker.put(uuid, now);

            return true;
        }

        return false;
    }

    private boolean needsHealing(ServerPlayer sp) {
        return (sp.getHealth() / sp.getMaxHealth()) < 0.35F;
    }

}