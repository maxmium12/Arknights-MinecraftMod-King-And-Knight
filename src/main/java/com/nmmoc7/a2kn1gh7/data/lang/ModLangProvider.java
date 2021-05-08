package com.nmmoc7.a2kn1gh7.data.lang;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * @author NmmOC7
 */
public abstract class ModLangProvider extends LanguageProvider {
     public ModLangProvider(DataGenerator gen, String locale) {
         super(gen, A2kn1gh7.MODID, locale);
     }

     public void addAdvancement(String id, String title, String description) {
         add("advancement.a2kn1gh7." + id + ".title", title);
         add("advancement.a2kn1gh7." + id + ".description", description);
     }

     public void addItemGroup(String id, String name) {
            add("itemGroup.a2kn1gh7." + id, name);
     }
}
