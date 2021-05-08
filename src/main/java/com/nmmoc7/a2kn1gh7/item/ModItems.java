package com.nmmoc7.a2kn1gh7.item;

import com.nmmoc7.a2kn1gh7.item.base.ModItemSupplierBase;
import com.nmmoc7.a2kn1gh7.itemgroup.ModItemGroups;
import net.minecraft.item.Item;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ModItems {
    public static final ArrayList<Item> ITEM_LIST = new ArrayList<>();

    public static final ModItemSupplierBase SOURCE_ROCKS = new ModItemSupplierBase("source_rocks", ModItemGroups.ITEM_GROUP);
    public static final ModItemSupplierBase HIGH_PURE_SOURCE_ROCKS = new ModItemSupplierBase("high_pure_source_rocks", ModItemGroups.ITEM_GROUP);
    public static final ModItemSupplierBase CRAFTING_JADE = new ModItemSupplierBase("crafting_jade", ModItemGroups.ITEM_GROUP);

    /** T1 */
    public static final ModItemSupplierBase SOURCE_ROCK = new ModItemSupplierBase("source_rock", ModItemGroups.ITEM_GROUP);
    public static final ModItemSupplierBase DAMAGED_DEVICE = new ModItemSupplierBase("damaged_device", ModItemGroups.ITEM_GROUP);
    public static final ModItemSupplierBase ESTER = new ModItemSupplierBase("ester", ModItemGroups.ITEM_GROUP);
    public static final ModItemSupplierBase W_KETONE = new ModItemSupplierBase("w_ketone", ModItemGroups.ITEM_GROUP);
    public static final ModItemSupplierBase ORIRON_SHARD = new ModItemSupplierBase("oriron_shard", ModItemGroups.ITEM_GROUP);
    public static final ModItemSupplierBase SUGAR_SUBSTITUTE = new ModItemSupplierBase("sugar_substitute", ModItemGroups.ITEM_GROUP);
}
