package com.acorsicanfrog.hungrywaters;

import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MobBucketItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(HungryWatersCommon.MODID)
public class HungryWatersNeoForge {
        private static final ResourceLocation PIRANHA_ID = ResourceLocation.fromNamespaceAndPath(HungryWatersCommon.MODID, "piranha");
        private static final ResourceKey<EntityType<?>> PIRANHA_KEY = ResourceKey.create(Registries.ENTITY_TYPE, PIRANHA_ID);

    // Entity Types
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, HungryWatersCommon.MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<PiranhaEntity>> PIRANHA = ENTITY_TYPES.register("piranha",
            () -> EntityType.Builder.of(PiranhaEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5F, 0.3F)
                    .clientTrackingRange(8)
                                        .build(PIRANHA_KEY));

    // Items
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(HungryWatersCommon.MODID);

    public static final DeferredItem<SpawnEggItem> PIRANHA_SPAWN_EGG = ITEMS.registerItem("piranha_spawn_egg",
            properties -> new SpawnEggItem(PIRANHA.get(), properties));

    public static final DeferredItem<Item> RAW_PIRANHA = ITEMS.registerItem("piranha_raw",
            properties -> new Item(properties.food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1F)
                    .build())));

    public static final DeferredItem<Item> COOKED_PIRANHA = ITEMS.registerItem("piranha_cooked",
            properties -> new Item(properties.food(new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationModifier(0.6F)
                    .build())));

    public static final DeferredItem<MobBucketItem> PIRANHA_BUCKET = ITEMS.registerItem("piranha_bucket",
            properties -> new MobBucketItem(PIRANHA.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, properties.stacksTo(1)));

    public HungryWatersNeoForge(IEventBus modEventBus, ModContainer modContainer) {
        ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, NeoForgeConfig.SPEC);

        // Wire common bucket reference
        HungryWatersCommon.PIRANHA_BUCKET_ITEM = PIRANHA_BUCKET::get;

        // Listen for config load/reload to sync values to common config holder
        modEventBus.addListener((ModConfigEvent event) -> NeoForgeConfig.syncToCommon());
    }

    @EventBusSubscriber(modid = HungryWatersCommon.MODID)
    public static class ModEvents {
        @SubscribeEvent
        public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(PIRANHA.get(), PiranhaEntity.createAttributes().build());
        }

        @SubscribeEvent
        public static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
            event.register(
                    PIRANHA.get(),
                    SpawnPlacementTypes.IN_WATER,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    PiranhaEntity::checkSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.AND
            );
        }

        @SubscribeEvent
        public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
            if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
                event.insertAfter(
                        new ItemStack(Items.PIGLIN_BRUTE_SPAWN_EGG),
                        new ItemStack(PIRANHA_SPAWN_EGG.get()),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
            }
            if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
                event.insertAfter(
                        new ItemStack(Items.SALMON_BUCKET),
                        new ItemStack(PIRANHA_BUCKET.get()),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
            }
            if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
                event.insertAfter(
                        new ItemStack(Items.COOKED_SALMON),
                        new ItemStack(RAW_PIRANHA.get()),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
                event.insertAfter(
                        new ItemStack(RAW_PIRANHA.get()),
                        new ItemStack(COOKED_PIRANHA.get()),
                        CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
                );
            }
        }
    }
}