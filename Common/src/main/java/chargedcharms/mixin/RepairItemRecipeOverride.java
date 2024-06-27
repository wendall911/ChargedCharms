package chargedcharms.mixin;

import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.world.level.Level;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import chargedcharms.common.item.ChargedCharmBase;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeOverride {

    @Inject(at = @At(value = "HEAD"), method = "matches(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/world/level/Level;)Z", cancellable = true)
    private void isChargedCharm(CraftingInput craftingInput, Level level, CallbackInfoReturnable<Boolean> cir) {
        boolean hasChargedCharm = false;

        for (int i = 0; i < craftingInput.size(); i++) {
            if (craftingInput.getItem(i).getItem() instanceof ChargedCharmBase) {
                hasChargedCharm = true;
            }
        }

        if (hasChargedCharm) {
            cir.setReturnValue(false);
        }
    }

    @Inject(at = @At(value = "HEAD"), method = "matches(Lnet/minecraft/world/item/crafting/RecipeInput;Lnet/minecraft/world/level/Level;)Z", cancellable = true)
    private void isChargedCharmRecipeInput(RecipeInput recipeInput, Level level, CallbackInfoReturnable<Boolean> cir) {
        boolean hasChargedCharm = false;

        for (int i = 0; i < recipeInput.size(); i++) {
            if (recipeInput.getItem(i).getItem() instanceof ChargedCharmBase) {
                hasChargedCharm = true;
            }
        }

        if (hasChargedCharm) {
            cir.setReturnValue(false);
        }
    }

}
