package chargedcharms.client.integration;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

//import net.neoforged.neoforge.items.IItemHandler;

//import top.theillusivec4.curios.api.CuriosApi;

public abstract class DynamicLightsModule {

    private final Set<EntityType<?>> processed = new HashSet<>();

    protected abstract int getLuminance(ItemStack stack, boolean inWater);

    protected abstract void registerDynamicLight(EntityType<?> type, Function<Entity, Integer> lightFunction);

    public void registerEntity(Entity entity, Level level) {
        if (level.isClientSide() && entity instanceof LivingEntity livingEntity) {
            EntityType<?> type = livingEntity.getType();

            if (!this.processed.contains(type)) {
                this.processed.add(type);
                registerDynamicLight(
                    type,
                    lEntity -> getLuminance(lEntity, (stack) -> getLuminance(stack, lEntity.isInWater()))
                );
            }
        }
    }

    public int getLuminance(Entity entity, Function<ItemStack, Integer> lightFunction) {
        AtomicInteger luminance = new AtomicInteger();

        luminance.set(0);

        /*
        if (entity instanceof LivingEntity livingEntity) {
            return CuriosApi.getCuriosInventory(livingEntity).map(inventory -> {
                IItemHandler itemHandler = inventory.getEquippedCurios();

                for (int i = 0; i < itemHandler.getSlots(); i++) {
                    luminance.set(Math.max(luminance.get(), lightFunction.apply(itemHandler.getStackInSlot(i))));
                }

                return luminance.get();
            }).orElse(luminance.get());
        }
         */

        return luminance.get();
    }

}
