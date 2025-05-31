package net.bunten.enderscape.resources;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import dev.lukebemish.dynamicassetgenerator.api.PathAwareInputStreamSource;
import dev.lukebemish.dynamicassetgenerator.api.ResourceCache;
import dev.lukebemish.dynamicassetgenerator.api.ResourceGenerationContext;
import dev.lukebemish.dynamicassetgenerator.api.client.AssetResourceCache;
import net.bunten.enderscape.Enderscape;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.IoSupplier;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DynamicResources {
    public static final AssetResourceCache ASSET_CACHE =
            ResourceCache.register(new AssetResourceCache(Enderscape.id("runtime_generated_data")));
    
    private static final Gson GSON = new GsonBuilder().setLenient().create();
    
    static {
        ASSET_CACHE.planSource(new PathAwareInputStreamSource() {
            @Override
            public Set<ResourceLocation> getLocations(ResourceGenerationContext resourceGenerationContext) {
                return EVENTS.keySet();
            }

            @Override
            public IoSupplier<InputStream> get(ResourceLocation resourceLocation, ResourceGenerationContext resourceGenerationContext) {
                var existing = resourceGenerationContext.getResourceSource().getResource(resourceLocation);
                final ResourceModificationEvent.EventFile file;
                if (existing == null) {
                    file = new ResourceModificationEvent.EventFile() {
                        final JsonObject jsonObject = new JsonObject();
                        
                        @Override
                        public JsonObject getAsJsonObject() {
                            return jsonObject;
                        }
                    };
                } else {
                    try (var stream = existing.get();
                         var reader = new InputStreamReader(stream)) {
                        var existingObject = GSON.fromJson(reader, JsonObject.class);
                        file = new ResourceModificationEvent.EventFile() {
                            final JsonObject jsonObject = existingObject;

                            @Override
                            public JsonObject getAsJsonObject() {
                                return jsonObject;
                            }
                        };
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                }
                
                var context = new ResourceModificationEvent.EventContext() {
                    @Override
                    public ResourceModificationEvent.EventFile getFile() {
                        return file;
                    }
                };
                
                var modifications = new ArrayList<>(EVENTS.getOrDefault(resourceLocation, List.of()));
                
                modifications.sort(Comparator.comparing(ResourceModification::priority).thenComparing(ResourceModification::eventName));
                
                for (var modification : modifications) {
                    modification.eventHandler.runEvent(context);
                }
                
                String contents = GSON.toJson(context.getFile().getAsJsonObject());
                return () -> new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
            }
        });
    }
    
    private record ResourceModification(
            int priority,
            String eventName,
            ResourceModificationEvent eventHandler
    ) {}
    
    private static final Map<ResourceLocation, List<ResourceModification>> EVENTS = new HashMap<>();
    
    public static void registerEvent(int priority, String targetString, String eventName, ResourceModificationEvent eventHandler) {
        var target = ResourceLocation.parse(targetString + ".json");
        var modification = new ResourceModification(priority, eventName, eventHandler);
        var list = EVENTS.computeIfAbsent(target, k -> new ArrayList<>());
        list.add(modification);
    }
}
