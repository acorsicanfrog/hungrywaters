package com.acorsicanfrog.hungrywaters.client;

import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;
import net.minecraft.client.model.CodModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

/**
 * Uses the vanilla cod model and texture as a placeholder renderer.
 */
public class PiranhaRenderer extends MobRenderer<PiranhaEntity, CodModel<PiranhaEntity>> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.withDefaultNamespace("textures/entity/fish/cod.png");

    public PiranhaRenderer(EntityRendererProvider.Context context) {
        super(context, new CodModel<>(context.bakeLayer(ModelLayers.COD)), 0.3F);
    }

    @Override
    public ResourceLocation getTextureLocation(PiranhaEntity entity) {
        return TEXTURE;
    }
}
