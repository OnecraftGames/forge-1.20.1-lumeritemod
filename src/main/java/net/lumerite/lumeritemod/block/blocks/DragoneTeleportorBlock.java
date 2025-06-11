package net.lumerite.lumeritemod.block.blocks;

import net.lumerite.lumeritemod.block.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
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

public class DragoneTeleportorBlock extends DirectionalBlock {

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 16, 16);

    public DragoneTeleportorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        // Nombre de particules à générer (tu peux ajuster)
        for (int i = 0; i < 2; ++i) {
            double x = pos.getX() + 0.5 + (random.nextDouble() - 0.5);
            double y = pos.getY() + 1.0;
            double z = pos.getZ() + 0.5 + (random.nextDouble() - 0.5);

            double dx = (random.nextDouble() - 0.5) * 0.2;
            double dy = (random.nextDouble()) * 0.2;
            double dz = (random.nextDouble() - 0.5) * 0.2;

            level.addParticle(ParticleTypes.ENCHANT, x, y, z, dx, dy, dz);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {

        BlockPos above = pos.above();
        BlockState aboveState = level.getBlockState(above);

        if (aboveState.getBlock() instanceof DragoneTeleportorBlockUpper) {
            // Si le bloc du dessous est un DragoneTeleportorBlock, on le détruit

            Direction thisFacing = state.getValue(BlockStateProperties.FACING);
            Direction aboveFacing = aboveState.getValue(BlockStateProperties.FACING);

            if (thisFacing == aboveFacing) {
                if (player instanceof ServerPlayer serverPlayer) {
                    if (serverPlayer.gameMode.getGameModeForPlayer() == GameType.SURVIVAL ||
                            serverPlayer.gameMode.getGameModeForPlayer() == GameType.ADVENTURE) {

                        // Drop de l'item manuellement
                        ItemStack stack = new ItemStack(ModBlock.DRAGONE_TELEPORTOR_UPPER.get()); // ou ton item personnalisé
                        Block.popResource(level, pos, stack);
                    }
                }
            }


        }

        super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (!pState.is(pNewState.getBlock())) {
            BlockPos above = pPos.above();
            if (pLevel.getBlockState(above).getBlock() instanceof DragoneTeleportorBlockUpper) {

                Direction thisFacing = pState.getValue(BlockStateProperties.FACING);
                Direction aboveFacing = pLevel.getBlockState(above).getValue(BlockStateProperties.FACING);

                if (thisFacing == aboveFacing) {

                    pLevel.setBlock(above, Blocks.AIR.defaultBlockState(), 3);
                    ((ServerLevel) pLevel).sendParticles(
                            ParticleTypes.END_ROD,     // ou ton propre ParticleType
                            pPos.getX() + 0.5,
                            pPos.getY() + 1.0,
                            pPos.getZ() + 0.5,
                            20, // quantité
                            0.3, 0.3, 0.3, // écartement en X, Y, Z
                            0.05 // vitesse
                    );

                }


            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }
}
