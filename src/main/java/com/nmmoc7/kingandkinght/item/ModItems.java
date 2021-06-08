package com.nmmoc7.kingandkinght.item;

import com.nmmoc7.kingandkinght.item.base.ModItemBase;
import com.nmmoc7.kingandkinght.itemgroup.ModItemGroups;
import net.minecraft.item.Item;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ModItems {
    public static final ArrayList<Item> ITEM_LIST = new ArrayList<>();

    public static final ModItemBase SOURCE_ROCKS = new ModItemBase("source_rocks", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase HIGH_PURE_SOURCE_ROCKS = new ModItemBase("high_pure_source_rocks", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase CRAFTING_JADE = new ModItemBase("crafting_jade", ModItemGroups.ITEM_GROUP);

    /** T1 */
    public static final ModItemBase SOURCE_ROCK = new ModItemBase("source_rock", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase DAMAGED_DEVICE = new ModItemBase("damaged_device", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase ESTER = new ModItemBase("ester", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase W_KETONE = new ModItemBase("w_ketone", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase ORIRON_SHARD = new ModItemBase("oriron_shard", ModItemGroups.ITEM_GROUP);
    public static final ModItemBase SUGAR_SUBSTITUTE = new ModItemBase("sugar_substitute", ModItemGroups.ITEM_GROUP);
}
