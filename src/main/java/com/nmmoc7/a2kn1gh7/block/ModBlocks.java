package com.nmmoc7.a2kn1gh7.block;

import com.nmmoc7.a2kn1gh7.block.base.ModBlockSupplierBase;
import com.nmmoc7.a2kn1gh7.itemgroup.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ModBlocks {
    public static final ArrayList<Block> BLOCK_LIST = new ArrayList<>();

    public static final ModBlockSupplierBase SOURCE_ROCKS_BLOCK = new ModBlockSupplierBase("source_rocks_block", Material.IRON, ModItemGroups.BLOCK_GROUP);
    public static final ModBlockSupplierBase BUILDING_MATERIAL = new ModBlockSupplierBase("building_material", Material.IRON, ModItemGroups.BLOCK_GROUP);
}
