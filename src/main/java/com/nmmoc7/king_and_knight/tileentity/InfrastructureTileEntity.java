package com.nmmoc7.king_and_knight.tileentity;

import com.nmmoc7.king_and_knight.ModServerCounter;
import com.nmmoc7.king_and_knight.recipes.ModRecipes;
import com.nmmoc7.king_and_knight.tileentity.abstracts.AbstractTileEntity;
import net.minecraft.item.ItemStack;

/**
 * @author DustW
 */
public class InfrastructureTileEntity extends AbstractTileEntity {
    public InfrastructureTileEntity() {
        super(ModTileEntities.INFRASTRUCTURE_TILE_ENTITY, 5, "infrastructure");
    }

    @Override
    public void tick() {
        if (ModServerCounter.count % ModServerCounter.TPS == 0) {
            craftRecipes();
        }
    }

    public void craftRecipes() {
        ModRecipes.INFRASTRUCTURE_RECIPES_LIST.forEach(recipes -> {
            if (handler.getResult().isEmpty() && recipes.isValid(getHandler())) {
                for (ItemStack itemStack: recipes.getInputs().getMatchingStacks()) {
                    handler.removeItem(itemStack);
                }

                getHandler().setResult(recipes.getRecipeOutput().copy());
            }
        });
    }
}
