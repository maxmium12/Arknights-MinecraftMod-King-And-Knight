package com.nmmoc7.king_and_knight.data.lang;

import com.nmmoc7.king_and_knight.KingAndKnight;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * @author NmmOC7
 */
public abstract class ModLangProvider extends LanguageProvider {
     public ModLangProvider(DataGenerator gen, String locale) {
         super(gen, KingAndKnight.MOD_ID, locale);
     }

     public void addAdvancement(String id, String title, String description) {
         add("advancement." + KingAndKnight.MOD_ID + "." + id + ".title", title);
         add("advancement." + KingAndKnight.MOD_ID + "." + id + ".description", description);
     }

     public void addItemGroup(String id, String name) {
            add("itemGroup." + id, name);
     }

    public void addItem(Item key, String name) {
        super.addItem(() -> key, name);
    }

    public void addBlock(Block key, String name) {
         super.addBlock(() -> key, name);
    }
}
