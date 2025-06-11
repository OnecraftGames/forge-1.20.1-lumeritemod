package net.lumerite.lumeritemod.item.items;

import net.lumerite.lumeritemod.item.tiers.DragoneToolTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class DragoneSword extends SwordItem {
    public DragoneSword() {
        super(DragoneToolTiers.DRAGONE, 3, -2.4F, new Item.Properties());
    }
}

