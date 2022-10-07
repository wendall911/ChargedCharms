package chargedcharms.util;

import java.util.Set;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LightLayer;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.platform.Services;

public class CharmHelper {

    public static boolean useTotem(LivingEntity livingEntity) {
        Set<ItemStack> stackSet = Services.PLATFORM.findCharms(livingEntity);
        ItemStack totem = stackSet.stream().filter(stack -> !stack.isEmpty() && CharmEffectProviders.hasTotem(stack)).findFirst().orElse(ItemStack.EMPTY);

        if (!totem.isEmpty()) {
            ItemStack copy = totem.copy();
            totem.setDamageValue(totem.getDamageValue() + 1);

            if (livingEntity instanceof ServerPlayer player) {
                player.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING), 1);
                CriteriaTriggers.USED_TOTEM.trigger(player, copy);
            }
            CharmEffectProviders.getEffectProvider(copy.getItem())
                    .ifPresent(effectProvider -> effectProvider.applyEffects(livingEntity));
            livingEntity.level.broadcastEntityEvent(livingEntity, (byte) 35);

            return true;
        }

        return false;
    }

    public static void triggerCharm(LivingEntity sourceEntity, LivingEntity targetEntity, Item charm) {
        ItemStack charmStack = getCharm(sourceEntity, charm);

        triggerCharm(targetEntity, charmStack);
    }

    public static void triggerCharm(LivingEntity targetEntity, ItemStack charmStack) {
        if (!charmStack.isEmpty()) {
            charmStack.setDamageValue(charmStack.getDamageValue() + 1);
            CharmEffectProviders.getEffectProvider(charmStack.getItem())
                    .ifPresent(effectProvider -> effectProvider.applyEffects(targetEntity));
        }
    }

    public static ItemStack getCharm(LivingEntity sourceEntity, Item charm) {
        Set<ItemStack> stackSet = Services.PLATFORM.findCharms(sourceEntity);

        return stackSet.stream().filter(stack -> !stack.isEmpty()
                && CharmEffectProviders.hasChargedCharm(stack, charm)).findFirst().orElse(ItemStack.EMPTY);
    }

    public static void chargeSolarCharm(ServerPlayer sp, Item charm) {
        Set<ItemStack> stackSet = Services.PLATFORM.findCharms(sp);
        ItemStack charmStack = stackSet.stream().filter(stack -> !stack.isEmpty()
                && stack.is(charm) && stack.getDamageValue() > 0).findFirst().orElse(ItemStack.EMPTY);

        if (!charmStack.isEmpty()) {
            CompoundTag tags = charmStack.getOrCreateTag();
            double radiation = getSunRadiation(sp.getLevel(), sp.getOnPos());
            String key = "solar_radiation";

            if (tags.contains(key)) {
                radiation += tags.getDouble(key);
            }

            if (radiation > 10000) {
                charmStack.setDamageValue(charmStack.getDamageValue() - 1);
                tags.putDouble(key, 0);
            }
            else {
                tags.putDouble(key, radiation);
            }
        }
    }

    /*
     * Based on sun angle ... do mathy things to get radiation
     */
    private static double getSunRadiation(ServerLevel world, BlockPos pos) {
        double radiation = 0.0;
        double sunlight = world.getBrightness(LightLayer.SKY, pos.above()) - world.getSkyDarken();
        float f = world.getSunAngle(1.0F);

        if (sunlight > 0) {
            float f1 = f < (float)Math.PI ? 0.0F : ((float)Math.PI * 2F);
            f += (f1 - f) * 0.2F;
            sunlight = sunlight * Mth.cos(f);
        }

        radiation += sunlight * 100;

        return Math.max(radiation, 0);
    }

}