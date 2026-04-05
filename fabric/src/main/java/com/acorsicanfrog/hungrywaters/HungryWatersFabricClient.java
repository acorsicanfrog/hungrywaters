package com.acorsicanfrog.hungrywaters;

import com.acorsicanfrog.hungrywaters.client.PiranhaModel;
import com.acorsicanfrog.hungrywaters.client.PiranhaRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.renderer.entity.EntityRenderers;

public class HungryWatersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRenderers.register(HungryWatersFabric.PIRANHA, PiranhaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(PiranhaModel.LAYER_LOCATION, PiranhaModel::createBodyLayer);
    }
}