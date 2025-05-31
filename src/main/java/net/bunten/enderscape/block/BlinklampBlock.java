package net.bunten.enderscape.block;

import net.bunten.enderscape.registry.EnderscapeBlockSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.jetbrains.annotations.Nullable;

public class BlinklampBlock extends Block {
    public static final IntegerProperty LUMINANCE = IntegerProperty.create("luminance", 0, 15);

    public BlinklampBlock(Properties properties) {
        super(properties.lightLevel((state -> BlinklampBlock.getLuminance(state))));
        registerDefaultState(defaultBlockState().setValue(LUMINANCE, 4));
    }

    public static int getLuminance(BlockState state) {
        return state.getValue(LUMINANCE);
    }

    private int findLuminance(Level world, BlockPos pos) {
        return 15 - world.getBestNeighborSignal(pos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LUMINANCE);
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(LUMINANCE, findLuminance(context.getLevel(), context.getClickedPos()));
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos blockPos2, boolean bl) {
        if (!level.isClientSide()) {
            int luminance = findLuminance(level, pos);
            if (luminance != getLuminance(state)) {
                level.scheduleTick(pos, this, 5);
            }
        }
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int luminance = findLuminance(world, pos);
        SoundEvent sound = luminance > getLuminance(state) ? EnderscapeBlockSounds.BLINKLAMP_INCREASE : EnderscapeBlockSounds.BLINKLAMP_DECREASE;

        world.setBlock(pos, state.setValue(LUMINANCE, luminance), 3);
        world.playSound(null, pos, sound, SoundSource.BLOCKS, 1, 1);
    }
}