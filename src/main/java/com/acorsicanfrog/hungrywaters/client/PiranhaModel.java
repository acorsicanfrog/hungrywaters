package com.acorsicanfrog.hungrywaters.client;

import com.acorsicanfrog.hungrywaters.HungryWaters;
import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class PiranhaModel extends HierarchicalModel<PiranhaEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(HungryWaters.MODID, "piranha"), "main");

    private final ModelPart root;
    private final ModelPart firstdraft;

    public PiranhaModel(ModelPart root) {
        this.root = root;
        this.firstdraft = root.getChild("Firstdraft");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition Firstdraft = partdefinition.addOrReplaceChild("Firstdraft", CubeListBuilder.create(), PartPose.offset(0.0F, 20.3F, -2.4F));

        Firstdraft.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1, 1).mirror().addBox(1.0F, -1.0F, -0.6F, 0.0F, 1.0F, 0.6F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.3F, 0.8F, -2.0F, -1.5708F, -1.3526F, 1.5708F));

        Firstdraft.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1, 1).mirror().addBox(1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 0.4F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.1F, -0.2F, 0.0F, -1.5708F, -1.3526F, 1.5708F));

        Firstdraft.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(0.8F, -1.0F, -1.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 1.2F, -0.1F, -0.2182F, 0.0F, 0.0F));

        Firstdraft.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(1, 1).addBox(-1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.1F, -0.2F, 0.0F, -1.5708F, 1.3526F, -1.5708F));

        Firstdraft.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(1, 1).addBox(-1.0F, -1.0F, -0.6F, 0.0F, 1.0F, 0.6F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3F, 0.8F, -2.0F, -1.5708F, 1.3526F, -1.5708F));

        Firstdraft.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(1, 1).addBox(-1.0F, -1.0F, -1.0F, 0.0F, 1.0F, 0.4F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, -0.2F, 0.0F, -1.5708F, 1.3526F, -1.5708F));

        Firstdraft.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(16, 5).addBox(-1.0F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(16, 5).addBox(-1.2F, 0.0F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 0.8F, 1.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition bone2 = Firstdraft.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.9F, -2.5F, -4.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 8).addBox(-1.9F, -2.5F, -4.4F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(-1, 13).addBox(-1.9F, -0.5F, -3.4F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(12, 14).addBox(-1.4F, -3.5F, -3.4F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(16, 0).addBox(-1.4F, 0.5F, -3.4F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.9F, 1.2F, 3.8F));

        bone2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(24, 26).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8F, 1.0F, -2.0F, 0.0F, -0.5236F, 0.0F));

        bone2.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(20, 19).addBox(-1.0F, -2.0F, -1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, -0.35F, 3.5F, -0.1745F, 0.0F, 0.0F));

        bone2.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(6, 19).addBox(-1.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, -0.05F, 1.9F, 0.0873F, 0.0F, 0.0F));

        bone2.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(12, 19).addBox(1.0F, -2.0F, -1.0F, 0.0F, 0.6F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 19).addBox(1.0F, -1.4F, -2.0F, 0.0F, 1.4F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.9F, -2.5F, 0.6F, -0.3491F, 0.0F, 0.0F));

        bone2.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(16, 8).addBox(0.0F, -2.0F, -2.0F, 0.0F, 1.4F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(16, 19).addBox(0.0F, -0.6F, -2.0F, 0.0F, 0.6F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.9F, 1.5F, 1.6F, 0.6981F, 0.0F, 0.0F));

        bone2.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(24, 26).mirror().addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 1.0F, -2.0F, 0.0F, 0.5236F, 0.0F));

        PartDefinition bone3 = Firstdraft.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-0.1F, -2.5F, -4.4F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(-1, 13).mirror().addBox(-0.1F, -0.5F, -3.4F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(12, 14).mirror().addBox(0.4F, -3.5F, -3.4F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(16, 0).mirror().addBox(0.4F, 0.5F, -3.4F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 8).mirror().addBox(-0.1F, -2.5F, -4.4F, 2.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-0.9F, 1.2F, 3.8F));

        bone3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(12, 19).mirror().addBox(-1.0F, -2.0F, -1.0F, 0.0F, 0.6F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 19).mirror().addBox(-1.0F, -1.4F, -2.0F, 0.0F, 1.4F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.9F, -2.5F, 0.6F, -0.3491F, 0.0F, 0.0F));

        bone3.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(16, 8).mirror().addBox(0.0F, -2.0F, -2.0F, 0.0F, 1.4F, 3.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(16, 19).mirror().addBox(0.0F, -0.6F, -2.0F, 0.0F, 0.6F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.9F, 1.5F, 1.6F, 0.6981F, 0.0F, 0.0F));

        bone3.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(20, 19).mirror().addBox(1.0F, -2.0F, -1.0F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.1F, -0.35F, 3.5F, -0.1745F, 0.0F, 0.0F));

        bone3.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(6, 19).mirror().addBox(0.0F, -2.0F, -1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.4F, -0.05F, 1.9F, 0.0873F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("Final", CubeListBuilder.create(), PartPose.offset(0.0F, 20.3F, -2.4F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(PiranhaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float speed = entity.isInWater() ? 1.0F : 1.5F;
        this.firstdraft.yRot = -speed * 0.45F * Mth.sin(0.6F * ageInTicks);
    }
}
