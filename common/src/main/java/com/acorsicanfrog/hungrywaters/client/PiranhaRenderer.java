package com.acorsicanfrog.hungrywaters.client;

import com.acorsicanfrog.hungrywaters.HungryWatersCommon;
import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class PiranhaRenderer extends MobRenderer<PiranhaEntity, PiranhaRenderState, PiranhaModel> {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(HungryWatersCommon.MODID, "textures/entity/piranha.png");

    public PiranhaRenderer(EntityRendererProvider.Context context) 
    {
        super(context, new PiranhaModel(context.bakeLayer(PiranhaModel.LAYER_LOCATION)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(PiranhaRenderState renderState) 
    {
        return TEXTURE;
    }

    @Override
    public PiranhaRenderState createRenderState() 
    {
        return new PiranhaRenderState();
    }

    @Override
    public void extractRenderState(PiranhaEntity entity, PiranhaRenderState reusedState, float partialTick) 
    {
        super.extractRenderState(entity, reusedState, partialTick);
        
        reusedState.isAggressive = entity.isAggressive();
        reusedState.swimDefaultAnimationState = entity.swimDefaultAnimationState;
        reusedState.swimAttackAnimationState = entity.swimAttackAnimationState;
        reusedState.swimDefaultAnimationBlend = entity.getSwimDefaultAnimationBlend();
        reusedState.swimAttackAnimationBlend = entity.getSwimAttackAnimationBlend();
        reusedState.swimDefaultAnimationTime = entity.getSwimDefaultAnimationTime(partialTick);
    }
}