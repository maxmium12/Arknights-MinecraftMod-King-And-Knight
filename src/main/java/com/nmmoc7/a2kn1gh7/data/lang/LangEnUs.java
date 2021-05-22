package com.nmmoc7.a2kn1gh7.data.lang;

import com.nmmoc7.a2kn1gh7.block.ModBlocks;
import com.nmmoc7.a2kn1gh7.item.ModItems;
import com.nmmoc7.a2kn1gh7.itemgroup.ModItemGroups;
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
    }

    private void addGroups() {
        addItemGroup(ModItemGroups.ITEM_GROUP.getPath(), "A2kn1gh7 Items");
        addItemGroup(ModItemGroups.BLOCK_GROUP.getPath(), "A2kn1gh7 Blocks");
    }

    private void addBlocks() {
        addBlock(ModBlocks.SOURCE_ROCKS_BLOCK, "Compress Originium Block");
    }
}
