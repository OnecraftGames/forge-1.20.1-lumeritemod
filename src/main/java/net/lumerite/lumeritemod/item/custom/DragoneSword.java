package net.lumerite.lumeritemod.item.custom;

import net.lumerite.lumeritemod.item.tiers.DragonePickaxeTiers;
import net.lumerite.lumeritemod.item.tiers.DragoneSwordTiers;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;

public class DragoneSword extends SwordItem {
    public DragoneSword() {
        super(new DragoneSwordTiers(), 1, -3.0F, new Item.Properties());
    }
}
