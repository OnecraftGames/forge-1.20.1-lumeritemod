package net.lumerite.lumeritemod.item.entity;

import net.lumerite.lumeritemod.item.ModItems;
import net.lumerite.lumeritemod.item.base.BaseDynamiteEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class DynamiteEntity extends BaseDynamiteEntity {
    public DynamiteEntity(EntityType<? extends BaseDynamiteEntity> type, Level level) {
        super(type, level, ModItems.DYNAMITE.get(), 3.0F, 3.0);
    }

    public DynamiteEntity(Level level, LivingEntity shooter) {
        super(ModItemsEntities.DYNAMITE_ENTITY.get(), level, shooter,
                ModItems.DYNAMITE.get(), 3.0F, 3.0);
    }
}

