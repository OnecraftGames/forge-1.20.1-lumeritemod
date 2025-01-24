package net.lumerite.lumeritemod.fluids;

import net.lumerite.lumeritemod.LumeriteMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundAction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.joml.Vector3f;

public class ModFluidsTypes {

    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("minecraft:blocks/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("minecraft:blocks/water_flow");
    public static final ResourceLocation URANIUM_OVERLAY_RL = new ResourceLocation("lumeritemod:misc/in_uranium_water");

    public static final DeferredRegister<FluidType> FLUIDS_TYPES =
            DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, LumeriteMod.MOD_ID);


    public static final RegistryObject<FluidType> URANIUM = register("uranium_fluid",
            FluidType.Properties.create()
                    .lightLevel(2)
                    .density(15)
                    .viscosity(5)
                    .sound(SoundAction.get("drink"), SoundEvents.HONEY_DRINK)
    );


    private static RegistryObject<FluidType> register(String name, FluidType.Properties properties) {
        return FLUIDS_TYPES.register(
                name,
                () -> new BaseFluidsTypes(
                        WATER_STILL_RL,
                        WATER_FLOWING_RL,
                        URANIUM_OVERLAY_RL,
                        0x3F76E4,
                        new Vector3f(
                                224f / 255f,
                                56f / 255f,
                                208f / 255f),
                        properties
                ));
    }

    public static void register(IEventBus e) {
        FLUIDS_TYPES.register(e);
    }
}
