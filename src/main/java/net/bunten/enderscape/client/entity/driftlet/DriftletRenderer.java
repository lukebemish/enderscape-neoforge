package net.bunten.enderscape.client.entity.driftlet;

import net.bunten.enderscape.Enderscape;
import net.bunten.enderscape.client.registry.EnderscapeEntityRenderData;
import net.bunten.enderscape.entity.drifter.Driftlet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class DriftletRenderer extends MobRenderer<Driftlet, DriftletModel> {
    public DriftletRenderer(Context context) {
        super(context, new DriftletModel(context.bakeLayer(EnderscapeEntityRenderData.DRIFTLET)), 0.9F);
    }

    @Override
    protected int getBlockLightLevel(Driftlet mob, BlockPos pos) {
        return Math.max(3, super.getBlockLightLevel(mob, pos));
    }

    @Override
    public ResourceLocation getTextureLocation(Driftlet mob) {
        return Enderscape.id("textures/entity/drifter/driftlet.png");
    }

    @Override
    protected RenderType getRenderType(Driftlet mob, boolean showBody, boolean translucent, boolean showOutline) {
        return showBody ? RenderType.entityTranslucent(getTextureLocation(mob)) : super.getRenderType(mob, showBody, translucent, showOutline);
    }
}