package com.nmmoc7.a2kn1gh7.block.base;

import com.nmmoc7.a2kn1gh7.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

/**
 * @author DustW
 */
public class ModItemBlockBase extends BlockItem {
    public ModItemBlockBase(Block blockIn, ItemGroup group) {
        super(blockIn, new Properties().group(group));

        this.setRegistryName(blockIn.getRegistryName());

        ModItems.ITEM_LIST.add(this);
    }
}
