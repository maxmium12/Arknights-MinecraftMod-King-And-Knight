package com.nmmoc7.a2kn1gh7.multiblock;

import com.nmmoc7.a2kn1gh7.multiblock.base.ModMultiBlockCoreSupplierBase;
import com.nmmoc7.a2kn1gh7.multiblock.base.ModMultiBlockTileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ModMultiBlockCores {
    public static final ArrayList<Block> CORES = new ArrayList<>();

    public static final ModMultiBlockCoreSupplierBase TEST_CORE = new ModMultiBlockCoreSupplierBase("test_core", Material.ANVIL, TestMultiBlockTileEntity::new);
}
