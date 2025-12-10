package chargedcharms.util;

import net.minecraft.resources.Identifier;

import chargedcharms.ChargedCharms;

public class ResourceLocationHelper extends technology.roughness.whitenoise.util.ResourceLocationHelper {

    public static Identifier prefix(String path) {
        return loc(ChargedCharms.MODID, path);
    }

}
