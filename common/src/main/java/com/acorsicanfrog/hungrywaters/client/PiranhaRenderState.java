package com.acorsicanfrog.hungrywaters.client;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class PiranhaRenderState extends LivingEntityRenderState 
{
    public boolean isAggressive;

    public AnimationState swimDefaultAnimationState;
    public AnimationState swimAttackAnimationState;
    
    public float swimDefaultAnimationBlend;
    public float swimAttackAnimationBlend;
    public float swimDefaultAnimationTime;
}