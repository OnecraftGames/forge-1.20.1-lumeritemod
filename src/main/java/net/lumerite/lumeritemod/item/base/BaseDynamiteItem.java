package net.lumerite.lumeritemod.item.base;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.function.BiFunction;

public class BaseDynamiteItem<T extends ThrowableItemProjectile> extends Item {

    private final BiFunction<Level, LivingEntity, T> entityFactory;

    public BaseDynamiteItem(Properties properties, BiFunction<Level, LivingEntity, T> entityFactory) {
        super(properties);
        this.entityFactory = entityFactory;

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // Crée un projectile de dynamite

            T dynamite = entityFactory.apply(level, player);


            dynamite.setItem(itemstack);
            dynamite.shootFromRotation(player, player.getXRot(), player.getYRot(), 4F, 0.4F, 0.0F);
            dynamite.setDeltaMovement(dynamite.getDeltaMovement().add(0.0D, 0.1D, 0.0D));
            level.addFreshEntity(dynamite);

            // Jouer un son
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.TNT_PRIMED, SoundSource.PLAYERS, 1.0F, 1.0F);

            player.awardStat(Stats.ITEM_USED.get(this));

            // Réduire la stack
            if (!player.isCreative()) {
                itemstack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
