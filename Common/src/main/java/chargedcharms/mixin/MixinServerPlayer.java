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
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import chargedcharms.common.effect.AbsorptionEffectProvider;
import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.config.ConfigHandler;
import chargedcharms.util.CharmHelper;

@Mixin(ServerPlayer.class)
public class MixinServerPlayer {

    @Unique
    private int chargedCharms$counter = 0;
    @Unique
    private final Map<UUID, Long> chargedCharms$absorptionCoolDownTracker = Maps.newHashMap();
    @Unique
    private final Map<UUID, Long> chargedCharms$speedCoolDownTracker = Maps.newHashMap();

    @Inject(at = @At(value = "TAIL"), method = "doTick")
    private void monitorDoTick(CallbackInfo ci) {
        ServerPlayer sp = (ServerPlayer) (Object) this;

        // Check every 20 ticks
        if (chargedCharms$counter % 20 == 0) {
            if (chargedCharms$needsHealing(sp) && !sp.hasEffect(MobEffects.REGENERATION)) {
                CharmHelper.triggerCharm(sp, sp, ChargedCharmsItems.regenerationCharm);
            }
            if (chargedCharms$isSprintJumping(sp) && !sp.hasEffect(MobEffects.MOVEMENT_SPEED)) {
                ItemStack charmStack = CharmHelper.getCharm(sp, ChargedCharmsItems.speedCharm);

                if (chargedCharms$hasCharge(charmStack) && chargedCharms$canTriggerSpeedCharm(sp)) {
                    CharmHelper.triggerCharm(sp, charmStack);
                }
            }
            if (chargedCharms$needsAir(sp) && !sp.hasEffect(MobEffects.WATER_BREATHING)) {
                ItemStack charmStack = CharmHelper.getCharm(sp, ChargedCharmsItems.waterBreathingCharm);

                if (chargedCharms$hasCharge(charmStack)) {
                    CharmHelper.triggerCharm(sp, charmStack);
                }
            }
        }

        if (chargedCharms$counter % 100 == 0) {
            CharmHelper.chargeSolarCharm(sp, ChargedCharmsItems.glowupCharm);
        }

        chargedCharms$counter++;
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

        if (!sp.isInvulnerableTo(damageSource) && chargedCharms$isValidDamageSource(damageSource)) {
            if (!sp.hasEffect(MobEffects.ABSORPTION)) {
                ItemStack charmStack = CharmHelper.getCharm(sp, ChargedCharmsItems.absorptionCharm);

                if (chargedCharms$hasCharge(charmStack) && chargedCharms$canTriggerAbsorptionCharm(sp)) {
                    CharmHelper.triggerCharm(sp, charmStack);
                }
            }
        }
    }

    @Unique
    private boolean chargedCharms$isValidDamageSource(DamageSource damageSource) {
        return AbsorptionEffectProvider.invalidDamageSources.stream().noneMatch(damageSource::is);
    }

    @Unique
    private boolean chargedCharms$canTriggerAbsorptionCharm(LivingEntity livingEntity) {
        long now = System.currentTimeMillis();
        UUID uuid = livingEntity.getUUID();
        long lastTime = chargedCharms$absorptionCoolDownTracker.getOrDefault(uuid, now);
        long cooldown = ConfigHandler.Common.absorptionCooldown();
        long elapsed = now - lastTime;

        if (elapsed == 0 || elapsed > cooldown) {
            chargedCharms$absorptionCoolDownTracker.put(uuid, now);

            return true;
        }

        return false;
    }

    @Unique
    private boolean chargedCharms$canTriggerSpeedCharm(LivingEntity livingEntity) {
        long now = System.currentTimeMillis();
        UUID uuid = livingEntity.getUUID();
        long lastTime = chargedCharms$speedCoolDownTracker.getOrDefault(uuid, now);
        long cooldown = ConfigHandler.Common.speedCooldown();
        long elapsed = now - lastTime;

        if (elapsed == 0 || elapsed > cooldown) {
            chargedCharms$speedCoolDownTracker.put(uuid, now);

            return true;
        }

        return false;
    }

    @Unique
    private boolean chargedCharms$needsHealing(ServerPlayer sp) {
        return (sp.getHealth() / sp.getMaxHealth()) < ConfigHandler.Common.regenPercentage();
    }

    @Unique
    private boolean chargedCharms$isSprintJumping(ServerPlayer sp) {
        return !sp.onGround() && sp.isSprinting() && !sp.isSwimming();
    }

    @Unique
    private boolean chargedCharms$hasCharge(ItemStack charmStack) {
        return !charmStack.isEmpty() && charmStack.getDamageValue() < charmStack.getMaxDamage();
    }

    @Unique
    private boolean chargedCharms$needsAir(ServerPlayer sp) {
        return (sp.getAirSupply() / (float) sp.getMaxAirSupply()) <= ConfigHandler.Common.airRemaining();
    }

}