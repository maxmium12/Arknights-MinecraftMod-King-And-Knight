package com.nmmoc7.kingandkinght.recipes;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ModRecipes {
    public static final ArrayList<InfrastructureRecipes> INFRASTRUCTURE_RECIPES_LIST = new ArrayList<>();

    public static final IRecipeType<InfrastructureRecipes> INFRASTRUCTURE_RECIPES = IRecipeType.register("infrastructure_recipes");

    public static final InfrastructureRecipes TEST_RECIPES = new InfrastructureRecipes(
                new ResourceLocation(KingAndKnight.MOD_ID, "test"),
                new ItemStack[]{new ItemStack(Items.APPLE, 2)},
                new ItemStack(ModItems.ESTER),
                6).addToList(INFRASTRUCTURE_RECIPES_LIST);
}
