package net.lumerite.lumeritemod.block.blocks;

import net.lumerite.lumeritemod.block.ModBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.lumerite.lumeritemod.block.entity.ExtractorBlockEntity;
import net.lumerite.lumeritemod.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class ExtractorBlock extends BaseEntityBlock {

    public ExtractorBlock(Properties pProperties) {
        super(pProperties);
    }

    @Deprecated
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Deprecated
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.getBlock() != pNewState.getBlock()) {
            BlockEntity blockEntity = pLevel.getBlockEntity(pPos);
            if (blockEntity instanceof ExtractorBlockEntity) {
                ((ExtractorBlockEntity) blockEntity).drops();
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
    }

    /// ///////////////////////////// ///

    @Deprecated
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {


        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);


                if(entity instanceof ExtractorBlockEntity) {

                    ExtractorBlock grinder = (ExtractorBlock) pState.getBlock();

                    Direction clickedFace = pHit.getDirection();
                    boolean structureComplete = isStructureComplete(pLevel, pPos, clickedFace);

                    if (structureComplete) {
                        // Ouvrir l'écran de la machine
                        NetworkHooks.openScreen((ServerPlayer) pPlayer, (ExtractorBlockEntity) entity, pPos);
                    }
                } else {
                    throw new IllegalStateException("Our Container provider is missing!");

                }


        }

        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }

    public boolean isStructureComplete(Level world, BlockPos pos, Direction clickFace) {
        BlockPos centerPos = calculateCenter(pos, clickFace);

        // Couche 1 (en dessous) - Encadre la machine avec des Grinder Frame
        if (!checkLayer(world, centerPos.below(), rotateMatrix(new Block[][]{
                {ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_FRAME.get()},
                {ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_CASING.get(), ModBlock.EXTRACTOR_FRAME.get()},
                {ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_FRAME.get()},
        }, clickFace))) return false;

        // Couche 2 (niveau de la machine) - Casing et bloc central avec source de lave
        if (!checkLayer(world, centerPos, rotateMatrix(new Block[][]{
                {ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_CASING.get(), ModBlock.EXTRACTOR_FRAME.get()},
                {ModBlock.EXTRACTOR_CASING.get(), Blocks.WATER, ModBlock.EXTRACTOR_BLOCK.get()}, // Source de lave
                {ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_CASING.get(), ModBlock.EXTRACTOR_FRAME.get()}
        }, clickFace))) return false;

        // Couche 3 (au-dessus) - Encadre la machine avec des Grinder Frame
        if (!checkLayer(world, centerPos.above(), rotateMatrix(new Block[][]{
                {ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_FRAME.get()},
                {ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_CASING.get(), ModBlock.EXTRACTOR_FRAME.get()},
                {ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_FRAME.get(), ModBlock.EXTRACTOR_FRAME.get()},
        }, clickFace))) return false;

        return true;
    }

    private Block[][] rotateMatrix(Block[][] matrix, Direction clickFace) {
        // Appliquer une rotation ou un miroir selon la face cliquée
        switch (clickFace) {
            case NORTH:
                return rotate180(matrix); // Aucune rotation nécessaire
            case SOUTH:
                return matrix; // Rotation 180°
            case EAST:
                return rotate90(matrix); // Rotation 90° dans le sens horaire
            case WEST:
                return rotate270(matrix); // Rotation 270° (sens horaire)
            case UP:
            case DOWN:
                return matrix; // Pas de rotation pour le haut ou le bas
            default:
                return matrix;
        }
    }

    private Block[][] rotate90(Block[][] matrix) {
        int size = matrix.length;
        Block[][] rotated = new Block[size][size];
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                rotated[y][size - x - 1] = matrix[x][y];
            }
        }
        return rotated;
    }

    private Block[][] rotate180(Block[][] matrix) {
        return rotate90(rotate90(matrix)); // Effectue une rotation de 180° en appliquant deux rotations de 90°
    }


    private Block[][] rotate270(Block[][] matrix) {
        return rotate90(rotate90(rotate90(matrix))); // Effectue une rotation de 270° en appliquant trois rotations de 90°
    }


    private boolean checkLayer(Level world, BlockPos layerCenterPos, Block[][] layer) {
        int radius = layer.length / 2; // Rayon pour centrer la grille autour du centre logique

        // Parcours de la matrice de la couche (layer)
        for (int x = 0; x < layer.length; x++) {
            for (int z = 0; z < layer[x].length; z++) {
                Block expectedBlock = layer[x][z]; // Le bloc attendu à cette position
                if (expectedBlock != null) { // Si le bloc attendu n'est pas nul (ne vérifie pas les cases vides)
                    // Calcul de la position cible dans le monde en fonction de la couche
                    BlockPos targetPos = layerCenterPos.offset(x - radius, 0, z - radius);

                    // Récupération du bloc réel à cette position
                    Block actualBlock = world.getBlockState(targetPos).getBlock();

                    // Affichage pour le débogage (peut être commenté ou supprimé)
                    System.out.println("Checking block at " + targetPos + ": Expected " + expectedBlock + ", Found " + actualBlock);
                    System.out.println("x" + x + " z" + z + " radius" + radius);

                    // Si le bloc réel ne correspond pas au bloc attendu, retour false
                    if (actualBlock != expectedBlock) {
                        return false; // La couche n'est pas valide
                    }
                }
            }
        }

        // Si tous les blocs dans la couche correspondent, la couche est valide
        return true;
    }

    private BlockPos calculateCenter(BlockPos clickPos, Direction clickFace) {
        // Calculer le décalage du centre logique par rapport à la face cliquée
        switch (clickFace) {
            case NORTH:
                return clickPos.offset(0, 0, 1); // Centre au sud
            case SOUTH:
                return clickPos.offset(0, 0, -1); // Centre au nord
            case EAST:
                return clickPos.offset(-1, 0, 0); // Centre à l'ouest
            case WEST:
                return clickPos.offset(1, 0, 0); // Centre à l'est
            case UP:
                return clickPos.below(); // Centre en dessous
            case DOWN:
                return clickPos.above(); // Centre au-dessus
            default:
                return clickPos; // Par défaut, pas de décalage
        }
    }



    /// ///////////////////////////// ///

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ExtractorBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.EXTRCTOR_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
