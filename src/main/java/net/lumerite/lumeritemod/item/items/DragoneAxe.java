package net.lumerite.lumeritemod.item.items;

import net.lumerite.lumeritemod.item.tiers.DragoneToolTiers;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

public class DragoneAxe extends AxeItem {

    public DragoneAxe() {
        super(DragoneToolTiers.DRAGONE, 7.0F, -3.1F, new Item.Properties());
    }
}
