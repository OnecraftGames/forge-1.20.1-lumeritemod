package net.lumerite.lumeritemod.block.blocks;

import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class DragoneStairs extends StairBlock {
    public DragoneStairs(Supplier<BlockState> state, Properties properties) {
        super(state, properties);
    }
}
