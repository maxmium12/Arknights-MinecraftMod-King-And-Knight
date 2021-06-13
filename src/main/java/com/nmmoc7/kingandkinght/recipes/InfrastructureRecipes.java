package com.nmmoc7.kingandkinght.recipes;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.block.ModBlocks;
import com.nmmoc7.kingandkinght.recipes.ModRecipes;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/**
 * @author DustW
 */
public class InfrastructureRecipes implements IRecipe<IInventory>, IFinishedRecipe {
    public static final Serializer SERIALIZER = new Serializer();

    private static final String INPUT_KEY = "input";
    private static final String OUTPUT_KEY = "output";
    private static final String MACHINE_SLOT_SIZE_KEY = "machineSlotSize";

    private final Map<Item, Integer> inputs = new HashMap<>();
    private final ItemStack output;
    private final int machineSlotSize;
    private final ResourceLocation id;

    public InfrastructureRecipes(ResourceLocation id, ItemStack[] inputs, ItemStack output, int machineSlotSize) {
        for (ItemStack input : inputs) {
            this.inputs.put(input.getItem(), input.getCount());
        }
        this.output = output;
        this.machineSlotSize = machineSlotSize;
        this.id = id;
    }

    public Map<Item, Integer> getInputs() {
        return inputs;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        for (int i = 0; i < machineSlotSize; i++) {
            if (inputs.get(inv.getStackInSlot(i).getItem()) != inv.getStackInSlot(i).getCount()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return this.output.copy();
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

    public boolean hasInput(ItemStack input) {
        return inputs.containsKey(input.getItem()) && inputs.get(input.getItem()) <= input.getCount();
    }

    public boolean isValid(ItemStack... inputs) {
        return Arrays.stream(inputs).noneMatch(this::hasInput);
    }

    @Override
    public boolean canFit(int width, int height) {
        return true;
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
            JsonArray inputEle = JSONUtils.getJsonArray(json, INPUT_KEY);

            ArrayList<ItemStack> inputs = new ArrayList<>();

            for (JsonElement input: inputEle) {
                inputs.add(getInputItemFromJsonArray(input.getAsJsonArray()));
            }

            ItemStack output = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, OUTPUT_KEY), true);

            int machineSlotSize = JSONUtils.getInt(json, MACHINE_SLOT_SIZE_KEY);

            return new InfrastructureRecipes(recipeId, inputs.toArray(new ItemStack[]{}), output, machineSlotSize);
        }

        @Nullable
        @Override
        public InfrastructureRecipes read(ResourceLocation recipeId, PacketBuffer buffer) {
            int machineSlotSize = buffer.readInt();

            ItemStack[] inputs = new ItemStack[machineSlotSize];

            for (int i = 0; i < machineSlotSize; i++) {
                inputs[i] = buffer.readItemStack();
            }

            ItemStack output = buffer.readItemStack();

            ResourceLocation id = buffer.readResourceLocation();

            return new InfrastructureRecipes(id, inputs, output, machineSlotSize);
        }

        @Override
        public void write(PacketBuffer buffer, InfrastructureRecipes recipe) {
            buffer.writeInt(recipe.machineSlotSize);

            recipe.inputs.forEach((key, value) -> {
                buffer.writeItemStack(new ItemStack(key, value));
            });

            buffer.writeItemStack(recipe.output);

            buffer.writeResourceLocation(recipe.id);
        }

        public void write(JsonObject json, InfrastructureRecipes recipe) {
            json.addProperty("machineSlotSize", recipe.machineSlotSize);

            JsonArray inputs = new JsonArray();

            recipe.inputs.forEach((key, value) -> {
                JsonArray input = new JsonArray();

                input.add(key.toString());
                input.add(value);

                inputs.add(input);
            });

            json.add(INPUT_KEY, inputs);

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
