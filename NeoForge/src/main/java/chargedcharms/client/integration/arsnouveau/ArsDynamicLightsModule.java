package chargedcharms.client.integration.arsnouveau;

import java.util.function.Function;

import com.hollingsworth.arsnouveau.common.light.DynamLightUtil;
import com.hollingsworth.arsnouveau.common.light.LightManager;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;

import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

import chargedcharms.client.integration.DynamicLightsModule;

public class ArsDynamicLightsModule extends DynamicLightsModule {

    public static void setup() {
        ArsDynamicLightsModule module = new ArsDynamicLightsModule();

        NeoForge.EVENT_BUS.addListener(module::entityJoinLevel);
    }

    private void entityJoinLevel(final EntityJoinLevelEvent event) {
        registerEntity(event.getEntity(), event.getLevel());
    }

    @Override
    protected void registerDynamicLight(EntityType<?> type, Function<Entity, Integer> lightFunction) {
        LightManager.register(type, lightFunction::apply);
    }

    @Override
    protected int getLuminance(ItemStack stack, boolean inWater) {
        return DynamLightUtil.fromItemLike(stack.getItem());
    }

}
