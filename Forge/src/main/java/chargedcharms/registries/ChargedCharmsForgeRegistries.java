package chargedcharms.registries;

import com.mojang.serialization.MapCodec;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;

import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import chargedcharms.ChargedCharms;

public class ChargedCharmsForgeRegistries {

    public static final DeferredRegister<DataComponentType<?>> COMPONENT_TYPE_DEFERRED_REGISTER =
        DeferredRegister.create(BuiltInRegistries.DATA_COMPONENT_TYPE.key(), ChargedCharms.MODID);

    public static final DeferredRegister<MapCodec<? extends ICondition>> CONDITION_SERIALIZERS_DEFERRED_REGISTER =
        DeferredRegister.create(ForgeRegistries.Keys.CONDITION_SERIALIZERS, ChargedCharms.MODID);

}
