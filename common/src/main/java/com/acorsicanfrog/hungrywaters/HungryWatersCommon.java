package com.acorsicanfrog.hungrywaters;

import java.util.function.Supplier;
import net.minecraft.world.item.Item;

public final class HungryWatersCommon {
    public static final String MODID = "hungrywaters";

    public static Supplier<Item> PIRANHA_BUCKET_ITEM = () -> { throw new IllegalStateException("Piranha bucket not registered yet"); };

    private HungryWatersCommon() {}
}
