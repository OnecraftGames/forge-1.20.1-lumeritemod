package net.lumerite.lumeritemod.item.items;

import net.lumerite.lumeritemod.LumeriteMod;
import net.lumerite.lumeritemod.item.ModCreativeModTabs;
import net.lumerite.lumeritemod.item.base.DamageableItem;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraft.world.item.PickaxeItem;

import java.util.function.Consumer;

public class TeleportScroll extends DamageableItem {

    private static final ResourceLocation MODE_PROPERTY = new ResourceLocation(LumeriteMod.MOD_ID, "mode");
    private static final int maxUses = 10;

    public TeleportScroll(Properties pProperties) {
        super(pProperties);
    }

    private void ensureNBT(ItemStack stack) {
        if (!stack.hasTag()) {
            stack.getOrCreateTag().putInt("usesLeft", maxUses);
            stack.getOrCreateTag().putBoolean("isNotNew", true);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ensureNBT(stack);

        // Exécution côté serveur uniquement
        if (level.isClientSide) {
            return InteractionResultHolder.pass(stack);
        }

        CompoundTag tag = stack.getOrCreateTag();
        int usesLeft = tag.getInt("usesLeft");
        int mode = tag.getInt("mode");

        if (usesLeft <= 0) {
            player.displayClientMessage(Component.translatable("client.message.action_bar.scroll_item_broken"), true);
            return InteractionResultHolder.fail(stack);
        }

        // === MODE 1 → Téléportation ===
        if (mode == 1) {

            Vec3 tp = getTargetCoordinates(stack);
            assert tp != null;

            // repasse en mode 0
            tag.putInt("mode", 0);
            tag.remove("tpX");
            tag.remove("tpY");
            tag.remove("tpZ");

            player.teleportTo(tp.x, tp.y, tp.z);
            tag.putInt("usesLeft", usesLeft - 1);

            return InteractionResultHolder.sidedSuccess(stack, false);
        }

        // === MODE 0 → Enregistrer la position actuelle ===
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();

        // si le stack est = 1
        tag.putInt("mode", 1);
        tag.putDouble("tpX", x);
        tag.putDouble("tpY", y);
        tag.putDouble("tpZ", z);

        return InteractionResultHolder.sidedSuccess(stack, false);
    }

    @Override
    public Component getName(ItemStack pStack) {
        int mode = pStack.hasTag() ? pStack.getOrCreateTag().getInt("mode") : 0;

        Vec3 tp = getTargetCoordinates(pStack);
        assert tp != null;

        if (mode == 1) {
            double roundedX = Math.round(tp.x * 100.0) / 100.0;
            double roundedY = Math.round(tp.y * 100.0) / 100.0;
            double roundedZ = Math.round(tp.z * 100.0) / 100.0;

            return Component.translatable("item.lumeritemod.active_teleport_scroll_item").append(" (" + roundedX + ", " + roundedY + ", " + roundedZ + ")");
        }

        return Component.translatable("item.lumeritemod.teleport_scroll_item");
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return 1;
    }

    @Override
    public boolean isBarVisible(ItemStack pStack) {
        return pStack.getOrCreateTag().getBoolean("isNotNew");
    }

    @Override
    public int getBarWidth(ItemStack pStack) {
        int usesLeft = pStack.getOrCreateTag().getInt("usesLeft");
        return (int) (13.0F * usesLeft / maxUses); // 13 = largeur de la barre
    }

    @Override
    public int getBarColor(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        int usesLeft = tag.getInt("usesLeft");

        float ratio = (float) usesLeft / maxUses; // 1 = full, 0 = vide

        int red = (int)((1 - ratio) * 255);
        int green = (int)(ratio * 255);
        int blue = 0;

        return (red << 16) | (green << 8) | blue;
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return maxUses;
    }

    @Override
    public int getDamage(ItemStack stack) {
        return maxUses - stack.getOrCreateTag().getInt("usesLeft");
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            ItemProperties.register(
                    TeleportScroll.this,
                    MODE_PROPERTY,
                    (stack, world, entity, seed) -> {
                        if (stack.hasTag()) {
                            return stack.getTag().getInt("mode");
                        }
                        return 0;
                    }
            );

        consumer.accept(IClientItemExtensions.DEFAULT);

    }

    private Vec3 getTargetCoordinates(ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.contains("tpX") && tag.contains("tpY") && tag.contains("tpZ")) {
            return new Vec3(tag.getDouble("tpX"), tag.getDouble("tpY"), tag.getDouble("tpZ"));
        }
        return null;
    }
}
