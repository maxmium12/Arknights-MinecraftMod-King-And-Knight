package com.nmmoc7.king_and_knight.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.block.ModBlocks;
import com.nmmoc7.king_and_knight.tileentity.abstracts.AbstractTileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author DustW
 */
public class InfrastructureRecipes extends ModRecipeBase {
    public static final Serializer SERIALIZER = new Serializer();

    private static final String INPUT_KEY = "input";
    private static final String OUTPUT_KEY = "output";

    private final Ingredient inputs;
    private final ItemStack output;
    private final ResourceLocation id;

    public InfrastructureRecipes(ResourceLocation id, ItemStack[] inputs, ItemStack output) {
        this.inputs = Ingredient.fromStacks(inputs);
        this.output = output;
        this.id = id;
    }

    public InfrastructureRecipes(ResourceLocation id, Ingredient inputs, ItemStack output) {
        this.inputs = inputs;
        this.output = output;
        this.id = id;
    }

    public Ingredient getInputs() {
        return inputs;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void serialize(JsonObject json) {
        SERIALIZER.write(json, this);
    }

    @Override
    public ResourceLocation getID() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    @Nullable
    @Override
    public JsonObject getAdvancementJson() {
        return null;
    }

    @Nullable
    @Override
    public ResourceLocation getAdvancementID() {
        return null;
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipes.INFRASTRUCTURE_RECIPES;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(ModBlocks.INFRASTRUCTURE_ONE);
    }

    public boolean isValid(AbstractTileEntity.ModItemStackHandlerBase itemHandler) {
        return RecipeUtil.matches(this.inputs, itemHandler);
    }

    public InfrastructureRecipes addToList(List<InfrastructureRecipes> list) {
        list.add(this);
        return this;
    }

    public static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<InfrastructureRecipes> {
        Serializer() {
            setRegistryName(KingAndKnight.MOD_ID, "infrastructure_recipes");
        }

        @Override
        public InfrastructureRecipes read(ResourceLocation recipeId, JsonObject json) {
            ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, OUTPUT_KEY));

            Ingredient ingredient = Ingredient.deserialize(json.get(INPUT_KEY));

            return new InfrastructureRecipes(recipeId, ingredient, output);
        }

        @Nullable
        @Override
        public InfrastructureRecipes read(ResourceLocation recipeId, PacketBuffer buffer) {
            Ingredient inputs = Ingredient.read(buffer);

            ItemStack output = buffer.readItemStack();

            ResourceLocation id = buffer.readResourceLocation();

            return new InfrastructureRecipes(id, inputs, output);
        }

        @Override
        public void write(PacketBuffer buffer, InfrastructureRecipes recipe) {
            recipe.inputs.write(buffer);

            buffer.writeItemStack(recipe.output);

            buffer.writeResourceLocation(recipe.id);
        }

        public void write(JsonObject json, InfrastructureRecipes recipe) {
            json.add(INPUT_KEY, recipe.inputs.serialize());

            JsonObject result = new JsonObject();
            result.addProperty("item", recipe.output.getItem().getRegistryName().toString());
            result.addProperty("nbt", Optional.ofNullable(recipe.output.getTag()).orElseGet(CompoundNBT::new).toString());
            json.add(OUTPUT_KEY, result);
        }

        public ItemStack getInputItemFromJsonArray(JsonArray json) {
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.get(0).getAsString()));
            return new ItemStack(item, json.get(1).getAsInt());
        }
    }
}
