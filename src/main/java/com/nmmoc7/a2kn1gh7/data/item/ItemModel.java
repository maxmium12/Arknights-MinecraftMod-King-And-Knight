package com.nmmoc7.a2kn1gh7.data.item;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.block.base.ModBlockSupplierBase;
import com.nmmoc7.a2kn1gh7.item.ModItems;
import com.nmmoc7.a2kn1gh7.item.base.ModItemSupplierBase;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * @author DustW
 */
public class ItemModel extends ItemModelProvider {
    public static final ModelFile.UncheckedModelFile GENERATED =
            new ModelFile.UncheckedModelFile("item/generated");

    public static final ModelFile.UncheckedModelFile TEMPLATE_SPAWN_EGG = new ModelFile.UncheckedModelFile("item/template_spawn_egg");

    public ItemModel(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, A2kn1gh7.MODID, exFileHelper);
    }

    private ModelFile getModel(String loc) {
        return new ModelFile.UncheckedModelFile(new ResourceLocation(modid, loc));
    }

    protected void simpleItem(Item item, ResourceLocation texture) {
        singleTexture(item.getRegistryName().getPath(), mcLoc("generated"), "layer0", texture);
    }

    protected void simpleItem(Item item) {
        simpleItem(item, modLoc("item/" + item.getRegistryName().getPath()));
    }

    protected void simpleBlockItem(BlockItem blockItem) {
        getBuilder(blockItem.getRegistryName().getPath())
                .parent(getModel("block/" + blockItem.getRegistryName().getPath()));
    }

    protected void namedBlockItem(BlockItem blockItem, String name) {
        getBuilder(blockItem.getRegistryName().getPath())
                .parent(getModel("block/" + name));
    }

    protected void axisBlockItem(BlockItem blockItem) {
        getBuilder(blockItem.getRegistryName().getPath())
                .parent(getModel("block/" + blockItem.getRegistryName().getPath() + "_horizontal"));
    }

    protected void logBlockItem(BlockItem blockItem) {
        simpleBlockItem(blockItem);
    }

    @Override
    protected void registerModels() {
        for (Item item: ModItems.ITEM_LIST) {
            if (item instanceof ModItemSupplierBase.ModItemBase) {
                simpleItem(item);
            }
            else if (item instanceof ModBlockSupplierBase.ModItemBlockBase) {
                simpleBlockItem((BlockItem) item);
            }
        }

        addTrees();
        addCrops();
        addTools();
        addMachineBlockItems();
    }

    private void addTrees() {
    }

    private void addCrops() {
    }

    private void addTools() {
    }

    private void addMachineBlockItems() {
    }
}
