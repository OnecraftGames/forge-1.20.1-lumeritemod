package net.lumerite.lumeritemod.item.items;

import net.lumerite.lumeritemod.item.tiers.DragoneToolTiers;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Item;

public class DragoneShovel extends ShovelItem {

    public DragoneShovel() {
        super(DragoneToolTiers.DRAGONE, 1.5F, -3.0F, new Item.Properties());
    }
}
