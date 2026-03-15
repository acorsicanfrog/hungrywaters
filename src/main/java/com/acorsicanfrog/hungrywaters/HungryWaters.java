package com.acorsicanfrog.hungrywaters;

import org.slf4j.Logger;

import com.acorsicanfrog.hungrywaters.entity.PiranhaEntity;
import com.mojang.logging.LogUtils;

import net.minecraft.core.registries.Registries;
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
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(HungryWaters.MODID)
public class HungryWaters {
    public static final String MODID = "hungrywaters";
    public static final Logger LOGGER = LogUtils.getLogger();

    // Entity Types
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(Registries.ENTITY_TYPE, MODID);

    public static final DeferredHolder<EntityType<?>, EntityType<PiranhaEntity>> PIRANHA = ENTITY_TYPES.register("piranha",
            () -> EntityType.Builder.of(PiranhaEntity::new, MobCategory.WATER_AMBIENT)
                    .sized(0.5F, 0.3F)
                    .clientTrackingRange(8)
                    .build("piranha"));

    // Items
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredHolder<Item, DeferredSpawnEggItem> PIRANHA_SPAWN_EGG = ITEMS.register("piranha_spawn_egg",
            () -> new DeferredSpawnEggItem(PIRANHA, 0, 0, new Item.Properties()));

    public static final DeferredHolder<Item, Item> RAW_PIRANHA = ITEMS.register("piranha_raw",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(2)
                    .saturationModifier(0.1F)
                    .build())));

    public static final DeferredHolder<Item, Item> COOKED_PIRANHA = ITEMS.register("piranha_cooked",
            () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
                    .nutrition(5)
                    .saturationModifier(0.6F)
                    .build())));

    public static final DeferredHolder<Item, MobBucketItem> PIRANHA_BUCKET = ITEMS.register("piranha_bucket",
            () -> new MobBucketItem(PIRANHA.get(), Fluids.WATER, SoundEvents.BUCKET_EMPTY_FISH, new Item.Properties().stacksTo(1)));

    public HungryWaters(IEventBus modEventBus, ModContainer modContainer) {
        ENTITY_TYPES.register(modEventBus);
        ITEMS.register(modEventBus);

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("Hungry Waters loaded!");
    }

    @EventBusSubscriber(modid = MODID)
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