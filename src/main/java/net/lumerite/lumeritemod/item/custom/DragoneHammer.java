package net.lumerite.lumeritemod.item.custom;

import net.lumerite.lumeritemod.block.ModBlock;
import net.lumerite.lumeritemod.item.constructor.HammerConstructor;
import net.lumerite.lumeritemod.item.tiers.DragoneHammerTiers;
import net.lumerite.lumeritemod.item.tiers.DragonePickaxeTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class DragoneHammer extends HammerConstructor {

    public DragoneHammer() {
        super(new DragoneHammerTiers(), "tooltip.dragone_hammer");

    }

}
