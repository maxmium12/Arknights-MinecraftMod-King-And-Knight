package com.nmmoc7.kingandkinght.tileentity;

import com.nmmoc7.kingandkinght.recipes.InfrastructureRecipes;
import com.nmmoc7.kingandkinght.recipes.ModRecipes;
import com.nmmoc7.kingandkinght.tileentity.abstracts.AbstractTileEntity;
import net.minecraft.item.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author DustW
 */
public class InfrastructureTileEntity extends AbstractTileEntity {
    private List<InfrastructureRecipes> tmpFilterResult = null;

    public InfrastructureTileEntity() {
        super(ModTileEntities.INFRASTRUCTURE_TILE_ENTITY, 5, "infrastructure");
    }

    @Override
    public void tick() {
        craftRecipes();
    }

    @Override
    public void customOnSlotChange(int slot) {
        if (tmpFilterResult == null) {
            tmpFilterResult = ModRecipes.INFRASTRUCTURE_RECIPES_LIST.stream().filter((recipes) ->
                    recipes.hasInput(getHandler().getStackInSlot(slot))).collect(Collectors.toList());

            if (tmpFilterResult.size() == 0) {
                tmpFilterResult = null;
            }
        }
        else {
            tmpFilterResult = tmpFilterResult.stream().filter((recipes) ->
                    recipes.hasInput(getHandler().getStackInSlot(slot))).collect(Collectors.toList());
        }
    }

    public void craftRecipes() {
        if (tmpFilterResult != null) {
            for (InfrastructureRecipes recipes: tmpFilterResult) {
                ItemStack[] tmpInputs = new ItemStack[getHandler().getSlots()];

                for (int i = 0; i < getHandler().getSlots(); i++) {
                    tmpInputs[i] = getHandler().getStackInSlot(i);
                }

                if (recipes.isValid(tmpInputs)) {
                    recipes.getInputs().forEach((key, value) -> {
                        getHandler().shrinkStackUnCheck(new ItemStack(key, value));
                    });

                    getHandler().setResult(recipes.getRecipeOutput().copy());
                    return;
                }
            }
        }
    }
}
