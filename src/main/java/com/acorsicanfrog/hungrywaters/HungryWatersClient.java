package com.acorsicanfrog.hungrywaters;

import com.acorsicanfrog.hungrywaters.client.PiranhaModel;
import com.acorsicanfrog.hungrywaters.client.PiranhaRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = HungryWaters.MODID, dist = Dist.CLIENT)
public class HungryWatersClient {
    public HungryWatersClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @EventBusSubscriber(modid = HungryWaters.MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(HungryWaters.PIRANHA.get(), PiranhaRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(PiranhaModel.LAYER_LOCATION, PiranhaModel::createBodyLayer);
        }

        // DeferredSpawnEggItem registers tint colors at HIGHEST priority.
        // Our texture is already fully colored, so we override the tint to -1 (no tint).
        @SubscribeEvent(priority = EventPriority.LOW)
        public static void onRegisterItemColors(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tintIndex) -> -1, HungryWaters.PIRANHA_SPAWN_EGG.get());
        }
    }
}