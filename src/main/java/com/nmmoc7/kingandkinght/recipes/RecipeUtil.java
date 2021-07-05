package com.nmmoc7.kingandkinght.recipes;

import com.nmmoc7.kingandkinght.tileentity.abstracts.AbstractTileEntity;
import it.unimi.dsi.fastutil.ints.IntSet;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @author DustW
 */
public class RecipeUtil {
    public static boolean matches(Ingredient inputs, AbstractTileEntity.ModItemStackHandlerBase itemHandler) {
        return Arrays.stream(inputs.getMatchingStacks()).allMatch(itemHandler::contains);
    }
}
