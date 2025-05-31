package net.bunten.enderscape.entity.drifter;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.bunten.enderscape.entity.ai.EnderscapeMemory;
import net.bunten.enderscape.entity.ai.EnderscapeSensors;
import net.bunten.enderscape.entity.ai.behavior.*;
import net.bunten.enderscape.registry.EnderscapeEntities;
import net.bunten.enderscape.registry.tag.EnderscapeItemTags;
import net.bunten.enderscape.registry.tag.EnderscapePoiTags;
import net.minecraft.core.GlobalPos;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public class DrifterAI {

    public static final Supplier<ImmutableList<MemoryModuleType<? extends Object>>> MEMORY_TYPES = Suppliers.memoize(() -> ImmutableList.of(
            EnderscapeMemory.AVOID_TARGET,
            EnderscapeMemory.BREED_TARGET,
            EnderscapeMemory.CANT_REACH_WALK_TARGET_SINCE,
            EnderscapeMemory.DRIFTER_FIND_HOME_COOLDOWN.get(),
            EnderscapeMemory.DRIFTER_JELLY_CHANGE_COOLDOWN.get(),
            EnderscapeMemory.HOME,
            EnderscapeMemory.HURT_BY_ENTITY,
            EnderscapeMemory.IS_PANICKING,
            EnderscapeMemory.IS_TEMPTED,
            EnderscapeMemory.LOOK_TARGET,
            EnderscapeMemory.NEAREST_ATTACKABLE,
            EnderscapeMemory.NEAREST_INTIMIDATOR.get(),
            EnderscapeMemory.NEAREST_LIVING_ENTITIES,
            EnderscapeMemory.NEAREST_VISIBLE_ADULT,
            EnderscapeMemory.NEAREST_VISIBLE_LIVING_ENTITIES,
            EnderscapeMemory.NEAREST_VISIBLE_PLAYER,
            EnderscapeMemory.PATH,
            EnderscapeMemory.TEMPTATION_COOLDOWN_TICKS,
            EnderscapeMemory.TEMPTING_PLAYER,
            EnderscapeMemory.WALK_TARGET
    ));

    public static final Supplier<ImmutableList<SensorType<? extends Sensor<? super AbstractDrifter>>>> SENSOR_TYPES = Suppliers.memoize(() -> ImmutableList.of(
            EnderscapeSensors.DRIFTER_TEMPTATIONS.get(),
            EnderscapeSensors.HURT_BY,
            EnderscapeSensors.IS_IN_WATER,
            EnderscapeSensors.NEAREST_ADULT_DRIFTER.get(),
            EnderscapeSensors.NEAREST_INTIMIDATOR.get(),
            EnderscapeSensors.NEAREST_LIVING_ENTITIES,
            EnderscapeSensors.NEAREST_PLAYERS
    ));

    public static GlobalPos getHome(AbstractDrifter mob) {
        return mob.getBrain().getMemory(EnderscapeMemory.HOME).get();
    }

    public static void setHome(AbstractDrifter mob, GlobalPos value) {
        if (mob.level() instanceof ServerLevel level) {
            level.getPoiManager().getType(value.pos()).ifPresent(type -> {
                level.getPoiManager().take(poi -> poi.is(EnderscapePoiTags.DRIFTER_HOME), (type2, pos) -> pos.equals(value.pos()), value.pos(), 1);
                DebugPackets.sendPoiTicketCountPacket(level, value.pos());
            });
        }
        mob.getBrain().setMemory(EnderscapeMemory.HOME, value);
    }

    public static Brain<?> makeBrain(Brain<AbstractDrifter> brain) {
        initCoreActivity(brain);
        initIdleActivity(brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();

        return brain;
    }

    private static void initCoreActivity(Brain<AbstractDrifter> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new CalmDownFromAttacker(24),
                new CalmDownFromIntimidator(24),

                new CountDownCooldownTicks(EnderscapeMemory.DRIFTER_FIND_HOME_COOLDOWN.get()),
                new CountDownCooldownTicks(EnderscapeMemory.DRIFTER_JELLY_CHANGE_COOLDOWN.get()),
                new CountDownCooldownTicks(EnderscapeMemory.TEMPTATION_COOLDOWN_TICKS),

                new DrifterStartOrStopLeakingJelly(),
                new LookAtTargetSink(45, 90),
                new MoveToTargetSink(),
                new DrifterRefreshHomePosition(),
                new Swim(0.8f),

                SetWalkTargetAwayFrom.entity(EnderscapeMemory.HURT_BY_ENTITY, 2, 12, true),
                SetWalkTargetAwayFrom.entity(EnderscapeMemory.NEAREST_INTIMIDATOR.get(), 2, 8, true)
            )
        );
    }

    private static void initIdleActivity(Brain<AbstractDrifter> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
            Pair.of(0, new AnimalMakeLove(EnderscapeEntities.DRIFTER.get())),
            Pair.of(1, new FollowTemptation(mob -> 1.25F)),
            Pair.of(2, BabyFollowAdult.create(UniformInt.of(4, 16), 2)),
            Pair.of(3, SetWalkTargetAwayFrom.entity(EnderscapeMemory.NEAREST_INTIMIDATOR.get(), 1, 12, true)),
            Pair.of(4, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0f, UniformInt.of(30, 60))),
            Pair.of(5, SetEntityLookTargetSometimes.create(EnderscapeEntities.DRIFTLET.get(), 6.0f, UniformInt.of(30, 60))),
            Pair.of(8, new RunOne<>(
                    ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT),
                    ImmutableList.of(
                            Pair.of(DrifterWanderAround.create(), 3),
                            Pair.of(new DoNothing(30, 60), 3)
                        )
                    )
                )
            )
        );
    }

    public static void updateActivity(AbstractDrifter entity) {
        entity.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.IDLE));
    }

    public static Predicate<ItemStack> getTemptations() {
        return stack -> stack.is(EnderscapeItemTags.DRIFTER_FOOD);
    }
}