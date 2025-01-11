package net.lumerite.lumeritemod.item.entity;


import net.lumerite.lumeritemod.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.entity.projectile.ThrowableProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.network.NetworkHooks;

import java.util.List;

public class DynamiteEntity extends ThrowableItemProjectile {

    public DynamiteEntity(EntityType<? extends DynamiteEntity> entityType, Level level) {
        super(entityType, level);
    }


    public DynamiteEntity(Level level) {
        super(ModItemsEntities.DYNAMITE_ENTITY.get(), level); // Remplace par ton type d'entité
    }

    public DynamiteEntity(Level level, double x, double y, double z) {
        super(ModItemsEntities.DYNAMITE_ENTITY.get(), level);
        this.setPos(x, y, z);
    }

    public DynamiteEntity(Level level, LivingEntity shooter) {
        super(ModItemsEntities.DYNAMITE_ENTITY.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.DYNAMITE.get(); // Remplace par ton item
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        // Implémente ce que le projectile fait lorsqu'il touche une entité

        this.discard(); // Détruit le projectile après l'impact
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        // Implémente ce que le projectile fait lorsqu'il touche un bloc
        Level level = this.level();
        if (!level.isClientSide) {
            level.explode(this, this.getX(), this.getY(), this.getZ(), 3.0F, Level.ExplosionInteraction.BLOCK);
            damageEntitiesInRadius(level, this.getX(), this.getY(), this.getZ(), 3.0);

            this.discard();
        }
    }

    private void damageEntitiesInRadius(Level level, double x, double y, double z, double radius) {
        // Récupérer toutes les entités dans un rayon donné
        List<Entity> entities = level.getEntities(this, this.getBoundingBox().inflate(radius), entity -> entity instanceof LivingEntity);

        for (Entity entity : entities) {
            if (entity instanceof LivingEntity livingEntity) {
                // Appliquer des dégâts au lanceur et aux autres entités
                float damageAmount = 10.0F; // Quantité de dégâts
                livingEntity.hurt(level.damageSources().explosion(this, this.getOwner()), damageAmount);
            }
        }
    }
}


