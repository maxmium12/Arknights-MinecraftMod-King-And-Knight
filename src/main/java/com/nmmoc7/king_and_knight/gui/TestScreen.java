package com.nmmoc7.king_and_knight.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nmmoc7.king_and_knight.KingAndKnight;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TestScreen extends ContainerScreen<TestContainer> {
    private final ResourceLocation OBSIDIAN_CONTAINER_RESOURCE = new ResourceLocation(KingAndKnight.MOD_ID, "textures/gui/container.png");
    private final int textureWidth = 176;
    private final int textureHeight = 166;

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    public TestScreen(TestContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = textureWidth;
        this.ySize = textureHeight;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        drawCenteredString(matrixStack, this.font, Integer.toString(this.getContainer().getIntArray().get(0)), 82, 20, 0xeb0505);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bindTexture(OBSIDIAN_CONTAINER_RESOURCE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(matrixStack, i, j, 0, 0, xSize, ySize, this.textureWidth, textureHeight);
    }
}
