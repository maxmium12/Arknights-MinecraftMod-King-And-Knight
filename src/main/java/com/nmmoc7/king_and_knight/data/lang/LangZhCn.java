package com.nmmoc7.king_and_knight.data.lang;

import com.nmmoc7.king_and_knight.block.ModBlocks;
import com.nmmoc7.king_and_knight.item.ModItems;
import com.nmmoc7.king_and_knight.item.ModWeapons;
import com.nmmoc7.king_and_knight.itemgroup.ModItemGroups;
import net.minecraft.data.DataGenerator;

/**
 * @author NmmOC7
 */
public class LangZhCn extends ModLangProvider{
    public LangZhCn(DataGenerator gen) {
        super(gen, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        addItems();
        addSkills();
        addSkillDocs();
        addGroups();
        addBlocks();
        addCircles();
    }

    private void addSkillDocs() {

    }

    private void addSkills() {

    }

    private void addItems() {
        addItem(ModItems.SOURCE_ROCKS, "源石");
        addItem(ModItems.CRAFTING_JADE, "合成玉");
        addItem(ModItems.HIGH_PURE_SOURCE_ROCKS, "至纯源石");

        addItem(ModItems.SOURCE_ROCK, "源岩");
        addItem(ModItems.DAMAGED_DEVICE, "破损装置");
        addItem(ModItems.ESTER, "酯原料");
        addItem(ModItems.ORIRON_SHARD, "异铁碎片");
        addItem(ModItems.W_KETONE, "双酮");
        addItem(ModItems.SUGAR_SUBSTITUTE, "代糖");

        addItem(ModWeapons.IRIS_WEAPON, "爱丽丝");
    }

    private void addGroups() {
        addItemGroup(ModItemGroups.ITEM_GROUP.getPath(), "王与骑士  物品");
        addItemGroup(ModItemGroups.BLOCK_GROUP.getPath(), "王与骑士  方块");
        addItemGroup(ModItemGroups.MACHINE_GROUP.getPath(), "王与骑士  机器");
        addItemGroup(ModItemGroups.WEAPON_GROUP.getPath(), "王与骑士  武器");
    }

    private void addBlocks() {
        addBlock(ModBlocks.SOURCE_ROCKS_BLOCK, "压缩源石块");
        addBlock(ModBlocks.BUILDING_MATERIAL, "建筑材料");
    }

    private void addCircles() {

    }
}
