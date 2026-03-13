// package com.acorsicanfrog.hungrywaters.client.debug;

// import com.acorsicanfrog.hungrywaters.HungryWaters;
// import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;
// import net.minecraft.client.Minecraft;
// import net.minecraft.client.gui.GuiGraphics;
// import net.minecraft.world.entity.Entity;
// import net.minecraft.world.entity.projectile.ProjectileUtil;
// import net.minecraft.world.phys.AABB;
// import net.minecraft.world.phys.EntityHitResult;
// import net.minecraft.world.phys.HitResult;
// import net.minecraft.world.phys.Vec3;
// import net.neoforged.api.distmarker.Dist;
// import net.neoforged.bus.api.SubscribeEvent;
// import net.neoforged.fml.common.EventBusSubscriber;
// import net.neoforged.neoforge.client.event.RenderGuiLayerEvent;

// @EventBusSubscriber(modid = HungryWaters.MODID, value = Dist.CLIENT)
// public class PiranhaDebugOverlay {

//     private static final double REACH = 16.0D;

//     @SubscribeEvent
//     public static void onRenderGuiOverlay(RenderGuiLayerEvent.Post event) {
//         Minecraft mc = Minecraft.getInstance();
//         if (mc.player == null || mc.level == null) return;
//         if (mc.getDebugOverlay().showDebugScreen()) return; // don't clutter F3 screen

//         PiranhaEntity piranha = getLookedAtPiranha(mc);
//         if (piranha == null) return;

//         int hunger = piranha.getHunger();
//         boolean hungry = piranha.isHungry();
//         boolean aggressive = piranha.isAggressive();

//         String hungerText = "Hunger: " + hunger;
//         String modeText;
//         int color;
//         if (hungry) {
//             modeText = "Mode: HUNTING";
//             color = 0xFFFF4444;
//         } else if (aggressive) {
//             modeText = "Mode: RETALIATING";
//             color = 0xFFFF8800;
//         } else {
//             modeText = "Mode: Passive";
//             color = 0xFF44FF44;
//         }

//         GuiGraphics gui = event.getGuiGraphics();
//         int screenWidth = mc.getWindow().getGuiScaledWidth();
//         int x = screenWidth / 2 + 10;
//         int y = mc.getWindow().getGuiScaledHeight() / 2 - 10;

//         gui.drawString(mc.font, hungerText, x, y, color);
//         gui.drawString(mc.font, modeText, x, y + 12, color);
//     }

//     private static PiranhaEntity getLookedAtPiranha(Minecraft mc) {
//         Entity camera = mc.getCameraEntity();
//         if (camera == null) return null;

//         Vec3 eyePos = camera.getEyePosition(1.0F);
//         Vec3 lookVec = camera.getViewVector(1.0F);
//         Vec3 endPos = eyePos.add(lookVec.scale(REACH));

//         AABB searchBox = camera.getBoundingBox().expandTowards(lookVec.scale(REACH)).inflate(1.0D);
//         EntityHitResult hitResult = ProjectileUtil.getEntityHitResult(
//                 camera, eyePos, endPos, searchBox,
//                 entity -> !entity.isSpectator() && entity.isPickable(),
//                 REACH * REACH
//         );

//         if (hitResult != null && hitResult.getType() == HitResult.Type.ENTITY
//                 && hitResult.getEntity() instanceof PiranhaEntity piranha) {
//             return piranha;
//         }
//         return null;
//     }
// }