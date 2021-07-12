package com.nmmoc7.king_and_knight.data.lang;

import com.nmmoc7.king_and_knight.block.ModBlocks;
import com.nmmoc7.king_and_knight.item.ModItems;
import com.nmmoc7.king_and_knight.item.ModWeapons;
import com.nmmoc7.king_and_knight.itemgroup.ModItemGroups;
import net.minecraft.data.DataGenerator;

/**
 * @author DustW
 */
public class LangEnUs extends ModLangProvider {
    public LangEnUs(DataGenerator gen) {
        super(gen, "en_us");
    }

    @Override
    protected void addTranslations() {
        addItems();
        addGroups();
        addBlocks();
    }

    private void addItems() {
        addItem(ModItems.SOURCE_ROCKS, "Originium");
        addItem(ModItems.CRAFTING_JADE, "Orundum");
        addItem(ModItems.HIGH_PURE_SOURCE_ROCKS, "Originite Prime");

        addItem(ModItems.SOURCE_ROCK, "Orirock");
        addItem(ModItems.DAMAGED_DEVICE, "Damaged Device");
        addItem(ModItems.ESTER, "Ester");
        addItem(ModItems.ORIRON_SHARD, "Oriron Shard");
        addItem(ModItems.W_KETONE, "Diketon");
        addItem(ModItems.SUGAR_SUBSTITUTE, "Sugar Substitute");

        addItem(ModWeapons.IRIS_WEAPON, "Iris");
    }

    private void addGroups() {
        addItemGroup(ModItemGroups.ITEM_GROUP.getPath(), "King and Knight  Items");
        addItemGroup(ModItemGroups.BLOCK_GROUP.getPath(), "King and Knight  Blocks");
        addItemGroup(ModItemGroups.MACHINE_GROUP.getPath(), "King and Knight  Machine");
        addItemGroup(ModItemGroups.WEAPON_GROUP.getPath(), "King and Knight  Weapon");
    }

    private void addBlocks() {
        addBlock(ModBlocks.SOURCE_ROCKS_BLOCK, "Compress Originium Block");
        addBlock(ModBlocks.BUILDING_MATERIAL, "Building Material");
    }
}
