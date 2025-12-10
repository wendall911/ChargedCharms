package chargedcharms.common;

import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagBuilder;

import chargedcharms.ChargedCharms;

public class DataHelper {

    public static void addElement(TagBuilder builder, Identifier loc) {
        String namespace = loc.getNamespace();

        if (namespace.contains("minecraft") || namespace.contains(ChargedCharms.MODID)) {
            builder.addElement(loc);
        }
        else {
            builder.addOptionalElement(loc);
        }
    }

}
