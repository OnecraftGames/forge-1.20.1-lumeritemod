package net.lumerite.lumeritemod.core.handlers;

import net.lumerite.lumeritemod.LumeriteMod;
import net.lumerite.lumeritemod.core.base.item.FuelItem;
import net.lumerite.lumeritemod.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Mod.EventBusSubscriber(modid = LumeriteMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModFuelHandler {


    @SubscribeEvent
    public static void onFuelBurnTime(FurnaceFuelBurnTimeEvent event) {
        if (event.getItemStack().getItem() instanceof FuelItem fuel) {
            event.setBurnTime(fuel.getBurnTime());
        }
    }
}
