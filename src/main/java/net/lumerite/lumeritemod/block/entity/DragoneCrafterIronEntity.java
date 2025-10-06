package net.lumerite.lumeritemod.block.entity;

import net.lumerite.lumeritemod.block.base.BaseDragoneCrafterEntity;
import net.lumerite.lumeritemod.screen.dragonecrafter.DragoneCrafterIronMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class DragoneCrafterIronEntity extends BaseDragoneCrafterEntity {

    public DragoneCrafterIronEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DRAGONE_CRAFTER_IRON_BE.get(), pPos, pBlockState, 40, "block.lumeritemod.dragone_crafter_iron", DragoneCrafterIronMenu::new);
    }

}


