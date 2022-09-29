package chargedcharms.common;

import javax.annotation.Nonnull;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import chargedcharms.ChargedCharms;
import chargedcharms.common.item.ChargedCharmsItems;

public class ForgeCreativeTab extends CreativeModeTab {

    public static final ForgeCreativeTab INSTANCE = new ForgeCreativeTab();

    private ForgeCreativeTab() {
        super(ChargedCharms.MODID);
    }

    @Override
    public @Nonnull ItemStack makeIcon() {
        return new ItemStack(ChargedCharmsItems.regenerationCharm);
    }

}
