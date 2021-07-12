package com.nmmoc7.king_and_knight.recipes;

import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/**
 * @author DustW
 */
public abstract class ModRecipeBase implements IRecipe<IInventory>, IFinishedRecipe {
    @Override
    public boolean matches(IInventory inv, World worldIn) {
        // 最 好 别 用
        throw new IllegalStateException();
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        // 最 好 别 用
        throw new IllegalStateException();
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public boolean isDynamic() {
        return true;
    }
}
