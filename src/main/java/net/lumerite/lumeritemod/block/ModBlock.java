package net.lumerite.lumeritemod.block;

import net.lumerite.lumeritemod.LumeriteMod;
import net.lumerite.lumeritemod.block.custom.DragoneCrafter;
import net.lumerite.lumeritemod.block.custom.DragoneGlass;
import net.lumerite.lumeritemod.block.custom.ExtractorBlock;
import net.lumerite.lumeritemod.item.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlock {

    public static final DeferredRegister<Block> BLOCKS_BUILDER =
            DeferredRegister.create(Registries.BLOCK, LumeriteMod.MOD_ID);


    public static final RegistryObject<Block> DRAGONE_BLOCK = registerBlock("dragone_block",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

    public static final RegistryObject<Block> DRAGONE_ORE = registerBlock("dragone_ore",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_ORE).sound(SoundType.STONE)));

    public static final RegistryObject<Block> DRAGONE_SLAB = registerBlock("dragone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

    public static final RegistryObject<Block> DRAGONE_STAIRS = registerBlock("dragone_stairs",
            () -> new StairBlock(Blocks.STONE::defaultBlockState, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

    public static final RegistryObject<Block> DRAGONE_GLASS = registerBlock("dragone_glass",
            DragoneGlass::new);


    public static final RegistryObject<Block> EXTRACTOR_BLOCK = registerBlock("extractor_block",
            () -> new ExtractorBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

    public static final RegistryObject<Block> EXTRACTOR_CASING = registerBlock("extractor_block_casing",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));

    public static final RegistryObject<Block> EXTRACTOR_FRAME = registerBlock("extractor_block_frame",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));


    public static final RegistryObject<Block> DRAGONE_CRAFTER = registerBlock("dragone_crafter",
            () -> new DragoneCrafter(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(SoundType.METAL)));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS_BUILDER.register(name, block);
        registerBlockItem(name, toReturn);

        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return ModItems.ITEMS_BUILDER.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus e) {
        BLOCKS_BUILDER.register(e);
    }

}
