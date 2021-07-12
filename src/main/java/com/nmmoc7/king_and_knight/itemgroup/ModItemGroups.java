package com.nmmoc7.king_and_knight.itemgroup;

import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.block.ModBlocks;
import com.nmmoc7.king_and_knight.item.ModItems;
import com.nmmoc7.king_and_knight.item.ModWeapons;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

/**
 * @author DustW
 */
public class ModItemGroups {
    public static final ItemGroup ITEM_GROUP = new ItemGroup(KingAndKnight.MOD_ID + ".item_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.SOURCE_ROCKS);
        }
    };

    public static final ItemGroup BLOCK_GROUP = new ItemGroup(KingAndKnight.MOD_ID + ".block_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.SOURCE_ROCKS_BLOCK);
        }
    };

    public static final ItemGroup WEAPON_GROUP = new ItemGroup(KingAndKnight.MOD_ID + ".weapon_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModWeapons.IRIS_WEAPON);
        }
    };

    public static final ItemGroup MACHINE_GROUP = new ItemGroup(KingAndKnight.MOD_ID + ".machine_group") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.INFRASTRUCTURE_ONE);
        }
    };
}
