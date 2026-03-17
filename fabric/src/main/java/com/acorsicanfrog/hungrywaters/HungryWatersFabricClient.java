package com.acorsicanfrog.hungrywaters;

import com.acorsicanfrog.hungrywaters.client.PiranhaModel;
import com.acorsicanfrog.hungrywaters.client.PiranhaRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class HungryWatersFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(HungryWatersFabric.PIRANHA, PiranhaRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(PiranhaModel.LAYER_LOCATION, PiranhaModel::createBodyLayer);
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> -1, HungryWatersFabric.PIRANHA_SPAWN_EGG);
    }
}
