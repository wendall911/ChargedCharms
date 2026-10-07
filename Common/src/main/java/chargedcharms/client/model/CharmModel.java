package chargedcharms.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;

public class CharmModel<T extends HumanoidRenderState> extends HumanoidModel<T> {

    public CharmModel(ModelPart part) {
        super(part);

        for (ModelPart modelPart : this.allParts()) {
            modelPart.visible = false;
        }

        this.root.visible = true;
        this.body.visible = true;
    }

    public static LayerDefinition createLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition part = mesh.getRoot();

        part.addOrReplaceChild(
            "body",
           CubeListBuilder.create().texOffs(0, 0)
               .addBox(-1.0F, 1.0F, -4.0F, 4, 4, 1, CubeDeformation.NONE.extend(0.1F)),
           PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(mesh, 16, 16);
    }

}
