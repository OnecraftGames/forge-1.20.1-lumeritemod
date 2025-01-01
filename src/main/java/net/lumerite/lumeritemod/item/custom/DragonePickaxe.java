package net.lumerite.lumeritemod.item.custom;

import net.lumerite.lumeritemod.item.tiers.DragonePickaxeTiers;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Item;

public class DragonePickaxe extends PickaxeItem {

    public DragonePickaxe() {
        super(new DragonePickaxeTiers(), 1, -2.8F, new Item.Properties());
    }
}
