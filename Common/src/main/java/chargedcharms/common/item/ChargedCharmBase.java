package chargedcharms.common.item;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class ChargedCharmBase extends Item {

    public ChargedCharmBase(Properties props) {
        super(props);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull TooltipDisplay tooltipDisplay, @NotNull Consumer<Component> components, @NotNull TooltipFlag flag) {
        Component textComponent = Component.translatable("tooltip.charged_charm.charges");
        int charges = stack.getMaxDamage() - stack.getDamageValue();
        String text = charges + "/" + stack.getMaxDamage() + " " + textComponent.getString();

        components.accept(Component.literal(text).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
    }

}
