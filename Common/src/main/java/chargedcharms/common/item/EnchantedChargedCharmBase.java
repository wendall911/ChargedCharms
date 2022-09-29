package chargedcharms.common.item;

import net.minecraft.world.item.ItemStack;

public class EnchantedChargedCharmBase extends ChargedCharmBase {

    public EnchantedChargedCharmBase(Properties props) {
        super(props);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return true;
    }

}
