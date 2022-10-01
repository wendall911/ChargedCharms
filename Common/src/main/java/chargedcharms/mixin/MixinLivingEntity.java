package chargedcharms.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import chargedcharms.util.CharmHelper;

@SuppressWarnings("unused")
@Mixin(LivingEntity.class)
public abstract class MixinLivingEntity {

    @SuppressWarnings("ConstantConditions")
    @Inject(at = @At(value = "INVOKE", target = "net/minecraft/world/InteractionHand.values()[Lnet/minecraft/world/InteractionHand;"), method = "checkTotemDeathProtection", cancellable = true)
    private void chargedcharms$checkTotemDeathProtection(DamageSource src,
            CallbackInfoReturnable<Boolean> cir) {
        if (CharmHelper.useTotem((LivingEntity) (Object) this)) {
            cir.setReturnValue(true);
        }
    }

}