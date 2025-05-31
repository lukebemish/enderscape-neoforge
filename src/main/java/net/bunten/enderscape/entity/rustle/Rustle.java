package net.bunten.enderscape.entity.rustle;

import com.mojang.serialization.Dynamic;
import net.bunten.enderscape.entity.ai.EnderscapeMemory;
import net.bunten.enderscape.registry.*;
import net.bunten.enderscape.registry.tag.EnderscapeBlockTags;
import net.bunten.enderscape.registry.tag.EnderscapeItemTags;
import net.bunten.enderscape.registry.tag.EnderscapePoiTags;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bucketable;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class Rustle extends Animal implements Bucketable, Shearable {

    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(Rustle.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> FROM_BUCKET = SynchedEntityData.defineId(Rustle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(Rustle.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SHEARED = SynchedEntityData.defineId(Rustle.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState sleepingAnimationState = new AnimationState();

    private long lastBumpTimestamp = 0;
    private static final long BUMP_COOLDOWN_TIME = 20;

    public Rustle(EntityType<? extends Rustle> type, Level world) {
        super(type, world);
        setPathfindingMalus(PathType.WATER, -1);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createMobAttributes()
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.13)
                .add(Attributes.SAFE_FALL_DISTANCE, 3);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_FLAGS_ID, (byte) 0);
        builder.define(FROM_BUCKET, false);
        builder.define(SLEEPING, false);
        builder.define(SHEARED, false);
    }


    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putBoolean("FromBucket", fromBucket());
        tag.putBoolean("Sleeping", isSleeping());
        tag.putBoolean("Sheared", isSheared());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        setFromBucket(tag.getBoolean("FromBucket"));
        setSleeping(tag.getBoolean("Sleeping"));
        setSheared(tag.getBoolean("Sheared"));
    }

    @Override
    public void saveToBucketTag(ItemStack stack) {
        Bucketable.saveDefaultDataToBucketTag(this, stack);

        CustomData.update(DataComponents.BUCKET_ENTITY_DATA, stack, (tag) -> {
            tag.putInt("Age", getAge());

            if (isSheared()) tag.putBoolean("Sheared", isSheared());
            if (getBrain().hasMemoryValue(EnderscapeMemory.RUSTLE_HAIR_REGROWTH_COOLDOWN.get())) tag.putInt("HairRegrowthCooldownTicks", getBrain().getMemory(EnderscapeMemory.RUSTLE_HAIR_REGROWTH_COOLDOWN.get()).orElseThrow());
        });
    }

    @Override
    public void loadFromBucketTag(CompoundTag tag) {
        Bucketable.loadDefaultDataFromBucketTag(this, tag);

        if (tag.contains("Age")) setAge(tag.getInt("Age"));
        if (tag.contains("Sheared")) setSheared(tag.getBoolean("Sheared"));
        if (tag.contains("HairRegrowthCooldownTicks")) getBrain().setMemory(EnderscapeMemory.RUSTLE_HAIR_REGROWTH_COOLDOWN.get(), tag.getInt("HairRegrowthCooldownTicks"));
    }

    @Override
    public boolean requiresCustomPersistence() {
        return super.requiresCustomPersistence() || fromBucket();
    }

    @Override
    protected Brain.Provider<Rustle> brainProvider() {
        return Brain.provider(RustleAI.MEMORY_TYPES.get(), RustleAI.SENSOR_TYPES.get());
    }

    @Override
    protected Brain<?> makeBrain(Dynamic<?> dynamic) {
        return RustleAI.makeBrain(brainProvider().makeBrain(dynamic));
    }

    @Override
    @SuppressWarnings("unchecked")
    public Brain<Rustle> getBrain() {
        return (Brain<Rustle>) super.getBrain();
    }

    @Override
    public void tick() {
        super.tick();

        if (isSleeping()) {
            sleepingAnimationState.startIfStopped(tickCount);
        } else {
            sleepingAnimationState.stop();
        }

        if (isAlive() && isSleeping() && level().getGameTime() % (60 + getRandom().nextInt(20)) == 0 && level().isClientSide()) {
            Vec3 pos = position().add(getLookAngle().scale(0.5));
            level().addParticle(EnderscapeParticles.RUSTLE_SLEEPING_BUBBLE.get(), pos.x, getY() + getBbHeight() - 0.1F, pos.z, 0, 0, 0);
        }
    }

    @Override
    protected void customServerAiStep() {
        if (level() instanceof ServerLevel server) {
            ProfilerFiller profiler = server.getProfiler();

            profiler.push("rustleBrain");
            getBrain().tick(server, this);
            profiler.pop();

            profiler.push("rustleActivityUpdate");
            RustleAI.updateActivity(this);
            profiler.pop();

            if (isSleeping()) {
                getBrain().getMemory(EnderscapeMemory.RUSTLE_SLEEPING_SPOT.get()).ifPresentOrElse(pos -> {
                    if (!server.getPoiManager().exists(pos, type -> type.is(EnderscapePoiTags.RUSTLE_SLEEPING_SPOT))) {
                        getBrain().eraseMemory(EnderscapeMemory.RUSTLE_SLEEPING_SPOT.get());
                        wakeUp();
                    }

                    if (isInWaterRainOrBubble() || getDeltaMovement().lengthSqr() > 0.1 || !server.getPoiManager().exists(blockPosition(), type -> type.is(EnderscapePoiTags.RUSTLE_SLEEPING_SPOT))) wakeUp();
                }, this::wakeUp);
            }

            setSleeping(getBrain().hasMemoryValue(EnderscapeMemory.RUSTLE_SLEEP_TICKS.get()));
        }

        super.customServerAiStep();
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return level.getBlockState(pos.below()).is(EnderscapeBlockTags.RUSTLE_PREFERRED) ? 10 : 0;
    }

    public static boolean canSpawn(EntityType<?> type, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(EnderscapeBlockTags.RUSTLE_SPAWNABLE_ON);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data) {
        if (reason == MobSpawnType.BUCKET) return data;
        return super.finalizeSpawn(level, difficulty, reason, data);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance instance) {
        return !instance.is(MobEffects.POISON) && super.canBeAffected(instance);
    }

    @Override
    protected void doPush(Entity entity) {
        super.doPush(entity);

        if (getVehicle() == null && entity.getType() != getType() && !entity.isCrouching()) {
            long gameTime = level().getGameTime();

            if (gameTime - lastBumpTimestamp > BUMP_COOLDOWN_TIME) {
                lastBumpTimestamp = gameTime;
                playSound(EnderscapeEntitySounds.RUSTLE_BUMP.get(), 1, getVoicePitch());
                getNavigation().stop();
                if (isSleeping()) wakeUp();
            }
        }
    }

    @Override
    public boolean fromBucket() {
        return entityData.get(FROM_BUCKET);
    }

    @Override
    public void setFromBucket(boolean value) {
        entityData.set(FROM_BUCKET, value);
    }

    @Override
    public boolean isSleeping() {
        return entityData.get(SLEEPING);
    }

    public void setSleeping(boolean value) {
        entityData.set(SLEEPING, value);
    }

    public void wakeUp() {
        getBrain().eraseMemory(EnderscapeMemory.RUSTLE_SLEEPING_SPOT.get());
        getBrain().eraseMemory(EnderscapeMemory.RUSTLE_SLEEP_TICKS.get());
        getBrain().setMemoryWithExpiry(EnderscapeMemory.RUSTLE_SLEEPING_ON_COOLDOWN.get(), true,100);
        setSleeping(false);
    }

    public boolean isSheared() {
        return entityData.get(SHEARED);
    }

    public void setSheared(boolean value) {
        entityData.set(SHEARED, value);
    }

    @Override
    public void shear(SoundSource source) {
        if (level() instanceof ServerLevel server) {
            LootTable lootTable = server.getServer().reloadableRegistries().getLootTable(EnderscapeEntityLootTables.SHEARING_RUSTLE);
            LootParams lootParams = new LootParams.Builder(server)
                    .withParameter(LootContextParams.ORIGIN, position())
                    .withParameter(LootContextParams.THIS_ENTITY, this)
                    .create(LootContextParamSets.SHEARING);

            for (ItemStack item : lootTable.getRandomItems(lootParams)) {
                ItemEntity entity = spawnAtLocation(item, 0.2F);
                if (entity != null) {
                    entity.setDeltaMovement(entity.getDeltaMovement().add(Mth.nextFloat(random, -0.1F, 0.1F), Mth.nextFloat(random, 0, 0.05F), Mth.nextFloat(random, -0.1F, 0.1F)));
                }
            }
        }

        playSound(EnderscapeEntitySounds.RUSTLE_SHEAR.get(), 1, 1);
        setSheared(true);
        RustleAI.refreshNaturalHairGrowthCooldown(this);
    }

    @Override
    public boolean readyForShearing() {
        return isAlive() && !isBaby() && !isSheared();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean result = super.hurt(source, amount);
        if (result && isSleeping()) wakeUp();
        return result;
    }

    public static void regrowHairWithParticles(ServerLevel level, Rustle mob) {
        Vec3 pos = mob.position();

        level.sendParticles(ParticleTypes.HAPPY_VILLAGER, pos.x, pos.y + 0.5, pos.z, 5, 0.5F, 0.5F, 0.5F, 0);
        mob.setSheared(false);

        RustleAI.refreshNaturalHairGrowthCooldown(mob);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (isFood(stack) && isSleeping()) return InteractionResult.PASS;

        if (isAlive()) {
            if (stack.getItem() == Items.BUCKET) {
                playSound(getPickupSound(), 1, 1);

                ItemStack bucket = getBucketItemStack();
                saveToBucketTag(bucket);
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, bucket, false));
                if (player instanceof ServerPlayer server) CriteriaTriggers.FILLED_BUCKET.trigger(server, bucket);

                if (isLeashed()) dropLeash(true, true);

                discard();

                return InteractionResult.SUCCESS;
            }

            if (stack.getItem() instanceof ShearsItem && readyForShearing()) {
                if (level() instanceof ServerLevel server) {
                    shear(SoundSource.PLAYERS);
                    stack.hurtAndBreak(1, player, getSlotForHand(hand));
                    gameEvent(GameEvent.SHEAR, player);

                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.CONSUME;
            }
        }

        return super.mobInteract(player, hand);
    }

    @Override
    public SoundEvent getPickupSound() {
        return EnderscapeItemSounds.RUSTLE_BUCKET_FILL.get();
    }

    @Override
    public ItemStack getBucketItemStack() {
        return new ItemStack(EnderscapeItems.RUSTLE_BUCKET.get());
    }

    @Override
    public void travel(Vec3 vec) {
        if (isSleeping()) {
            if (getNavigation().getPath() != null) getNavigation().stop();
            vec = Vec3.ZERO;
        }
        super.travel(vec);
    }

    @Override
    public void lookAt(Entity entity, float f, float g) {
        if (isSleeping()) return;
        super.lookAt(entity, f, g);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return isSleeping() ? EnderscapeEntitySounds.RUSTLE_SNORE.get() : EnderscapeEntitySounds.RUSTLE_AMBIENT.get();
    }

    @Override
    protected @Nullable SoundEvent getHurtSound(DamageSource source) {
        return EnderscapeEntitySounds.RUSTLE_HURT.get();
    }

    @Override
    protected @Nullable SoundEvent getDeathSound() {
        return EnderscapeEntitySounds.RUSTLE_DEATH.get();
    }

    protected SoundEvent getStepSound() {
        return EnderscapeEntitySounds.RUSTLE_STEP.get();
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        playSound(getStepSound(), 0.15F, Mth.nextFloat(random, 0.9F, 1.1F));
    }

    @Override
    protected void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
        super.usePlayerItem(player, hand, stack);
        if (isFood(stack)) playEatingSound();
    }

    protected void playEatingSound() {
        level().playSound(null, this, EnderscapeEntitySounds.RUSTLE_EAT.get(), getSoundSource(), getSoundVolume(), getVoicePitch());
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(EnderscapeItemTags.RUSTLE_FOOD);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return EnderscapeEntities.RUSTLE.get().create(level);
    }
}