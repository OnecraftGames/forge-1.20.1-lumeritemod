package net.lumerite.lumeritemod.item;


import net.lumerite.lumeritemod.block.ModBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS_BUILDER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "lumeritemod");

    public static final RegistryObject<CreativeModeTab> LUMERITE_TAB = CREATIVE_MODE_TABS_BUILDER.register("lumerite_tab",
            () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.LUMERITE_INGOT.get()))
                    .title(Component.translatable("creative_mode_tab.lumerite_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModItems.LUMERITE_INGOT.get());
                        pOutput.accept(ModItems.LUMERITE_NUGGET.get());
                        pOutput.accept(ModItems.LUMERITE_FRAG.get());

                        pOutput.accept(ModItems.DRAGONE_INGOT.get());
                        pOutput.accept(ModItems.DRAGONE_COMPRESSED.get());
                        pOutput.accept(ModItems.DRAGONE_HEART.get());

                        pOutput.accept(ModBlock.DRAGONE_BLOCK.get());
                        pOutput.accept(ModBlock.DRAGONE_ORE.get());

                        pOutput.accept(ModBlock.EXTRACTOR_BLOCK.get());
                        pOutput.accept(ModBlock.EXTRACTOR_CASING.get());
                        pOutput.accept(ModBlock.EXTRACTOR_FRAME.get());

                        pOutput.accept(ModItems.DRAGONE_PICKAXE.get());
                    })
                    .build()
    );

    public static void register(IEventBus e) {
        CREATIVE_MODE_TABS_BUILDER.register(e);
    }

}
