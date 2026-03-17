package com.acorsicanfrog.hungrywaters;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FabricConfig {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private FabricConfig() {}

    public static void load() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        Path configFile = configDir.resolve("hungrywaters.json");

        if (Files.exists(configFile)) {
            try {
                String json = Files.readString(configFile);
                JsonObject root = GSON.fromJson(json, JsonObject.class);
                readValues(root);
                LOGGER.info("Loaded Hungry Waters config from {}", configFile);
            } catch (Exception e) {
                LOGGER.error("Failed to read Hungry Waters config, using defaults", e);
            }
        } else {
            writeDefaults(configFile);
            LOGGER.info("Created default Hungry Waters config at {}", configFile);
        }
    }

    private static void readValues(JsonObject root) {
        if (root.has("stats")) {
            JsonObject stats = root.getAsJsonObject("stats");
            if (stats.has("scaleMin")) HungryWatersConfig.scaleMin = stats.get("scaleMin").getAsDouble();
            if (stats.has("scaleMax")) HungryWatersConfig.scaleMax = stats.get("scaleMax").getAsDouble();
            if (stats.has("attackDamage")) HungryWatersConfig.attackDamage = stats.get("attackDamage").getAsDouble();
            if (stats.has("movementSpeed")) HungryWatersConfig.movementSpeed = stats.get("movementSpeed").getAsDouble();
            if (stats.has("maxHealth")) HungryWatersConfig.maxHealth = stats.get("maxHealth").getAsDouble();
        }
        if (root.has("hunger")) {
            JsonObject hunger = root.getAsJsonObject("hunger");
            if (hunger.has("alwaysAggressive")) HungryWatersConfig.alwaysAggressive = hunger.get("alwaysAggressive").getAsBoolean();
            if (hunger.has("hungerMax")) HungryWatersConfig.hungerMax = hunger.get("hungerMax").getAsInt();
            if (hunger.has("hungerDrainRate")) HungryWatersConfig.hungerDrainRate = hunger.get("hungerDrainRate").getAsInt();
            if (hunger.has("randomStartHunger")) HungryWatersConfig.randomStartHunger = hunger.get("randomStartHunger").getAsBoolean();
        }
    }

    private static void writeDefaults(Path configFile) {
        JsonObject root = new JsonObject();

        JsonObject stats = new JsonObject();
        stats.addProperty("scaleMin", HungryWatersConfig.scaleMin);
        stats.addProperty("scaleMax", HungryWatersConfig.scaleMax);
        stats.addProperty("attackDamage", HungryWatersConfig.attackDamage);
        stats.addProperty("movementSpeed", HungryWatersConfig.movementSpeed);
        stats.addProperty("maxHealth", HungryWatersConfig.maxHealth);
        root.add("stats", stats);

        JsonObject hunger = new JsonObject();
        hunger.addProperty("alwaysAggressive", HungryWatersConfig.alwaysAggressive);
        hunger.addProperty("hungerMax", HungryWatersConfig.hungerMax);
        hunger.addProperty("hungerDrainRate", HungryWatersConfig.hungerDrainRate);
        hunger.addProperty("randomStartHunger", HungryWatersConfig.randomStartHunger);
        root.add("hunger", hunger);

        try {
            Files.writeString(configFile, GSON.toJson(root));
        } catch (IOException e) {
            LOGGER.error("Failed to write default Hungry Waters config", e);
        }
    }
}