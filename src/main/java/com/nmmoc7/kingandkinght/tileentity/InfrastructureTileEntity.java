package com.nmmoc7.kingandkinght.tileentity;

import com.nmmoc7.kingandkinght.ModServerCounter;
import com.nmmoc7.kingandkinght.recipes.InfrastructureRecipes;
import com.nmmoc7.kingandkinght.recipes.ModRecipeBase;
import com.nmmoc7.kingandkinght.recipes.ModRecipes;
import com.nmmoc7.kingandkinght.tileentity.abstracts.AbstractTileEntity;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

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
