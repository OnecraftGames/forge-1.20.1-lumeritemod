package net.lumerite.lumeritemod.item.custom;

import net.lumerite.lumeritemod.block.ModBlock;
import net.lumerite.lumeritemod.item.tiers.DragonePickaxeTiers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;


public class DragoneHammer extends PickaxeItem {


    public DragoneHammer() {
        super(new DragonePickaxeTiers(), 1, -2.8F, new Item.Properties());

    }



    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, BlockPos pos, LivingEntity entity) {
        if (!world.isClientSide()) {
            // Casser les blocs autour
            breakAdjacentBlocks(world, pos, entity);
        }
        return super.mineBlock(stack, world, state, pos, entity);
    }

    private BlockHitResult rayTrace(Level world, Player player) {
        Vec3 eyePos = player.getEyePosition(1.0F);
        Vec3 lookVec = player.getViewVector(1.0F);
        Vec3 rayEnd = eyePos.add(lookVec.x * 5, lookVec.y * 5, lookVec.z * 5);
        return world.clip(new ClipContext(eyePos, rayEnd, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
    }

    private void breakAdjacentBlocks(Level world, BlockPos pos, LivingEntity entity) {

        BlockState minedBlock = world.getBlockState(pos.offset(0, 0, 0));

        boolean isOre = isOreBlock(minedBlock);

            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        // Ne pas casser le bloc central
                        if (dx == 0 && dy == 0 && dz == 0) continue;

                        Direction direction = rayTrace(world, entity.level().getPlayerByUUID(entity.getUUID())).getDirection();
                        System.out.println(direction);

                        BlockPos adjacentPos = pos.offset(dx, dx, dz);


                        if (direction == Direction.UP || direction == Direction.DOWN) {
                            adjacentPos = pos.offset(dx, 0, dz);
                        } else if (direction == Direction.EAST || direction == Direction.WEST) {
                            adjacentPos = pos.offset(0, dy, dz);
                        } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                            adjacentPos = pos.offset(dx, dy, 0);
                        }


                        BlockState adjacentState = world.getBlockState(adjacentPos);

                        boolean isOres = isOreBlock(adjacentState);

                        // Vérifiez si le bloc peut être miné avec l'outil
                        if (!isOres) {
                            world.destroyBlock(adjacentPos, true); // true = drope les items
                        } else if (minedBlock.getBlock() == adjacentState.getBlock() && isOre) {
                            world.destroyBlock(adjacentPos, true); // true = drope les items
                        }
                    }
                }
            }
    }

    private boolean isOreBlock(BlockState state) {
        return state.is(BlockTags.COAL_ORES) ||
                state.is(BlockTags.IRON_ORES) ||
                state.is(BlockTags.COPPER_ORES) ||
                state.is(BlockTags.GOLD_ORES) ||
                state.is(BlockTags.DIAMOND_ORES) ||
                state.is(BlockTags.EMERALD_ORES) ||
                state.is(BlockTags.LAPIS_ORES) ||
                state.is(BlockTags.REDSTONE_ORES) ||
                state.is(ModBlock.DRAGONE_ORE.get());
    }

}
