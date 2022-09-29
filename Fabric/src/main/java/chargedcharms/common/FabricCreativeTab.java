package chargedcharms.common;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import chargedcharms.ChargedCharms;
import chargedcharms.common.item.ChargedCharmsItems;
import static chargedcharms.util.ResourceLocationHelper.prefix;

public class FabricCreativeTab extends CreativeModeTab {

    public static final FabricCreativeTab INSTANCE;

    static {
        CreativeModeTab sacrificial = FabricItemGroupBuilder.build(prefix("sacrificial_tab"), () -> new ItemStack(ChargedCharmsItems.regenerationCharm));

        INSTANCE = new FabricCreativeTab(sacrificial.getId(), ChargedCharms.MODID);
    }

    public FabricCreativeTab(int idx, String langId) {
        super(idx, langId);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(ChargedCharmsItems.regenerationCharm);
    }

}
