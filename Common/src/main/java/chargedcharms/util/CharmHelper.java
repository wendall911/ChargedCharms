package chargedcharms.util;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import chargedcharms.common.CharmProviders;
import chargedcharms.platform.Services;

public class CharmHelper {

    public static boolean useTotem(LivingEntity livingEntity) {
        ItemStack stack = Services.PLATFORM.findCharm(livingEntity);

        if (!stack.isEmpty() && CharmProviders.hasTotem(stack)) {
            ItemStack copy = stack.copy();
            stack.shrink(1);

            if (livingEntity instanceof ServerPlayer player) {
                player.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING), 1);
                CriteriaTriggers.USED_TOTEM.trigger(player, copy);
            }
            CharmProviders.getEffectProvider(copy.getItem())
                    .ifPresent(effectProvider -> effectProvider.applyEffects(livingEntity));
            livingEntity.level.broadcastEntityEvent(livingEntity, (byte) 35);

            return true;
        }

        return false;
    }

}
