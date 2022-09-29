package chargedcharms.common.item;

import javax.annotation.Nullable;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

public class ChargedCharmBase extends Item {

    public ChargedCharmBase(Properties props) {
        super(props);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        Component textComponent = Component.translatable("tooltip.charged_charm.charges");
        int charges = stack.getMaxDamage() - stack.getDamageValue();

        super.appendHoverText(stack, level, components, flag);

        components.add(Component.translatable(
                charges + " " + textComponent.getString()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
    }

    @Override
    public boolean isEdible() {
        return false;
    }

}