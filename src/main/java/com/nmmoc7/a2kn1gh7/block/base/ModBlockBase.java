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
public class ModBlockBase extends Block {
    public ModBlockBase(String regName, Material material, ItemGroup group) {
        super(AbstractBlock.Properties.create(material));

        this.setRegistryName(new ResourceLocation(A2kn1gh7.MODID, regName));
        new ModItemBlockBase(this, group);

        ModBlocks.BLOCK_LIST.add(this);
    }
}
