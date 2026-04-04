package com.acorsicanfrog.hungrywaters.client;

import com.acorsicanfrog.hungrywaters.HungryWatersCommon;
import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.Identifier;

public class PiranhaRenderer extends MobRenderer<PiranhaEntity, PiranhaRenderState, PiranhaModel> {

    private static final Identifier TEXTURE = Identifier.fromNamespaceAndPath(HungryWatersCommon.MODID, "textures/entity/piranha.png");

    public PiranhaRenderer(EntityRendererProvider.Context context) {
        super(context, new PiranhaModel(context.bakeLayer(PiranhaModel.LAYER_LOCATION)), 0.3F);
    }

    @Override
    public Identifier getTextureLocation(PiranhaRenderState state) {
        return TEXTURE;
    }

    @Override
    public PiranhaRenderState createRenderState() {
        return new PiranhaRenderState();
    }

    @Override
    public void extractRenderState(PiranhaEntity entity, PiranhaRenderState state, float partialTick) {
        super.extractRenderState(entity, state, partialTick);
        state.swimDefaultAnimationState.copyFrom(entity.swimDefaultAnimationState);
        state.swimAttackAnimationState.copyFrom(entity.swimAttackAnimationState);
        state.isAggressive = entity.isAggressive();
    }
}