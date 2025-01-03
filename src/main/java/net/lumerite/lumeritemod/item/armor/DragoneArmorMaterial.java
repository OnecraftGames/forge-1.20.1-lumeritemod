package net.lumerite.lumeritemod.item.armor;

import net.lumerite.lumeritemod.item.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Items;

public class DragoneArmorMaterial implements ArmorMaterial {
    private static final int[] DURABILITY = new int[]{95, 138, 130, 112}; // Casque, plastron, jambière, bottes
    private static final int[] DEFENSE = new int[]{4, 7, 8, 4}; // Casque, plastron, jambière, bottes

    @Override
    public int getDurabilityForType(ArmorItem.Type type) {
        return DURABILITY[type.getSlot().getIndex()] * 30; // Multiplieur pour durabilité
    }

    @Override
    public int getDefenseForType(ArmorItem.Type type) {
        return DEFENSE[type.getSlot().getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return 25; // Valeur d'enchantement
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.DRAGONE_INGOT.get()); // Ingrédient de réparation
    }

    @Override
    public String getName() {
        return "lumeritemod:dragone"; // Utilisé pour les textures (ex. "dragone_layer_1.png")
    }

    @Override
    public float getToughness() {
        return 5F; // Résistance de l'armure
    }

    @Override
    public float getKnockbackResistance() {
        return 0.1F; // Résistance au recul
    }
}
