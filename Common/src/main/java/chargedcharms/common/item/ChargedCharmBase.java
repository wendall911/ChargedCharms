package chargedcharms.common.item;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class ChargedCharmBase extends Item {

    public ChargedCharmBase(Properties props) {
        super(props);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        Component textComponent = Component.translatable("tooltip.charged_charm.charges");
        int charges = stack.getMaxDamage() - stack.getDamageValue();
        String text = charges + "/" + stack.getMaxDamage() + " " + textComponent.getString();

        super.appendHoverText(stack, context, components, flag);

        components.add(Component.translatable(text).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
    }

}