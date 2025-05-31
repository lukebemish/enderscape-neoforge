package net.bunten.enderscape.client.block;

import net.minecraft.client.color.block.BlockColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

public class BlinklightColorProvider implements BlockColor {

    public static final Vec3i[] COLORS = new Vec3i[] {
            new Vec3i(209, 225, 255),
            new Vec3i(255, 209, 250)
    };

    @Override
    public int getColor(BlockState state, BlockAndTintGetter view, BlockPos pos, int tint) {
        if (pos == null) pos = BlockPos.ZERO;

        int size = COLORS.length - 1;
        long i = (long) pos.getX() + (long) pos.getY() + (long) pos.getZ();
        double delta = i * 0.1;
        int index = Mth.floor(delta);
        int index2 = (index + 1) & size;
        delta -= index;
        index &= size;

        Vec3i color1 = COLORS[index];
        Vec3i color2 = COLORS[index2];

        int r = Mth.floor(Mth.lerp(delta, color1.getX(), color2.getX()));
        int g = Mth.floor(Mth.lerp(delta, color1.getY(), color2.getY()));
        int b = Mth.floor(Mth.lerp(delta, color1.getZ(), color2.getZ()));

        return color(r, g, b);
    }

    public static int color(int r, int g, int b) {
        return 255 << 24 | (r << 16) | (g << 8) | b;
    }
}