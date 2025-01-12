package net.lumerite.lumeritemod.item.armor;

import net.lumerite.lumeritemod.item.ModItems;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DragoneArmorItem extends ArmorItem {
    public DragoneArmorItem(ArmorMaterial material, Type slot, Properties properties) {
        super(material, slot, properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {


            if (pStack.getItem() == ModItems.DRAGONE_HELMET.get()) {
                if (Screen.hasShiftDown()) {
                    pTooltipComponents.add(Component.empty());
                    pTooltipComponents.add(Component.translatable("tooltip.dragone_helmet"));
                    pTooltipComponents.add(Component.empty());
                } else {
                    pTooltipComponents.add(Component.translatable("tooltip.hold_shift"));
                }
            } else if (pStack.getItem() == ModItems.DRAGONE_BOOTS.get()) {
                if (Screen.hasShiftDown()) {
                    pTooltipComponents.add(Component.empty());
                    pTooltipComponents.add(Component.translatable("tooltip.dragone_boots"));
                    pTooltipComponents.add(Component.empty());
                } else {
                    pTooltipComponents.add(Component.translatable("tooltip.hold_shift"));
                }

            }




    }
}
