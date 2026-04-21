package chargedcharms.client.integration.dynamiclights;

import java.util.Set;

import org.jspecify.annotations.NonNull;
import org.jetbrains.annotations.Range;

import com.mojang.serialization.MapCodec;

import dev.lambdaurora.lambdynlights.api.entity.luminance.EntityLuminance;
import dev.lambdaurora.lambdynlights.api.item.ItemLightSourceManager;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import chargedcharms.common.item.ChargedCharmsItems;
import chargedcharms.config.ConfigHandler;
import chargedcharms.platform.Services;

public record GlowUpCharmEntity() implements EntityLuminance {

    public static final GlowUpCharmEntity INSTANCE = new GlowUpCharmEntity();
    public static final MapCodec<GlowUpCharmEntity> MAP_CODEC = MapCodec.unit(INSTANCE);

    @Override
    public @NonNull Type type() {
        return ChargedCharmsLighting.GlowUpCharmLuminance;
    }

    @Override
    public @Range(from = 0L, to = 15L) int getLuminance(@NonNull ItemLightSourceManager itemLightSourceManager, @NonNull Entity entity) {
        int luminance = 0;

        if (entity instanceof LivingEntity livingEntity) {
            Set<ItemStack> items = Services.PLATFORM.findCharms(livingEntity);
            ItemStack charmStack = items.stream().filter(stack -> !stack.isEmpty()
                && stack.is(ChargedCharmsItems.glowupCharm) && stack.getDamageValue() < ConfigHandler.Common.glowUpCharges()).findFirst().orElse(ItemStack.EMPTY);

            if (!charmStack.isEmpty()) {
                luminance = 15;
            }
        }

        return luminance;
    }

}
