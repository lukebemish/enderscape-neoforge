package net.bunten.enderscape.resources;

import com.google.gson.JsonObject;

public interface ResourceModificationEvent {
    void runEvent(EventContext context);
    
    interface EventContext {
        EventFile getFile();
    }
    
    interface EventFile {
        JsonObject getAsJsonObject();
    }
}
