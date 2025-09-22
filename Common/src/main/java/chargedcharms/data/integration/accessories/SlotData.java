package chargedcharms.data.integration.accessories;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.minecraft.resources.ResourceLocation;

public class SlotData {

    private static final List<String> validators = new ArrayList<>();
    private final boolean replace;
    private final int amount;
    private final Operation operation;
    private final int order;
    private final ResourceLocation icon;

    public SlotData(boolean replace, int amount, Operation operation, int order, ResourceLocation icon, @Nullable List<Validator> validators) {
        this.replace = replace;
        this.amount = amount;
        this.operation = operation;
        this.order = order;
        this.icon = icon;

        if (validators != null) {
            for (Validator validator : validators) {
                String location = validator.location();
                if (!SlotData.validators.contains(location)) {
                    SlotData.validators.add(location);
                }
            }
        }
    }

    public static class Serializer implements JsonSerializer<SlotData> {

        @Override
        public JsonElement serialize(SlotData slotData, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonObject json = new JsonObject();

            json.addProperty("replace", slotData.replace());
            json.addProperty("amount", slotData.amount());
            json.addProperty("operation", slotData.getOperation().toString());
            json.addProperty("order", slotData.order());
            json.addProperty("icon", slotData.icon().toString());
            if (!slotData.validators().isEmpty()) {
                var validators = jsonSerializationContext.serialize(slotData.validators());
                json.add("validators", validators);
            }

            return json;
        }

    }

    public boolean replace() {
        return replace;
    }

    public int amount() {
        return amount;
    }

    public Operation getOperation() {
        return operation;
    }

    public int order() {
        return order;
    }

    public ResourceLocation icon() {
        return icon;
    }

    public List<String> validators() {
        return validators;
    }

    public enum Operation {
        SET,
        ADD,
        REMOVE;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }
    }

    public enum Validator {
        TAG,
        COMPONENT,
        ATTRIBUTE,
        ELYTRA_ITEM,
        TOTEM_ITEM,
        BANNER_ITEM;

        @Override
        public String toString() {
            return this.name().toLowerCase();
        }

        public String location() {
            return "accessories:" + this;
        }
    }

}
