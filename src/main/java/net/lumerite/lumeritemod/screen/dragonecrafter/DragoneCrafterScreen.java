package net.lumerite.lumeritemod.screen.dragonecrafter;

import net.lumerite.lumeritemod.screen.base.BaseDragoneCrafterScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DragoneCrafterScreen extends BaseDragoneCrafterScreen<DragoneCrafterMenu> {

    public DragoneCrafterScreen(DragoneCrafterMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

}
