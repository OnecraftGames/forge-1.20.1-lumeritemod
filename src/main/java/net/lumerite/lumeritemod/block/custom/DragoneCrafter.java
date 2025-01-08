package net.lumerite.lumeritemod.block.custom;

import net.lumerite.lumeritemod.block.constructor.DragoneCrafterConstructor;
import net.lumerite.lumeritemod.block.entity.DragoneCrafterEntity;
import net.lumerite.lumeritemod.block.entity.ModBlockEntities;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DragoneCrafter extends DragoneCrafterConstructor {
    public DragoneCrafter(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {

        pTooltip.add(Component.empty());
        pTooltip.add(Component.translatable("tooltip.dragone_crafter_iron"));
        pTooltip.add(Component.empty());
    }

}
