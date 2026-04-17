package chargedcharms.client.integration.ryoamiclights;

import java.util.function.Function;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

/*
import org.thinkingstudio.ryoamiclights.RyoamicLights;
import org.thinkingstudio.ryoamiclights.api.DynamicLightHandlers;

import chargedcharms.client.integration.DynamicLightsModule;

public class RyoamicDynamicLightsModule extends DynamicLightsModule {

    public static void setup() {
        RyoamicDynamicLightsModule module = new RyoamicDynamicLightsModule();

        NeoForge.EVENT_BUS.addListener(module::entityJoinLevel);
    }

    private void entityJoinLevel(final EntityJoinLevelEvent event) {
        registerEntity(event.getEntity(), event.getLevel());
    }

    @Override
    protected int getLuminance(ItemStack stack, boolean inWater) {
        return RyoamicLights.getLuminanceFromItemStack(stack, inWater);
    }

    @Override
    protected void registerDynamicLight(EntityType<?> type, Function<Entity, Integer> lightFunction) {
        DynamicLightHandlers.registerDynamicLightHandler(type, lightFunction::apply);
    }

}
*/
