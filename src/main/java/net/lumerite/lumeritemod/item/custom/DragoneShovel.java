package net.lumerite.lumeritemod.item.custom;

import net.lumerite.lumeritemod.item.tiers.DragonePickaxeTiers;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Item;

public class DragoneShovel extends ShovelItem {
    public DragoneShovel() {
        super(new DragonePickaxeTiers(), 1F, -3.0F, new Item.Properties());
    }
}
