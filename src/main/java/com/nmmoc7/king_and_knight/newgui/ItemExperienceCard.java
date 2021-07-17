package com.nmmoc7.king_and_knight.newgui;

import net.minecraft.item.Item;

/**
 * @author MaxSeth
 */
public class ItemExperienceCard extends Item {
    private final int level;

    public ItemExperienceCard(Properties properties, int level) {
        super(new Properties());
        this.level = level;
    }

    public int getLevel() {
        return level;
    }
}
