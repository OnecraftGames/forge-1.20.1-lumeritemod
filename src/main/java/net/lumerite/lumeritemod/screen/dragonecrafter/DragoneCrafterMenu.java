package net.lumerite.lumeritemod.screen.dragonecrafter;

import net.lumerite.lumeritemod.block.ModBlock;
import net.lumerite.lumeritemod.block.entity.DragoneCrafterEntity;
import net.lumerite.lumeritemod.screen.ModMenuTypes;
import net.lumerite.lumeritemod.screen.base.BaseDragoneCrafterMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;

public class DragoneCrafterMenu extends BaseDragoneCrafterMenu<DragoneCrafterEntity> {

    public DragoneCrafterMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(6));
    }

    public DragoneCrafterMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
        super(pContainerId, inv, entity, data, ModBlock.DRAGONE_CRAFTER.get(), ModMenuTypes.DragoneCrafterMenu.get());

    }

}
