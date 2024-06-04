package chargedcharms.registries;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import chargedcharms.ChargedCharms;

public class ChargedCharmsNeoForgeRegistries {

    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPE_DEFERRED_REGISTER =
        DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE, ChargedCharms.MODID);
    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_SERIALIZERS_DEFERRED_REGISTER =
        DeferredRegister.create(NeoForgeRegistries.CONDITION_SERIALIZERS, ChargedCharms.MODID);

}
