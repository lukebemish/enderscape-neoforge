package net.bunten.enderscape.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import net.bunten.enderscape.entity.ai.EnderscapeMemory;
import net.bunten.enderscape.entity.drifter.AbstractDrifter;
import net.bunten.enderscape.registry.tag.EnderscapeEntityTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.sensing.Sensor;

import java.util.Set;

public class NearestIntimidatorSensor extends Sensor<AbstractDrifter> {

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(
            EnderscapeMemory.NEAREST_VISIBLE_LIVING_ENTITIES
        );
    }

    @Override
    protected void doTick(ServerLevel level, AbstractDrifter mob) {
        Brain<AbstractDrifter> brain = mob.getBrain();
        NearestVisibleLivingEntities nearby = brain.getMemory(EnderscapeMemory.NEAREST_VISIBLE_LIVING_ENTITIES).orElse(NearestVisibleLivingEntities.empty());
        for (LivingEntity next : nearby.findAll((next) -> next.getType().is(EnderscapeEntityTags.DRIFTERS_INTIMIDATED_BY))) {
            brain.setMemory(EnderscapeMemory.NEAREST_INTIMIDATOR.get(), next);
            break;
        }
    }
}