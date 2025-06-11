package net.lumerite.lumeritemod.item.items;

import net.lumerite.lumeritemod.item.entity.DynamiteEntity;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Dynamite extends Item {
    public Dynamite(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (!level.isClientSide) {
            // Crée un projectile de dynamite
            DynamiteEntity dynamite = new DynamiteEntity(level, player);
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

