package net.lumerite.lumeritemod.block.entity;

import net.lumerite.lumeritemod.block.ModBlock;
import net.lumerite.lumeritemod.item.ModItems;
import net.lumerite.lumeritemod.screen.extractorblock.ExtractorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExtractorBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(4);

    private static final int INPUT_SLOT = 0;
    private static final int INPUT_SLOT_CHARGED = 1;
    private static final int OUTPUT_SLOT_INGOT = 2;
    private static final int OUTPUT_SLOT_FRAG = 3;



    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public ExtractorBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.EXTRCTOR_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> ExtractorBlockEntity.this.progress;
                    case 1 -> ExtractorBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> ExtractorBlockEntity.this.progress = pValue;
                    case 1 -> ExtractorBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.lumeritemod.extractor_block");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new ExtractorMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("extractor.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("extractor.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if(hasRecipe() && hasPowerCompressedDradon()) {
            increaseCraftingProgress();
            setChanged(pLevel, pPos, pState);

            if(hasProgressFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem() {
        ItemStack result = new ItemStack(ModItems.DRAGONE_INGOT.get(), 4);
        ItemStack resultFrag = new ItemStack(ModItems.LUMERITE_NUGGET.get(), 1);
        this.itemHandler.extractItem(INPUT_SLOT, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_CHARGED, 1, false);

        int n = (int) (Math.random() * 5);

        if (n == 1) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT_FRAG, new ItemStack(resultFrag.getItem(),
                    this.itemHandler.getStackInSlot(OUTPUT_SLOT_FRAG).getCount() + resultFrag.getCount()));
        }

        this.itemHandler.setStackInSlot(OUTPUT_SLOT_INGOT, new ItemStack(result.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT_INGOT).getCount() + result.getCount()));
    }

    private boolean hasPowerCompressedDradon() {
        boolean isDragonCompressed = this.itemHandler.getStackInSlot(INPUT_SLOT_CHARGED).getItem() == ModItems.DRAGONE_COMPRESSED.get();
        boolean countOfDragonCompressed = this.itemHandler.getStackInSlot(INPUT_SLOT_CHARGED).getCount() >= 1;

        return isDragonCompressed && countOfDragonCompressed;
    }

    private boolean hasRecipe() {
        boolean hasCraftingItem = this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == ModBlock.DRAGONE_BLOCK.get().asItem();
        ItemStack result = new ItemStack(ModItems.DRAGONE_INGOT.get());

        return hasCraftingItem && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT_INGOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT_INGOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT_INGOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT_INGOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }
}