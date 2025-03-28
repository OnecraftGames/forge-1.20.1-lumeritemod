package net.lumerite.lumeritemod.events;

import net.lumerite.lumeritemod.block.ModBlock;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "lumeritemod", bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

    public static void init(FMLClientSetupEvent event) {
        // Définir le type de rendu pour les blocs spécifiques
        ItemBlockRenderTypes.setRenderLayer(ModBlock.DRAGONE_GLASS.get(), RenderType.cutout());
    }

}

