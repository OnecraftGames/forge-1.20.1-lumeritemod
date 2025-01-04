package net.lumerite.lumeritemod.block.entity;

import net.lumerite.lumeritemod.block.ModBlock;
import net.lumerite.lumeritemod.item.ModItems;
import net.lumerite.lumeritemod.managers.DragoneCrafterRecipesManager;
import net.lumerite.lumeritemod.screen.dragonecrafter.DragoneCrafterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
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
import oshi.util.tuples.Pair;

public class DragoneCrafterEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(6);

    private static final int INPUT_SLOT_1 = 0;   //
    private static final int INPUT_SLOT_2 = 1; //
    private static final int INPUT_SLOT_3 = 2; //
    private static final int INPUT_SLOT_4 = 3; //
    private static final int INPUT_SLOT_5 = 4; //
    private static final int OUTPUT_SLOT = 5; //



    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 78;

    public DragoneCrafterEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DRAGONE_CRAFTER_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> DragoneCrafterEntity.this.progress;
                    case 1 -> DragoneCrafterEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch (pIndex) {
                    case 0 -> DragoneCrafterEntity.this.progress = pValue;
                    case 1 -> DragoneCrafterEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 6;
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

    private final DragoneCrafterRecipesManager recipeManager = new DragoneCrafterRecipesManager();

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);

        recipeManager.loadRecipes();
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
        return Component.translatable("block.lumeritemod.dragone_crafter");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DragoneCrafterMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("crafter.progress", progress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("crafter.progress");
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (!pLevel.isClientSide()) {
            for (DragoneCrafterRecipesManager.Recipe recipe : recipeManager.getRecipes()) {
                if (matchesRecipe(recipe)) {
                    increaseCraftingProgress();
                    if (hasProgressFinished()) {
                        craftItem(recipe);
                        resetProgress();
                    }
                    return; // Arrête après avoir trouvé une recette correspondante
                }
            }
            resetProgress();
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void craftItem(DragoneCrafterRecipesManager.Recipe recipe) {

        // Consomme les ingrédients
        for (Pair<Integer, ItemStack> entry : recipe.inputs) {
            int slot = entry.getA();
            int count = entry.getB().getCount();
            itemHandler.extractItem(slot, count, false);
        }

        // Ajoute l'item de sortie
        ItemStack output = recipe.output;
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }


    /// ////////////////////////////////////////////////////
    ///
    ///

    private boolean hasRecipe() {
        for (DragoneCrafterRecipesManager.Recipe recipe : recipeManager.getRecipes()) {
            if (matchesRecipe(recipe)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesRecipe(DragoneCrafterRecipesManager.Recipe recipe) {
        for (Pair<Integer, ItemStack> entry : recipe.inputs) {
            int slot = entry.getA();
            ItemStack required = entry.getB();
            ItemStack provided = itemHandler.getStackInSlot(slot);

            if (!ItemStack.isSameItem(required, provided) || provided.getCount() < required.getCount()) {
                return false;
            }
        }
        return true;
    }


    /// ////////////////////////////////////////////////////

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasProgressFinished() {
        return progress >= maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }
}
