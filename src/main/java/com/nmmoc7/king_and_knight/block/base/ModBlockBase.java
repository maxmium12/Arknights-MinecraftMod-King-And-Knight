package com.nmmoc7.king_and_knight.block.base;

import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

/**
 * @author reallyTouch
 */
public class ModBlockBase extends Block {
    public ModBlockBase(String regName, Material material, ItemGroup group) {
        this(regName, Properties.create(material), group);
    }

    public ModBlockBase(String regName, Properties properties, ItemGroup group) {
        super(properties);

        this.setRegistryName(new ResourceLocation(KingAndKnight.MOD_ID, regName));
        new ModItemBlockBase(this, group);

        ModBlocks.BLOCK_LIST.add(this);
    }
}
