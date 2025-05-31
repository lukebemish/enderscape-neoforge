package net.bunten.enderscape.client.entity.rubblemite;

import net.bunten.enderscape.entity.rubblemite.Rubblemite;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class RubblemiteModel extends HierarchicalModel<Rubblemite> {
    private final ModelPart shell;
    private final ModelPart head;
    private final ModelPart root;

    public RubblemiteModel(ModelPart root) {
        this.root = root;
        shell = root.getChild("shell");
        head = shell.getChild("head");
    }

    @Override
    public ModelPart root() {
        return root;
    }

    public static LayerDefinition createLayer() {
        CubeDeformation dilation = CubeDeformation.NONE;

        MeshDefinition data = new MeshDefinition();
        PartDefinition rootData = data.getRoot();

        PartDefinition shellData = rootData.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0).addBox(-4, -6, -4, 8, 6, 8, dilation), PartPose.offset(0, 24, 0));
        shellData.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 14).addBox(-2, -2, -1, 4, 4, 1, dilation), PartPose.offset(0, -2, -4));

        return LayerDefinition.create(data, 32, 32);
    }

    @Override
    public void setupAnim(Rubblemite mob, float animPos, float animSpeed, float age, float headYaw, float headPitch) {
        root().getAllParts().forEach(ModelPart::resetPose);

        float strength = 0.05F;
        float speed = 0.3F;
        float speed2 = speed * 2;

        if (mob.isDashing()) {
            shell.yRot = age;
        } else {
            head.xRot = -Mth.sin(age * speed) * strength;
            head.zRot = -Mth.sin(age * speed2 + Mth.HALF_PI) * strength;

            shell.xRot = Mth.sin(age * speed + Mth.HALF_PI) * strength;
            shell.zRot = Mth.sin(age * speed2) * strength;

            shell.xRot += (headPitch * (Mth.PI / 180)) / 2;
            shell.yRot += (headYaw * (Mth.PI / 180)) / 2;
        }
        
        head.visible = !mob.isInsideShell() && !mob.isDashing();
    }
}