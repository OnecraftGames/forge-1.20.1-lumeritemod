package net.lumerite.lumeritemod;

import com.mojang.logging.LogUtils;
import net.lumerite.lumeritemod.block.ModBlock;
import net.lumerite.lumeritemod.block.entity.ModBlockEntities;
import net.lumerite.lumeritemod.events.ClientSetup;
import net.lumerite.lumeritemod.item.ModCreativeModTabs;
import net.lumerite.lumeritemod.item.ModItems;
import net.lumerite.lumeritemod.screen.ModMenuTypes;
import net.lumerite.lumeritemod.screen.dragonecrafter.DragoneCrafterScreen;
import net.lumerite.lumeritemod.screen.extractorblock.ExtractorScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.awt.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(LumeriteMod.MOD_ID)
public class LumeriteMod
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "lumeritemod";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public LumeriteMod(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();

        ModItems.register(modEventBus);
        ModCreativeModTabs.register(modEventBus);
        ModBlock.register(modEventBus);

        ModMenuTypes.register(modEventBus);
        ModBlockEntities.register(modEventBus);

        modEventBus.addListener(this::commonSetup);



        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.EXTRACTOR_MENU.get(), ExtractorScreen::new);
            MenuScreens.register(ModMenuTypes.DragoneCrafterMenu.get(), DragoneCrafterScreen::new);

            ClientSetup.init(event);
        }
    }
}
