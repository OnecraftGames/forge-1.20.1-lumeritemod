package net.lumerite.lumeritemod.block.blocks;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class DragoneGlass extends Block {
    public DragoneGlass() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS)
                .sound(SoundType.GLASS)// Matériau du bloc
                .strength(50f, 20.0f)
                .noOcclusion()
                .isSuffocating((state, level, pos) -> false) // Ne bloque pas la vue
                .isViewBlocking((state, level, pos) -> false)
                .lightLevel(state -> 0));
    }



    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {

        if (Screen.hasShiftDown()) {
            pTooltip.add(Component.empty());
            pTooltip.add(Component.translatable("tooltip.dragone_glass"));
            pTooltip.add(Component.empty());
        } else {
            pTooltip.add(Component.translatable("tooltip.hold_shift"));
        }

    }

    @Override
    public boolean propagatesSkylightDown(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return true;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(0, 0, 0, 16, 16, 16); // Forme complète pour la boîte de sélection
    }

    @Override
    public int getLightBlock(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return 0; // Permet à la lumière de passer à travers
    }
}
