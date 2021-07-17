package com.nmmoc7.king_and_knight.newgui;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

/**
 * @author MaxSeth
 */
public class Tag {

    private final String id;
    private final ITextComponent title;
    private final ResourceLocation tagOffTexture;
    private final ResourceLocation tagOnTexture;
    private final int texU;
    private final int texV;
    private final int texWidth;
    private final int texHeight;

    public Tag(String id, ITextComponent title, ResourceLocation tagOffTexture, ResourceLocation tagOnTexture,
               int texU, int texV, int texWidth, int texHeight) {

        this.id = id;
        this.title = title;
        this.tagOffTexture = tagOffTexture;
        this.tagOnTexture = tagOnTexture;
        this.texU = texU;
        this.texV = texV;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
    }

    public int getTexHeight() {
        return texHeight;
    }

    public int getTexU() {
        return texU;
    }

    public int getTexV() {
        return texV;
    }

    public int getTexWidth() {
        return texWidth;
    }

    public ResourceLocation getTagOffTexture() {
        return tagOffTexture;
    }

    public ResourceLocation getTagOnTexture() {
        return tagOnTexture;
    }

    public String getId() {
        return id;
    }

    public ITextComponent getTitle() {
        return title;
    }
}
