package net.lumerite.lumeritemod.item.entity;

import net.lumerite.lumeritemod.item.ModItems;
import net.lumerite.lumeritemod.item.base.BaseDynamiteEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public class BigDynamiteEntity extends BaseDynamiteEntity {
    public BigDynamiteEntity(EntityType<? extends BaseDynamiteEntity> type, Level level) {
        super(type, level, ModItems.BIG_DYNAMITE.get(), 5.0F, 5.0);
    }

    public BigDynamiteEntity(Level level, LivingEntity shooter) {
        super(ModItemsEntities.BIG_DYNAMITE_ENTITY.get(), level, shooter,
                ModItems.BIG_DYNAMITE.get(), 5.0F, 5.0);
    }
}
