package net.lumerite.lumeritemod.item.items;

import net.lumerite.lumeritemod.item.tiers.DragoneToolTiers;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Item;

public class DragonePickaxe extends PickaxeItem {

    public DragonePickaxe() {
        super(DragoneToolTiers.DRAGONE, 1, -2.8F, new Item.Properties());
    }
}
