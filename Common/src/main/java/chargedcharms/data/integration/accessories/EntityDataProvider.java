package chargedcharms.data.integration.accessories;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import io.wispforest.accessories.api.data.AccessoriesTags;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

import static chargedcharms.ChargedCharms.prefix;

public class EntityDataProvider implements DataProvider {

    private final PackOutput packOutput;
    private final Map<Identifier, EntityData> entityDataMap = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(EntityData.class, new EntityData.Serializer()).create();

    public EntityDataProvider(final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void registerEntityData() {
        add(
            prefix("slots"),
            new EntityData(
                false,
                List.of(AccessoriesTags.DEFAULTED_TARGETS_BINDING),
                List.of(EntityData.Slot.CHARGED_CHARM)
            )
        );
    }

    @Override
    public @NotNull String getName() {
        return "Charged Charms - Accessories Entity Data";
    }

    protected void add(Identifier id, EntityData entityData) {
        this.entityDataMap.put(id, entityData);
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        List<CompletableFuture<?>> outputs = new ArrayList<>();

        registerEntityData();

        for (Map.Entry<Identifier, EntityData> entry : this.entityDataMap.entrySet()) {
            Path path = getPathProvider(entry.getKey());

            outputs.add(DataProvider.saveStable(cache, GSON.toJsonTree(entry.getValue()), path));
        }

        return CompletableFuture.allOf(outputs.toArray(CompletableFuture[]::new));
    }

    private Path getPathProvider(Identifier location) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "accessories/entity").json(location);
    }

}

