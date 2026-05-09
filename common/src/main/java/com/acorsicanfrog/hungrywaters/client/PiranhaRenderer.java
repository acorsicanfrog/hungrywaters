package com.acorsicanfrog.hungrywaters.client;

import com.acorsicanfrog.hungrywaters.HungryWatersCommon;
import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;
import java.util.WeakHashMap;

public class PiranhaRenderer extends MobRenderer<PiranhaEntity, PiranhaRenderState, PiranhaModel> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(HungryWatersCommon.MODID, "textures/entity/piranha.png");

    private final Map<PiranhaEntity, SwimAnimationTracker> swimAnimationTrackers = new WeakHashMap<>();

    public PiranhaRenderer(EntityRendererProvider.Context context) {
        super(context, new PiranhaModel(context.bakeLayer(PiranhaModel.LAYER_LOCATION)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(PiranhaRenderState renderState) {
        return TEXTURE;
    }

    @Override
    public PiranhaRenderState createRenderState() {
        return new PiranhaRenderState();
    }

    @Override
    public void extractRenderState(PiranhaEntity entity, PiranhaRenderState renderState, float partialTick) {
        super.extractRenderState(entity, renderState, partialTick);

        SwimAnimationTracker tracker = this.swimAnimationTrackers.computeIfAbsent(entity, ignored -> new SwimAnimationTracker());
        float currentAgeInTicks = renderState.ageInTicks;
        float deltaAgeInTicks = Math.max(0.0F, currentAgeInTicks - tracker.lastAgeInTicks);

        tracker.lastAgeInTicks = currentAgeInTicks;
        tracker.swimAnimationTime += deltaAgeInTicks * 50.0F * PiranhaModel.getDefaultAnimationSpeed(renderState.walkAnimationSpeed);

        renderState.swimAnimationTime = tracker.swimAnimationTime;
        renderState.swimDefaultAnimationState.copyFrom(entity.swimDefaultAnimationState);
        renderState.swimAttackAnimationState.copyFrom(entity.swimAttackAnimationState);
    }

    private static final class SwimAnimationTracker {
        private float lastAgeInTicks;
        private float swimAnimationTime;
    }
}