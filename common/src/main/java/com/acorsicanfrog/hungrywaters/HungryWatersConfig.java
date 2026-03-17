package com.acorsicanfrog.hungrywaters;

public final class HungryWatersConfig {
    // Stats
    public static double scaleMin = 0.8D;
    public static double scaleMax = 1.3D;
    public static double attackDamage = 2.0D;
    public static double movementSpeed = 0.3D;
    public static double maxHealth = 6.0D;

    // Hunger
    public static boolean alwaysAggressive = false;
    public static int hungerMax = 180;
    public static int hungerDrainRate = 1;
    public static boolean randomStartHunger = true;

    private HungryWatersConfig() {}
}
