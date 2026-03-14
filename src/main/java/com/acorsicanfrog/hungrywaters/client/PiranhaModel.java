package com.acorsicanfrog.hungrywaters.client;

import com.acorsicanfrog.hungrywaters.HungryWaters;
import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;

import net.minecraft.client.animation.KeyframeAnimations;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.AnimationState;
import org.joml.Vector3f;

public class PiranhaModel extends HierarchicalModel<PiranhaEntity> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(HungryWaters.MODID, "piranha"), "main");

    private static final Vector3f ANIM_VEC = new Vector3f();

    // Tweak these to match Blockbench preview
    private static final float ANIM_SPEED_IDLE = 1.0F;
    private static final float ANIM_SPEED_SWIM = 2.0F;
    private static final float ANIM_SPEED_ATTACK = 4.0F;
    
    private static final float ANIM_SCALE = 1.5F;

    private final ModelPart root;
    private final ModelPart bone;
    private final ModelPart Backbody;

    public PiranhaModel(ModelPart root) {
        this.root = root;
        this.bone = root.getChild("bone");
        this.Backbody = root.getChild("Backbody");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(17, 26).addBox(-1.0F, 1.0F, -5.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.0F, 3.0F));

        bone.addOrReplaceChild("Ttopteeth_r1", CubeListBuilder.create().texOffs(11, 26).addBox(-0.5F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(1.0F, 1.4F, -5.5F, 3.1416F, 0.0F, 0.0F));

        bone.addOrReplaceChild("Ttopteeth_r2", CubeListBuilder.create().texOffs(11, 26).addBox(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(-0.5F, 1.4F, -5.5F, 0.0F, 1.5708F, 3.1416F));

        bone.addOrReplaceChild("Mouth", CubeListBuilder.create().texOffs(17, 20).addBox(-1.0F, 0.1137F, -2.0233F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(0.0F, 0.9F, -4.4F, -0.096F, 0.0F, 0.0F));

        PartDefinition Mouth = bone.getChild("Mouth");

        Mouth.addOrReplaceChild("Bottomteeth_r1", CubeListBuilder.create().texOffs(11, 26).addBox(0.0F, -0.4024F, -0.5216F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-0.5F, 0.2279F, -2.0532F, 0.0F, 3.1416F, 0.0F));

        Mouth.addOrReplaceChild("Bottomteeth_r2", CubeListBuilder.create().texOffs(11, 26).addBox(-0.5216F, -0.4024F, -0.5F, 0.5F, 0.5F, 0.5F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(1.0F, 0.2279F, -2.0532F, 0.0F, 1.5708F, 0.0F));

        bone.addOrReplaceChild("bone2", CubeListBuilder.create().texOffs(11, 24).addBox(-0.5F, -2.5F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        bone.addOrReplaceChild("bone3", CubeListBuilder.create().texOffs(22, 20).addBox(-0.5F, 1.5F, -5.0F, 1.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 0.0F));

        PartDefinition TopNageoire = bone.addOrReplaceChild("TopNageoire", CubeListBuilder.create(), PartPose.offset(0.0F, -1.4F, -1.5F));

        TopNageoire.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(1, 22).addBox(0.0F, -1.7828F, -0.4924F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1F, 0.0F, -0.2618F, 0.0F, 0.0F));

        TopNageoire.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(1, 19).addBox(0.0F, -1.2828F, -1.4924F, 0.0F, 2.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition BottomNageoire = bone.addOrReplaceChild("BottomNageoire", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, -1.5F, -2.2689F, 0.0F, 0.0F));

        BottomNageoire.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(1, 14).addBox(0.0F, -1.4673F, -0.5418F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        BottomNageoire.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(3, 19).addBox(0.0F, -0.9673F, -1.5418F, 0.0F, 2.5F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        PartDefinition Leftnageoire = bone.addOrReplaceChild("Leftnageoire", CubeListBuilder.create(), PartPose.offset(1.0F, 2.0F, -3.5F));

        Leftnageoire.addOrReplaceChild("Leftnageoire_r1", CubeListBuilder.create().texOffs(1, 26).mirror().addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.3054F, 0.0F));

        PartDefinition Rightnageoire = bone.addOrReplaceChild("Rightnageoire", CubeListBuilder.create(), PartPose.offset(-1.0F, 2.0F, -3.5F));

        Rightnageoire.addOrReplaceChild("Rightnageoire_r1", CubeListBuilder.create().texOffs(1, 26).addBox(0.0F, -1.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, -0.3054F, 0.0F));

        PartDefinition Backbody = partdefinition.addOrReplaceChild("Backbody", CubeListBuilder.create().texOffs(27, 27).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 21.5F, 3.0F));

        PartDefinition Tail = Backbody.addOrReplaceChild("Tail", CubeListBuilder.create(), PartPose.offset(0.0F, -0.5F, 1.0F));

        Tail.addOrReplaceChild("Tail_r1", CubeListBuilder.create().texOffs(19, 23).addBox(0.0F, -1.4071F, -0.1273F, 0.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 32, 32);
    }

    @Override
    public ModelPart root() {
        return this.root;
    }

    @Override
    public void setupAnim(PiranhaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root().getAllParts().forEach(ModelPart::resetPose);

        float defaultSpeed = Mth.lerp(Math.min(limbSwingAmount * 5.0F, 1.0F), ANIM_SPEED_IDLE, ANIM_SPEED_SWIM);
        
        animateScaled(entity.swimDefaultAnimationState, PiranhaAnimation.SWIM_DEFAULT, ageInTicks, defaultSpeed, ANIM_SCALE);
        animateScaled(entity.swimAttackAnimationState, PiranhaAnimation.SWIM_ATTACK, ageInTicks, ANIM_SPEED_ATTACK, ANIM_SCALE);

        // Flop on land
        if (!entity.isInWater()) {
            this.bone.zRot = Mth.sin(0.6F * ageInTicks) * 0.7F;
        }
    }

    private void animateScaled(AnimationState state, net.minecraft.client.animation.AnimationDefinition definition, float ageInTicks, float speed, float scale) {
        state.updateTime(ageInTicks, speed);
        state.ifStarted(s -> KeyframeAnimations.animate(this, definition, s.getAccumulatedTime(), scale, ANIM_VEC));
    }
}