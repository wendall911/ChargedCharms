package chargedcharms.data.integration.accessories;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class EntityData {

    private final boolean replace;
    private static final List<String> entities = new ArrayList<>();
    private static final List<String> slots = new ArrayList<>();

    public EntityData(boolean replace, List<TagKey<EntityType<?>>> entities, List<Slot> slots) {
        this.replace = replace;
        if (entities != null) {
            for (TagKey<EntityType<?>> entity : entities) {
                String location = "#" + entity.location();

                if (!EntityData.entities.contains(location)) {
                    EntityData.entities.add(location);
                }
            }
        }

        if (slots != null) {
            for (Slot slot : slots) {
                String name = slot.toString();
                if (!EntityData.slots.contains(name)) {
                    EntityData.slots.add(name);
                }
            }
        }
    }

    public static class Serializer implements JsonSerializer<EntityData> {

        @Override
        public JsonElement serialize(EntityData entityData, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();

            json.addProperty("replace", entityData.replace());
            if (!entityData.entities().isEmpty()) {
                var entities = jsonSerializationContext.serialize(entityData.entities());
                json.add("entities", entities);
            }
            if (!entityData.slots().isEmpty()) {
                var slots = jsonSerializationContext.serialize(entityData.slots());
                json.add("slots", slots);
            }

            return json;
        }

    }

    public boolean replace() {
        return replace;
    }

    public List<String> entities() {
        return entities;
    }

    public List<String> slots() {
        return slots;
    }

    public enum Slot {
        ANKLET,
        BACK,
        BELT,
        CAPE,
        CHARGED_CHARM,
        CHARM,
        FACE,
        HAND,
        HAT,
        NECKLACE,
        RING,
        SHOES,
        WRIST;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

}
