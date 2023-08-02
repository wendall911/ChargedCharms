package chargedcharms.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;

import chargedcharms.ChargedCharms;

public class DataHelper {

    public static void addElement(Tag.Builder builder, ResourceLocation loc) {
        String namespace = loc.getNamespace();

        if (namespace.contains("minecraft") || namespace.contains(ChargedCharms.MODID)) {
            builder.addElement(loc, ChargedCharms.MODID);
        }
        else {
            builder.addOptionalElement(loc, ChargedCharms.MODID);
        }
    }

}
