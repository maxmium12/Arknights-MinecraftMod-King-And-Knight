package com.nmmoc7.king_and_knight.recipes;

import com.nmmoc7.king_and_knight.tileentity.abstracts.AbstractTileEntity;
import net.minecraft.item.crafting.Ingredient;

import java.util.*;

/**
 * @author DustW
 */
public class RecipeUtil {
    public static boolean matches(Ingredient inputs, AbstractTileEntity.ModItemStackHandlerBase itemHandler) {
        return Arrays.stream(inputs.getMatchingStacks()).allMatch(itemHandler::contains);
    }
}
