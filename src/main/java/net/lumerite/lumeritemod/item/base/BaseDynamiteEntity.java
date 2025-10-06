package net.lumerite.lumeritemod.item.base;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class BaseDynamiteEntity extends ThrowableItemProjectile {

    private final float explosionPower;
    private final double explosionRadius;
    private final Item itemRef;

    public BaseDynamiteEntity(EntityType<? extends BaseDynamiteEntity> type, Level level,
                              Item itemRef, float explosionPower, double explosionRadius) {
        super(type, level);
        this.explosionPower = explosionPower;
        this.explosionRadius = explosionRadius;
        this.itemRef = itemRef;
    }

    public BaseDynamiteEntity(EntityType<? extends BaseDynamiteEntity> type, Level level, LivingEntity shooter,
                              Item itemRef, float explosionPower, double explosionRadius) {
        super(type, shooter, level);
        this.explosionPower = explosionPower;
        this.explosionRadius = explosionRadius;
        this.itemRef = itemRef;
    }

    @Override
    protected Item getDefaultItem() {
        return itemRef;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        this.discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        Level level = this.level();
        if (!level.isClientSide) {
            level.explode(this, this.getX(), this.getY(), this.getZ(), explosionPower, Level.ExplosionInteraction.BLOCK);
            damageEntitiesInRadius(level, this.getX(), this.getY(), this.getZ(), explosionRadius);
            this.discard();
        }
    }

    private void damageEntitiesInRadius(Level level, double x, double y, double z, double radius) {
        List<Entity> entities = level.getEntities(this, this.getBoundingBox().inflate(radius),
                entity -> entity instanceof LivingEntity);

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                livingEntity.hurt(level.damageSources().explosion(this, this.getOwner()), 10.0F);
            }
        }
    }
}

