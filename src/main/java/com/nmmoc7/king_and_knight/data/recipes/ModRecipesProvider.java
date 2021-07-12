package com.nmmoc7.king_and_knight.data.recipes;

import com.nmmoc7.king_and_knight.recipes.ModRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraftforge.common.data.ForgeRecipeProvider;

import java.util.function.Consumer;

/**
 * @author DustW
 */
public class ModRecipesProvider extends ForgeRecipeProvider {
    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerInfrastructureRecipes(consumer);
    }

    protected void registerInfrastructureRecipes(Consumer<IFinishedRecipe> consumer) {
        ModRecipes.INFRASTRUCTURE_RECIPES_LIST.forEach(consumer);
    }
}
