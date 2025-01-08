package net.lumerite.lumeritemod.managers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import oshi.util.tuples.Pair;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DragoneCrafterRecipesManager {

    public static class Recipe {
        public final List<Pair<Integer, ItemStack>> inputs;
        public final ItemStack output;

        public Recipe(List<Pair<Integer, ItemStack>> inputs, ItemStack output) {
            this.inputs = inputs;
            this.output = output;
        }
    }

    private final List<Recipe> recipes = new ArrayList<>();

    public void loadRecipes() {
        try {
            ResourceLocation recipeLocation = new ResourceLocation("lumeritemod", "recipes/dragone_crafter_custom_recipes.json");

            Optional<Resource> resource = Minecraft.getInstance().getResourceManager().getResource(recipeLocation);

            // Lire la ressource avec un InputStream
            try (Reader reader = new InputStreamReader(resource.get().open())) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();

                // Traitez le JSON (par exemple, affichez son contenu)

                JsonArray recipesArray = json.getAsJsonArray("recipes");

                for (int i = 0; i < recipesArray.size(); i++) {
                    JsonObject recipeJson = recipesArray.get(i).getAsJsonObject();

                    List<Pair<Integer, ItemStack>> inputs = new ArrayList<>();
                    JsonArray inputsArray = recipeJson.getAsJsonArray("inputs");
                    for (int j = 0; j < inputsArray.size(); j++) {
                        JsonObject input = inputsArray.get(j).getAsJsonObject();
                        int slot = input.get("slot").getAsInt();
                        String itemName = input.get("item").getAsString();

                        ResourceLocation result_recipe = new ResourceLocation(itemName);
                        Item item = BuiltInRegistries.ITEM.get(result_recipe);

                        int count = input.get("count").getAsInt();
                        inputs.add(new Pair<>(slot, new ItemStack(item, count)));
                    }

                    JsonObject outputJson = recipeJson.getAsJsonObject("output");
                    String outputItem = outputJson.get("item").getAsString();

                    ResourceLocation result_recipe = new ResourceLocation(outputItem);
                    Item item = BuiltInRegistries.ITEM.get(result_recipe);

                    int outputCount = outputJson.get("count").getAsInt();
                    ItemStack output = new ItemStack(item, outputCount);

                    recipes.add(new Recipe(inputs, output));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

}
