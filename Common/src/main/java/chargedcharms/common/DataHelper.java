package chargedcharms.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagBuilder;

import chargedcharms.ChargedCharms;

public class DataHelper {

    public static void addElement(TagBuilder builder, ResourceLocation loc) {
        String namespace = loc.getNamespace();

        if (namespace.contains("minecraft") || namespace.contains(ChargedCharms.MODID)) {
            builder.addElement(loc);
        }
        else {
            builder.addOptionalElement(loc);
        }
    }

}
