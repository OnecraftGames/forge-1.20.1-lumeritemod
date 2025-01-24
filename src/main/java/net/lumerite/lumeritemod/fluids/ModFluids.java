package net.lumerite.lumeritemod.fluids;

import net.lumerite.lumeritemod.LumeriteMod;
import net.lumerite.lumeritemod.block.ModBlock;
import net.lumerite.lumeritemod.item.ModItems;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.io.Reader;

public class ModFluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, LumeriteMod.MOD_ID);


    public static final RegistryObject<FlowingFluid> SOURCE_URANIUM_WATER = FLUIDS.register("source_uranium_water",
            () -> new ForgeFlowingFluid.Source(ModFluids.URANIUM_WATER_PROPERTIES));

    public static final RegistryObject<FlowingFluid> FLOWING_URANIUM_WATER = FLUIDS.register("flowing_uranium_water",
            () -> new ForgeFlowingFluid.Flowing(ModFluids.URANIUM_WATER_PROPERTIES));

    public static final ForgeFlowingFluid.Properties URANIUM_WATER_PROPERTIES = new ForgeFlowingFluid.Properties(
            ModFluidsTypes.URANIUM, SOURCE_URANIUM_WATER, FLOWING_URANIUM_WATER
    )
            .slopeFindDistance(2)
            .levelDecreasePerBlock(2)
            .bucket(ModItems.URANIUM_BUCKET)
            .block(ModBlock.URANIUM_WATER);

    public static void register(IEventBus e) {
        FLUIDS.register(e);
    }

}

