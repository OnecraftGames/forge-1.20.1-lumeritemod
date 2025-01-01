package net.lumerite.lumeritemod.item.tiers;

import net.lumerite.lumeritemod.item.ModItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.Item;

public class DragoneAxeTiers implements Tier {

    @Override
    public int getUses() {
        return 5000; // Durabilité
    }

    @Override
    public float getSpeed() {
        return 50.0F; // Vitesse de minage
    }

    @Override
    public float getAttackDamageBonus() {
        return 5.0F; // Bonus de dégâts
    }

    @Override
    public int getLevel() {
        return 3; // Niveau de minage (3 = DIAMOND)
    }

    @Override
    public int getEnchantmentValue() {
        return 22; // Valeur pour l'enchantabilité
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(new Item[]{ModItems.DRAGONE_INGOT.get()/* Votre Item ici, par exemple : Items.DIAMOND */});
    }

}
