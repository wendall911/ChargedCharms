package chargedcharms.data.integration.accessories;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import static chargedcharms.ChargedCharms.prefix;
import static technology.roughness.whitenoise.util.ResourceLocationHelper.loc;

public class SlotDataProvider implements DataProvider {

    private final PackOutput packOutput;
    private final Map<ResourceLocation, SlotData> slotDataMap = new HashMap<>();
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(SlotData.class, new SlotData.Serializer()).create();

    public SlotDataProvider(final PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    protected void registerSlotData() {
        add(
            prefix("charged_charm"),
            new SlotData(
                false,
                2,
                SlotData.Operation.SET,
                1000,
                loc("accessories", "container/slot/charm"),
                List.of(SlotData.Validator.TAG, SlotData.Validator.COMPONENT, SlotData.Validator.ATTRIBUTE))
        );
    }

    @Override
    public @NotNull String getName() {
        return "Charged Charms - Accessories Slot Data";
    }

    protected void add(ResourceLocation id, SlotData slotData) {
        this.slotDataMap.put(id, slotData);
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cache) {
        List<CompletableFuture<?>> outputs = new ArrayList<>();

        registerSlotData();

        for (Map.Entry<ResourceLocation, SlotData> entry : this.slotDataMap.entrySet()) {
            Path path = getPathProvider(entry.getKey());

            outputs.add(DataProvider.saveStable(cache, GSON.toJsonTree(entry.getValue()), path));
        }

        return CompletableFuture.allOf(outputs.toArray(CompletableFuture[]::new));
    }

    private Path getPathProvider(ResourceLocation location) {
        return this.packOutput.createPathProvider(PackOutput.Target.DATA_PACK, "accessories/slot").json(location);
    }

}
