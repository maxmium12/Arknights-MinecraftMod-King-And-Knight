package com.nmmoc7.king_and_knight.item.base;

import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

/**
 * @author DustW
 */
public  class ModItemBase extends Item {
    public ModItemBase(String regName, ItemGroup group) {
        super(new Properties().group(group));

        this.setRegistryName(new ResourceLocation(KingAndKnight.MOD_ID, regName));

        ModItems.ITEM_LIST.add(this);
    }
}
