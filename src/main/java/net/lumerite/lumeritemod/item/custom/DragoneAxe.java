package net.lumerite.lumeritemod.item.custom;

import net.lumerite.lumeritemod.item.tiers.DragoneAxeTiers;
import net.lumerite.lumeritemod.item.tiers.DragonePickaxeTiers;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;

public class DragoneAxe extends AxeItem {
    public DragoneAxe() {
        super(new DragoneAxeTiers(), 1, -2.8F, new Item.Properties());
    }
}
