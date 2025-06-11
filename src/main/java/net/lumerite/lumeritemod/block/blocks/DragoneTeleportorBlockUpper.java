package net.lumerite.lumeritemod.block.blocks;

import net.lumerite.lumeritemod.block.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class DragoneTeleportorBlockUpper extends DirectionalBlock {

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public DragoneTeleportorBlockUpper(Properties properties) {
        super(properties); // comportement similaire à l'air);
    }


    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(DragoneTeleportorBlock.FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(DragoneTeleportorBlock.FACING);
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {

        BlockPos below = pos.below();
        BlockState belowState = level.getBlockState(below);

        if (belowState.getBlock() instanceof DragoneTeleportorBlock) {
            // Si le bloc du dessous est un DragoneTeleportorBlock, on le détruit

            Direction thisFacing = state.getValue(BlockStateProperties.FACING);
            Direction belowFacing = belowState.getValue(BlockStateProperties.FACING);

            if (thisFacing == belowFacing) {
                if (player instanceof ServerPlayer serverPlayer) {

                    if (serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL ||
                            serverPlayer.gameMode.getGameModeForPlayer() == GameType.ADVENTURE) {

                        // Drop de l'item manuellement
                        ItemStack stack = new ItemStack(ModBlock.DRAGONE_TELEPORTOR.get()); // ou ton item personnalisé
                        Block.popResource(level, pos, stack);
                    }
                }
            }

        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockPos below = pos.below();
            BlockState belowState = level.getBlockState(below);

            if (belowState.getBlock() instanceof DragoneTeleportorBlock) {

                Direction thisFacing = state.getValue(BlockStateProperties.FACING);
                Direction belowFacing = belowState.getValue(BlockStateProperties.FACING);

                if (thisFacing == belowFacing) {
                    level.setBlock(below, Blocks.AIR.defaultBlockState(), 3); // true pour drop, false sinon
                    ((ServerLevel) level).sendParticles(
                            ParticleTypes.END_ROD,     // ou ton propre ParticleType
                            below.getX() + 0.5,
                            below.getY() + 1.0,
                            below.getZ() + 0.5,
                            20, // quantité
                            0.3, 0.3, 0.3, // écartement en X, Y, Z
                            0.05 // vitesse
                    );
                }



            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

}
