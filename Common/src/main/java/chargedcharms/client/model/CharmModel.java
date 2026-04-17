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

public class CharmModel extends HumanoidModel<HumanoidRenderState> {

    public CharmModel(ModelPart root) {
        super(root);

        this.body.visible = true;
    }

    public static LayerDefinition getLayerDefinition() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("body",
            CubeListBuilder.create().texOffs(0, 0)
                .addBox(-2.0F, 2.0F, -3.0F, 4.0F, 4.0F, 1.0F, CubeDeformation.NONE.extend(0.4F)),
            PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F)
        );

        return LayerDefinition.create(meshdefinition, 16, 16);
    }

}
