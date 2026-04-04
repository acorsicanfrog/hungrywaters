package com.acorsicanfrog.hungrywaters.client;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class PiranhaRenderState extends LivingEntityRenderState 
{
    public boolean isAggressive;

    public final AnimationState swimDefaultAnimationState = new AnimationState();
    public final AnimationState swimAttackAnimationState = new AnimationState();
}