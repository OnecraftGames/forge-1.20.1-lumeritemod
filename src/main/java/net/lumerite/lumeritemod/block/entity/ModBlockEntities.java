package net.lumerite.lumeritemod.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.lumerite.lumeritemod.LumeriteMod;
import net.lumerite.lumeritemod.block.ModBlock;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, LumeriteMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<ExtractorBlockEntity>> EXTRCTOR_BE =
            BLOCK_ENTITIES.register("extractor_be", () ->
                    BlockEntityType.Builder.of(ExtractorBlockEntity::new,
                            ModBlock.EXTRACTOR_BLOCK.get()).build(null));

    public static final RegistryObject<BlockEntityType<DragoneCrafterEntity>> DRAGONE_CRAFTER_BE =
            BLOCK_ENTITIES.register("dragone_crafter_be", () ->
                    BlockEntityType.Builder.of(DragoneCrafterEntity::new,
                            ModBlock.DRAGONE_CRAFTER.get()).build(null));

    public static final RegistryObject<BlockEntityType<DragoneCrafterIronEntity>> DRAGONE_CRAFTER_IRON_BE =
            BLOCK_ENTITIES.register("dragone_crafter_iron_be", () ->
                    BlockEntityType.Builder.of(DragoneCrafterIronEntity::new,
                            ModBlock.DRAGONE_CRAFTER_IRON.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}