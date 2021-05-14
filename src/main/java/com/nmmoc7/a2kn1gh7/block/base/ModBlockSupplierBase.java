package com.nmmoc7.a2kn1gh7.block.base;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.block.ModBlocks;
import com.nmmoc7.a2kn1gh7.item.ModItems;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;

/**
 * @author reallyTouch
 */
public class ModBlockSupplierBase implements Supplier<Block> {
    protected final Block BLOCK;

    public ModBlockSupplierBase(String regName, Material material, ItemGroup group) {
        AbstractBlock.Properties properties = AbstractBlock.Properties.create(material);
        BLOCK = new ModBlockBase(regName, properties);

        Item.Properties blockItemProperties = new Item.Properties().group(group);
        new ModItemBlockBase(BLOCK, blockItemProperties);
    }

    @Override
    public Block get() {
        return BLOCK;
    }

    public static class ModBlockBase extends Block {
        protected ModBlockBase(String regName, Properties properties) {
            super(properties);

            this.setRegistryName(new ResourceLocation(A2kn1gh7.MODID, regName));

            ModBlocks.BLOCK_LIST.add(this);
        }
    }

    public static class ModItemBlockBase extends BlockItem {
        protected ModItemBlockBase(Block blockIn, Properties builder) {
            super(blockIn, builder);

            this.setRegistryName(blockIn.getRegistryName());

            ModItems.ITEM_LIST.add(this);
        }
    }
}
