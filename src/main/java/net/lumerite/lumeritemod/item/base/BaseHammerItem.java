package net.lumerite.lumeritemod.item.base;

import net.lumerite.lumeritemod.block.ModBlock;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class BaseHammerItem extends PickaxeItem {

    String tooltip;
    private Direction currentDirection;

    public BaseHammerItem(Tier tier, String Tooltip) {
        super(tier, 1, -2.8F, new Item.Properties());

        this.tooltip = Tooltip;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.empty());
            pTooltipComponents.add(Component.translatable(this.tooltip));
            pTooltipComponents.add(Component.empty());
        } else {
            pTooltipComponents.add(Component.translatable("tooltip.hold_shift"));
        }


    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!world.isClientSide()) {
            // Casser les blocs autour
            breakAdjacentBlocks(world, pos, entity);
        }
        return super.mineBlock(stack, world, state, pos, entity);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        Level level = player.level();

        // Seulement côté client
        if (level.isClientSide) {
            if (player.pick(20.0D, 0.0F, false) instanceof BlockHitResult hitResult) {
                this.currentDirection = hitResult.getDirection();
            }
        }

        return super.onBlockStartBreak(itemstack, pos, player);
    }

    private void breakAdjacentBlocks(Level world, BlockPos pos, LivingEntity entity) {

        BlockState minedBlock = world.getBlockState(pos.offset(0, 0, 0));

        boolean isOre = isOreBlock(minedBlock);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                for (int dz = -1; dz <= 1; dz++) {
                    // Ne pas casser le bloc central
                    if (dx == 0 && dy == 0 && dz == 0) continue;

                    BlockPos adjacentPos = pos.offset(dx, dx, dz);

                    switch (this.currentDirection) {
                        case DOWN, UP -> adjacentPos = pos.offset(dx, 0, dz);
                        case EAST, WEST -> adjacentPos = pos.offset(0, dy, dz);
                        case NORTH, SOUTH -> adjacentPos = pos.offset(dx, dy, 0);
                    }

                    BlockState adjacentState = world.getBlockState(adjacentPos);

                    boolean isOres = isOreBlock(adjacentState);

                    // Vérifiez si le bloc peut être miné avec l'outil
                    if (!isOres) {
                        world.destroyBlock(adjacentPos, true); // true = drope les items
                    } else if (minedBlock.getBlock() == adjacentState.getBlock() && isOre) {
                        world.destroyBlock(adjacentPos, true); // true = drope les items
                    }
                }
            }
        }
    }

    private boolean isOreBlock(BlockState state) {
        return state.is(BlockTags.COAL_ORES) ||
                state.is(BlockTags.IRON_ORES) ||
                state.is(BlockTags.COPPER_ORES) ||
                state.is(BlockTags.GOLD_ORES) ||
                state.is(BlockTags.DIAMOND_ORES) ||
                state.is(BlockTags.EMERALD_ORES) ||
                state.is(BlockTags.LAPIS_ORES) ||
                state.is(BlockTags.REDSTONE_ORES) ||
                state.is(ModBlock.DRAGONE_ORE.get()) ||
                state.is(ModBlock.URANIUM_BLOCK.get()) ||
                state.is(ModBlock.URANIUM_ORE.get());
    }

}
