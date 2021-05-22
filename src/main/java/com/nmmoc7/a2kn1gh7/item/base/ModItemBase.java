package com.nmmoc7.a2kn1gh7.item.base;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

/**
 * @author DustW
 */
public  class ModItemBase extends Item {
    public ModItemBase(String regName, ItemGroup group) {
        super(new Properties().group(group));

        this.setRegistryName(new ResourceLocation(A2kn1gh7.MODID, regName));

        ModItems.ITEM_LIST.add(this);
    }
}
