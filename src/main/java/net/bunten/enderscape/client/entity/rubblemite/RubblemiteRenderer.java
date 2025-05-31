package net.bunten.enderscape.client.entity.rubblemite;

import net.bunten.enderscape.Enderscape;
import net.bunten.enderscape.client.registry.EnderscapeEntityRenderData;
import net.bunten.enderscape.entity.rubblemite.Rubblemite;
import net.bunten.enderscape.entity.rubblemite.RubblemiteVariant;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class RubblemiteRenderer extends MobRenderer<Rubblemite, RubblemiteModel> {
    public RubblemiteRenderer(Context context) {
        super(context, new RubblemiteModel(context.bakeLayer(EnderscapeEntityRenderData.RUBBLEMITE)), 0.4F);
        addLayer(new RubblemiteEyesLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Rubblemite mob) {
        return Enderscape.id("textures/entity/rubblemite/" + RubblemiteVariant.get(mob).getName() + ".png");
    }
}