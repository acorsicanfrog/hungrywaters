package com.acorsicanfrog.hungrywaters;

import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;

import net.minecraft.tags.BiomeTags;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;

public class HungryWatersFabric implements ModInitializer {

    // Entity Type
    private static final Identifier PIRANHA_ID = Identifier.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha");

    public static final EntityType<PiranhaEntity> PIRANHA = Registry.register(
            BuiltInRegistries.ENTITY_TYPE,
            PIRANHA_ID,
            EntityType.Builder.<PiranhaEntity>of(PiranhaEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5F, 0.3F)
                    .clientTrackingRange(8)
                    .build(ResourceKey.create(Registries.ENTITY_TYPE, PIRANHA_ID))
    );

    // Item keys
    private static final ResourceKey<Item> PIRANHA_SPAWN_EGG_KEY = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha_spawn_egg"));
    private static final ResourceKey<Item> RAW_PIRANHA_KEY       = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha_raw"));
    private static final ResourceKey<Item> COOKED_PIRANHA_KEY    = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha_cooked"));
    private static final ResourceKey<Item> PIRANHA_BUCKET_KEY    = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha_bucket"));

    // Items
    public static final Item PIRANHA_SPAWN_EGG = Registry.register(
            BuiltInRegistries.ITEM,
            PIRANHA_SPAWN_EGG_KEY,
            new SpawnEggItem(new Item.Properties().setId(PIRANHA_SPAWN_EGG_KEY).spawnEgg(PIRANHA))
    );

    public static final Item RAW_PIRANHA = Registry.register(
            BuiltInRegistries.ITEM,
            RAW_PIRANHA_KEY,
            new Item(new Item.Properties().setId(RAW_PIRANHA_KEY).food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1F)
                    .build()))
    );

    public static final Item COOKED_PIRANHA = Registry.register(
            BuiltInRegistries.ITEM,
            COOKED_PIRANHA_KEY,
            new Item(new Item.Properties().setId(COOKED_PIRANHA_KEY).food(new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationModifier(0.6F)
                    .build()))
    );

    public static final Item PIRANHA_BUCKET = Registry.register(
            BuiltInRegistries.ITEM,
            PIRANHA_BUCKET_KEY,
            new MobBucketItem(PIRANHA, Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().setId(PIRANHA_BUCKET_KEY).stacksTo(1))
    );

    @Override
    public void onInitialize() {
        // Load config
        FabricConfig.load();

        // Wire common bucket reference
        HungryWatersCommon.PIRANHA_BUCKET_ITEM = () -> PIRANHA_BUCKET;

        // Entity attributes
        FabricDefaultAttributeRegistry.register(PIRANHA, PiranhaEntity.createAttributes());

        // Spawn placements
        SpawnPlacements.register(PIRANHA, SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PiranhaEntity::checkSpawnRules);

        // Biome spawns — vanilla biomes
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTags.IS_RIVER), MobCategory.WATER_AMBIENT, PIRANHA, 5, 1, 5);
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(Biomes.SWAMP, Biomes.MANGROVE_SWAMP), MobCategory.WATER_AMBIENT, PIRANHA, 6, 1, 5);

        // Creative tab additions
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.addAfter(Items.PIGLIN_BRUTE_SPAWN_EGG, PIRANHA_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.addAfter(Items.SALMON_BUCKET, PIRANHA_BUCKET);
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.addAfter(Items.COOKED_SALMON, RAW_PIRANHA);
            entries.addAfter(RAW_PIRANHA, COOKED_PIRANHA);
        });
    }
}