package net.lumerite.lumeritemod.item;

import net.lumerite.lumeritemod.LumeriteMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS_BUILDER =
            DeferredRegister.create(ForgeRegistries.ITEMS, LumeriteMod.MOD_ID);

    public static final RegistryObject<Item> LUMERITE_INGOT = ITEMS_BUILDER.register("lumerite_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LUMERITE_NUGGET = ITEMS_BUILDER.register("lumerite_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LUMERITE_FRAG = ITEMS_BUILDER.register("lumerite_frag",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRAGONE_INGOT = ITEMS_BUILDER.register("dragone_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRAGONE_COMPRESSED = ITEMS_BUILDER.register("dragone_compressed",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRAGONE_HEART = ITEMS_BUILDER.register("dragone_heart",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus e) {
        ITEMS_BUILDER.register(e);
    }
}
