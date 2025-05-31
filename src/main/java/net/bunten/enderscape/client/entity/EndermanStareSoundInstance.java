package net.bunten.enderscape.client.entity;

import net.bunten.enderscape.registry.EnderscapeEntitySounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.monster.EnderMan;

public class EndermanStareSoundInstance extends AbstractTickableSoundInstance {
    private final Minecraft client;
    private final int entityId;

    public EndermanStareSoundInstance(Minecraft client, int entityId) {
        super(EnderscapeEntitySounds.ENDERMAN_STARE_STEREO.get(), SoundSource.HOSTILE, SoundInstance.createUnseededRandom());

        this.client = client;
        this.entityId = entityId;

        delay = 5;
        volume = 1;
        pitch = 1;
    }

    @Override
    public void tick() {
       if (client.level == null || Minecraft.getInstance().level == null || volume < 0.01F) {
           stop();
       } else {
           if (!canPlay()) {
               volume = Mth.lerp(0.1F, volume, 0.0F);
               pitch = Mth.lerp(volume, 0.8F, 1.0F);
           }
       }
    }

    private boolean canPlay() {
        return client.level != null && client.level.getEntity(entityId) instanceof EnderMan enderman && enderman.isAlive() && enderman.isCreepy();
    }
}