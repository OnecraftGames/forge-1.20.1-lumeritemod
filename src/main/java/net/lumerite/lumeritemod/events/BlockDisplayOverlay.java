package net.lumerite.lumeritemod.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = "lumeritemod", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class BlockDisplayOverlay {

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {

        // Accéder à Minecraft
        Minecraft minecraft = Minecraft.getInstance();
        GuiGraphics graphics = event.getGuiGraphics();
        // Récupérer les objets nécessaires pour dessiner;

        // Vérifie si le joueur est dans un monde et que le résultat de visée est un bloc
        if (minecraft.player != null && minecraft.hitResult instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            Block block = minecraft.level.getBlockState(pos).getBlock();
            String blockName = block.getName().getString();

            if (block == Blocks.AIR) {
                blockName = "";
            }

            // Obtenir l'icône du bloc sous forme d'ItemStack
            ItemStack blockStack = new ItemStack(block.asItem());
            Font font = minecraft.font;

            // Afficher l'icône et le nom du bloc à l'écran
            graphics.renderItem(blockStack, 10, 10); // Position de l'icône
            graphics.drawString(font, blockName, 30, 15, 0xFFFFFF);
        }


        for (int i = 0; i < 4; i++) {

            ItemStack item = minecraft.player.getInventory().armor.get(3 - i);


            int y = 50 + i * 20;

            graphics.renderItem(item, 10, y);

            // Calculer la durabilité de l'item (si c'est applicable)
            if (item.isDamageableItem()) {
                int maxDurability = item.getMaxDamage();
                int currentDurability = maxDurability - item.getDamageValue();
                float durabilityPercent = (float) currentDurability / maxDurability;

                int green = (int)(255 * durabilityPercent);
                int red = 255 - green;
                int color = (red << 16) | (green << 8);

                graphics.drawString(minecraft.font, String.valueOf(currentDurability), 30, y + 5, color);
            }

        }


    }
}
