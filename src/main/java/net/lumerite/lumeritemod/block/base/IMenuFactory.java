package net.lumerite.lumeritemod.block.base;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;

@FunctionalInterface
public interface IMenuFactory {
    AbstractContainerMenu create(int containerId, Inventory playerInventory, BaseDragoneCrafterEntity entity, ContainerData data);
}
