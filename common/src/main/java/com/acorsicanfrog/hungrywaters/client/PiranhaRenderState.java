package com.acorsicanfrog.hungrywaters.client;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public class PiranhaRenderState extends LivingEntityRenderState {
    public float swimAnimationTime;
    public final AnimationState swimDefaultAnimationState = new AnimationState();
    public final AnimationState swimAttackAnimationState = new AnimationState();
}