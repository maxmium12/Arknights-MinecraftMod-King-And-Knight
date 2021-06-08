package com.nmmoc7.kingandkinght.machines.tileentity;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.machines.recipes.InfrastructureRecipes;
import com.nmmoc7.kingandkinght.machines.recipes.ModRecipes;
import com.nmmoc7.kingandkinght.machines.tileentity.abstracts.AbstractTileEntity;
import com.nmmoc7.kingandkinght.machines.tileentity.container.ProcessingStationContainer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author DustW
 */
public class InfrastructureTileEntity extends AbstractTileEntity {
    private List<InfrastructureRecipes> tmpFilterResult = null;

    public InfrastructureTileEntity() {
        super(ModTileEntities.INFRASTRUCTURE_TILE_ENTITY, 5, "infrastructure");
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(
                Util.makeTranslationKey("tileentity",
                        new ResourceLocation(KingAndKnight.MOD_ID, this.registerName + level)
                )
        );
    }

    @Override
    public void customOnSlotChange(int slot) {
        if (openingPlayer != null && openingPlayer.inventory.getItemStack().isEmpty()) {
            KingAndKnight.LOGGER.warn("out=======================");
            tmpFilterResult = null;
        }
        else {
            KingAndKnight.LOGGER.warn("in========================");

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

            if (tmpFilterResult != null) {
                tmpFilterResult.forEach((recipes) -> {
                    ArrayList<ItemStack> tmpInputs = new ArrayList<>();

                    for (int i = 0; i < getHandler().getSlots(); i++) {
                        tmpInputs.add(getHandler().getStackInSlot(i));
                    }

                    if (recipes.isValid(tmpInputs.toArray(new ItemStack[]{}))) {
                        getHandler().setResult(recipes.getRecipeOutput().copy());
                    }
                });
            }
        }
    }

    @Nullable
    @Override
    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
        return new ProcessingStationContainer(p_createMenu_1_, p_createMenu_2_, pos, world);
    }
}
