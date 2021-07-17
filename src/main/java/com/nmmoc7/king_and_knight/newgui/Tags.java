package com.nmmoc7.king_and_knight.newgui;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MaxSeth
 */
public class Tags {

    private static final Map<String, Tag> TAGS = new LinkedHashMap<>();

    public static void register(Tag tag) {
        TAGS.put(tag.getId(), tag);
    }

    public static Collection<Tag> getTags() {
        return TAGS.values();
    }
}
