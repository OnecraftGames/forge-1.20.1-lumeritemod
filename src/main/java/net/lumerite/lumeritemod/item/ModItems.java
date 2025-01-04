package net.lumerite.lumeritemod.item;

import net.lumerite.lumeritemod.LumeriteMod;
import net.lumerite.lumeritemod.item.armor.DragoneArmorItem;
import net.lumerite.lumeritemod.item.armor.DragoneArmorMaterial;
import net.lumerite.lumeritemod.item.custom.*;
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

    public static final RegistryObject<Item> DRAGONE_PICKAXE = ITEMS_BUILDER.register("dragone_pickaxe",
            DragonePickaxe::new);

    public static final RegistryObject<Item> DRAGONE_AXE = ITEMS_BUILDER.register("dragone_axe",
            DragoneAxe::new);

    public static final RegistryObject<Item> DRAGONE_SHOVEL = ITEMS_BUILDER.register("dragone_shovel",
            DragoneShovel::new);

    public static final RegistryObject<Item> DRAGONE_HAMMER = ITEMS_BUILDER.register("dragone_hammer",
            DragoneHammer::new);

    public static final RegistryObject<Item> DRAGONE_SWORD = ITEMS_BUILDER.register("dragone_sword",
            DragoneSword::new);

    public static final RegistryObject<Item> DRAGONE_HELMET = ITEMS_BUILDER.register("dragone_helmet",
            () -> new DragoneArmorItem(new DragoneArmorMaterial(), DragoneArmorItem.Type.HELMET, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONE_CHESTPLATE = ITEMS_BUILDER.register("dragone_chestplate",
            () -> new DragoneArmorItem(new DragoneArmorMaterial(), DragoneArmorItem.Type.CHESTPLATE, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONE_LEGGINGS = ITEMS_BUILDER.register("dragone_leggings",
            () -> new DragoneArmorItem(new DragoneArmorMaterial(), DragoneArmorItem.Type.LEGGINGS, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONE_BOOTS = ITEMS_BUILDER.register("dragone_boots",
            () -> new DragoneArmorItem(new DragoneArmorMaterial(), DragoneArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> DRAGONE_STICK = ITEMS_BUILDER.register("dragone_stick",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus e) {
        ITEMS_BUILDER.register(e);
    }
}
