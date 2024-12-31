package net.lumerite.lumeritemod.item.custom;

import net.lumerite.lumeritemod.item.tiers.DragoneTiers;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DragonePickaxe extends PickaxeItem {

    public DragonePickaxe() {
        super(new DragoneTiers(), 1, -2.8F, new Item.Properties());
    }
}
