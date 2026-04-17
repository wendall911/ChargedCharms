package chargedcharms;

import java.util.Map;

import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import chargedcharms.client.CharmRenderer;
import chargedcharms.common.item.ChargedCharmsItems;

public class ChargedCharmsClient {

    public static void init() {
        for (Map.Entry<Identifier, Item> entry : ChargedCharmsItems.getAll().entrySet()) {
        }
    }

}
