package chargedcharms.util;

import net.minecraft.resources.ResourceLocation;

import chargedcharms.ChargedCharms;

public class ResourceLocationHelper {

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(ChargedCharms.MODID, path);
    }

}
