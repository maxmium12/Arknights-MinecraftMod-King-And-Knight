package com.nmmoc7.kingandkinght.data.recipes;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.item.ModItems;
import com.nmmoc7.kingandkinght.machines.recipes.InfrastructureRecipes;
import com.nmmoc7.kingandkinght.machines.recipes.ModRecipes;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
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
