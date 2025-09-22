package chargedcharms.util;

import java.util.List;

import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.slot.SlotEntryReference;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.LightLayer;

import chargedcharms.common.CharmEffectProviders;
import chargedcharms.common.component.ChargedCharmsComponents;

public class CharmHelper {

    public static boolean useTotem(LivingEntity livingEntity) {
        List<SlotEntryReference> stackSet = findCharms(livingEntity);

        SlotEntryReference slotEntryReference = stackSet.stream().filter(reference -> !reference.stack().isEmpty()
            && CharmEffectProviders.hasTotem(reference.stack())).findFirst().orElse(null);

        if (slotEntryReference != null) {
            ItemStack totem = slotEntryReference.stack();
            ItemStack copy = totem.copy();
            totem.setDamageValue(totem.getDamageValue() + 1);

            if (livingEntity instanceof ServerPlayer player) {
                player.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING), 1);
                CriteriaTriggers.USED_TOTEM.trigger(player, copy);
            }
            CharmEffectProviders.getEffectProvider(copy.getItem())
                    .ifPresent(effectProvider -> effectProvider.applyEffects(livingEntity));
            livingEntity.level().broadcastEntityEvent(livingEntity, (byte) 35);

            slotEntryReference.reference().setStack(totem);

            return true;
        }

        return false;
    }

    public static void triggerCharm(LivingEntity sourceEntity, LivingEntity targetEntity, Item charm) {
        triggerCharm(targetEntity, getCharm(sourceEntity, charm));
    }

    public static void triggerCharm(LivingEntity targetEntity, SlotEntryReference slotEntryReference) {
        if (slotEntryReference != null) {
            ItemStack charmStack = slotEntryReference.stack();

            charmStack.setDamageValue(charmStack.getDamageValue() + 1);
            CharmEffectProviders.getEffectProvider(charmStack.getItem())
                .ifPresent(effectProvider -> effectProvider.applyEffects(targetEntity));
            slotEntryReference.reference().setStack(charmStack);
        }
    }

    public static void triggerCharm(LivingEntity targetEntity, ItemStack charm) {
        triggerCharm(targetEntity, getCharm(targetEntity, charm.getItem()));
    }

    public static SlotEntryReference getCharm(LivingEntity sourceEntity, Item charm) {
        List<SlotEntryReference> stackSet = findCharms(sourceEntity);

        return stackSet.stream().filter(reference -> !reference.stack().isEmpty()
                && CharmEffectProviders.hasChargedCharm(reference.stack(), charm)).findFirst().orElse(null);
    }

    public static void chargeSolarCharm(ServerPlayer sp, Item charm) {
        List<SlotEntryReference> stackSet = findCharms(sp);

        SlotEntryReference slotEntryReference = stackSet.stream().filter(reference -> !reference.stack().isEmpty()
                && reference.stack().is(charm) && reference.stack().getDamageValue() > 0).findFirst().orElse(null);

        if (slotEntryReference != null) {
            ItemStack charmStack = slotEntryReference.stack();
            int charmRadiation = charmStack.getOrDefault(ChargedCharmsComponents.SOLAR_RADIATION, 0);
            int radiation = (int)getSunRadiation(sp.level(), sp.getOnPos()) + charmRadiation;

            if (radiation > 10000) {
                charmStack.setDamageValue(charmStack.getDamageValue() - 1);
                charmStack.set(ChargedCharmsComponents.SOLAR_RADIATION, 0);
                slotEntryReference.reference().setStack(charmStack);
            }
            else {
                charmStack.set(ChargedCharmsComponents.SOLAR_RADIATION, radiation);
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

    private static List<SlotEntryReference> findCharms(LivingEntity livingEntity) {
        AccessoriesCapability capability = AccessoriesCapability.get(livingEntity);

        if (capability != null) {
            return capability.getEquipped(stack -> CharmEffectProviders.IS_CHARM.test(stack.getItem()));
        }
        
        return List.of();
    }

}
