package net.lumerite.lumeritemod.block.entity;

import net.lumerite.lumeritemod.block.base.BaseDragoneCrafterEntity;
import net.lumerite.lumeritemod.screen.dragonecrafter.DragoneCrafterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DragoneCrafterEntity extends BaseDragoneCrafterEntity {

    public DragoneCrafterEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DRAGONE_CRAFTER_BE.get(), pPos, pBlockState, 78, "block.lumeritemod.dragone_crafter", DragoneCrafterMenu::new);
    }

}
