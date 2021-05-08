package com.nmmoc7.a2kn1gh7.item.base;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;

import java.util.function.Supplier;

/**
 * @author DustW
 */
public class ModItemSupplierBase implements Supplier<Item> {
    private final Item ITEM;

    public ModItemSupplierBase(String regName, ItemGroup group) {
        Item.Properties properties = new Item.Properties().group(group);
        this.ITEM = new ModItemBase(regName, properties);
    }

    public static class ModItemBase extends Item {
        protected ModItemBase(String regName, Properties properties) {
            super(properties);

            this.setRegistryName(new ResourceLocation(A2kn1gh7.MODID, regName));

            ModItems.ITEM_LIST.add(this);
        }
    }

    @Override
    public Item get() {
        return this.ITEM;
    }
}
