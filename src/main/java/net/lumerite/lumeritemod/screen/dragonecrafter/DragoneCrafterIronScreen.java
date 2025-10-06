package net.lumerite.lumeritemod.screen.dragonecrafter;

import net.lumerite.lumeritemod.screen.base.BaseDragoneCrafterScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class DragoneCrafterIronScreen extends BaseDragoneCrafterScreen<DragoneCrafterIronMenu> {

    public DragoneCrafterIronScreen(DragoneCrafterIronMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

}
