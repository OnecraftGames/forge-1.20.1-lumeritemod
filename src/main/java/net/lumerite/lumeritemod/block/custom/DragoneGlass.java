package net.lumerite.lumeritemod.block.custom;

import com.mojang.blaze3d.systems.RenderSystem;
import net.lumerite.lumeritemod.block.ModBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GlassBlock;
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
        return 0;
    }

    @Override
    public boolean skipRendering(BlockState state, BlockState adjacentBlockState, Direction side) {

        if (adjacentBlockState.is(this)) {
            return true; // Ne pas afficher les faces adjacentes du même type (Cave Block)
        }

        return false; // Sinon, afficher normalement
    }
}
