package net.lumerite.lumeritemod.item.entity;

import net.lumerite.lumeritemod.LumeriteMod;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItemsEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, LumeriteMod.MOD_ID);

    public static final RegistryObject<EntityType<DynamiteEntity>> DYNAMITE_ENTITY = ENTITY_TYPES.register("dynamite",
            () -> EntityType.Builder.<DynamiteEntity>of(DynamiteEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("dynamite"));

    public static final RegistryObject<EntityType<BigDynamiteEntity>> BIG_DYNAMITE_ENTITY = ENTITY_TYPES.register("big_dynamite",
            () -> EntityType.Builder.<BigDynamiteEntity>of(BigDynamiteEntity::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build("big_dynamite"));

    public static void register(IEventBus e) {
        ENTITY_TYPES.register(e);
    }

}
