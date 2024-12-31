package net.lumerite.lumeritemod.screen;

import net.lumerite.lumeritemod.LumeriteMod;
import net.lumerite.lumeritemod.screen.extractorblock.ExtractorMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPE_DEFERRED_REGISTER =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, LumeriteMod.MOD_ID);

    public static final RegistryObject<MenuType<ExtractorMenu>> EXTRACTOR_MENU =
            registerMenuType("extractor_menu", ExtractorMenu::new);

    public static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(String name, IContainerFactory<T> factory) {
        return MENU_TYPE_DEFERRED_REGISTER.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus e) {
        MENU_TYPE_DEFERRED_REGISTER.register(e);
    }
}
