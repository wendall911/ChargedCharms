package chargedcharms.util;

import net.minecraft.resources.ResourceLocation;

import chargedcharms.ChargedCharms;

public class ResourceLocationHelper extends technology.roughness.whitenoise.util.ResourceLocationHelper {

    public static ResourceLocation prefix(String path) {
        return loc(ChargedCharms.MODID, path);
    }

}
