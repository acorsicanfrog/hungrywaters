package com.acorsicanfrog.hungrywaters;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoForgeConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    // Stats settings
    public static final ModConfigSpec.DoubleValue SCALE_MIN;
    public static final ModConfigSpec.DoubleValue SCALE_MAX;
    public static final ModConfigSpec.DoubleValue ATTACK_DAMAGE;
    public static final ModConfigSpec.DoubleValue MOVEMENT_SPEED;
    public static final ModConfigSpec.DoubleValue MAX_HEALTH;

    // Hunger settings
    public static final ModConfigSpec.BooleanValue ALWAYS_AGGRESSIVE;
    public static final ModConfigSpec.IntValue HUNGER_MAX;
    public static final ModConfigSpec.IntValue HUNGER_DRAIN_RATE;
    public static final ModConfigSpec.BooleanValue RANDOM_START_HUNGER;

    static final ModConfigSpec SPEC;

    static {
        BUILDER.push("stats");

        SCALE_MIN = BUILDER
                .comment("Minimum scale multiplier for a piranha at spawn (1.0 = normal size)")
                .defineInRange("scaleMin", 0.8D, 0.1D, 5.0D);

        SCALE_MAX = BUILDER
                .comment("Maximum scale multiplier for a piranha at spawn (1.0 = normal size)")
                .defineInRange("scaleMax", 1.3D, 0.1D, 5.0D);

        ATTACK_DAMAGE = BUILDER
                .comment("Base attack damage for piranhas")
                .defineInRange("attackDamage", 2.0D, 0.5D, 100.0D);

        MOVEMENT_SPEED = BUILDER
                .comment("Base movement speed for piranhas")
                .defineInRange("movementSpeed", 0.3D, 0.05D, 5.0D);

        MAX_HEALTH = BUILDER
                .comment("Base max health for piranhas")
                .defineInRange("maxHealth", 6.0D, 1.0D, 1024.0D);

        BUILDER.pop();

        BUILDER.push("hunger");

        ALWAYS_AGGRESSIVE = BUILDER
                .comment("When enabled, piranhas are always in frenzy mode and will attack any nearby entity, bypassing the hunger system entirely.")
                .define("alwaysAggressive", false);

        HUNGER_MAX = BUILDER
                .comment("Maximum hunger value for a piranha (in ticks). When hunger reaches 0, piranhas enter attack mode.")
                .defineInRange("hungerMax", 180, 1, 72000);

        HUNGER_DRAIN_RATE = BUILDER
                .comment("Amount of hunger drained per second")
                .defineInRange("hungerDrainRate", 1, 1, 100);

        RANDOM_START_HUNGER = BUILDER
                .comment("When enabled, piranhas start with a random hunger value instead of the maximum.")
                .define("randomStartHunger", true);

        BUILDER.pop();

        SPEC = BUILDER.build();
    }

    public static void syncToCommon() {
        HungryWatersConfig.scaleMin = SCALE_MIN.getAsDouble();
        HungryWatersConfig.scaleMax = SCALE_MAX.getAsDouble();
        HungryWatersConfig.attackDamage = ATTACK_DAMAGE.getAsDouble();
        HungryWatersConfig.movementSpeed = MOVEMENT_SPEED.getAsDouble();
        HungryWatersConfig.maxHealth = MAX_HEALTH.getAsDouble();
        HungryWatersConfig.alwaysAggressive = ALWAYS_AGGRESSIVE.getAsBoolean();
        HungryWatersConfig.hungerMax = HUNGER_MAX.getAsInt();
        HungryWatersConfig.hungerDrainRate = HUNGER_DRAIN_RATE.getAsInt();
        HungryWatersConfig.randomStartHunger = RANDOM_START_HUNGER.getAsBoolean();
    }
}
