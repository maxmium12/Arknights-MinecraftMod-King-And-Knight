package com.nmmoc7.a2kn1gh7.itemgroup;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.block.ModBlocks;
import com.nmmoc7.a2kn1gh7.item.ModItems;
import com.nmmoc7.a2kn1gh7.item.ModWeapons;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * @author DustW
 */
public class ModItemGroups {
    public static final ItemGroup ITEM_GROUP = new ItemGroup(A2kn1gh7.MODID + ".item_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.SOURCE_ROCKS);
        }
    };

    public static final ItemGroup BLOCK_GROUP = new ItemGroup(A2kn1gh7.MODID + ".block_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.SOURCE_ROCKS_BLOCK);
        }
    };

    public static final ItemGroup WEAPON_GROUP = new ItemGroup(A2kn1gh7.MODID + ".weapon_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModWeapons.IRIS_WEAPON);
        }
    };
}
