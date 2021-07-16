package com.nmmoc7.king_and_knight.newgui;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author MaxSeth
 */
public class Tags {

    private static final Map<String, Tag> TAGS = new Object2ObjectOpenHashMap<>();

    public static void register(Tag tag) {
        TAGS.put(tag.getId(), tag);
    }

    public static Collection<Tag> getTags() {
        return TAGS.values();
    }
}
