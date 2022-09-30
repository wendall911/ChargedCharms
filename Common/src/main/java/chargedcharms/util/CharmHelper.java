package chargedcharms.util;

import java.util.Set;

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
        Set<ItemStack> stackSet = Services.PLATFORM.findCharms(livingEntity);
        ItemStack totem = stackSet.stream().filter(stack -> !stack.isEmpty() && CharmProviders.hasTotem(stack)).findFirst().orElse(ItemStack.EMPTY);

        if (!totem.isEmpty()) {
            ItemStack copy = totem.copy();
            totem.setDamageValue(totem.getDamageValue() + 1);

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
