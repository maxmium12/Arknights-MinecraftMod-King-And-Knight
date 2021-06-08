package com.nmmoc7.kingandkinght.machines.tileentity.container;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nmmoc7.kingandkinght.KingAndKnight;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author DustW
 */
public class ProcessingStationContainerScreen extends ContainerScreen<ProcessingStationContainer> {
    private final ResourceLocation PROCESSING_STATION_CONTAINER_RESOURCE = new ResourceLocation(KingAndKnight.MOD_ID, "textures/gui/container1.png");
    private final int textureWidth = 176;
    private final int textureHeight = 166;

    @Override
    public void render(@NotNull MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, partialTicks);

        renderHoveredTooltip(matrixStack, mouseX, mouseY);
    }

    public ProcessingStationContainerScreen(ProcessingStationContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.xSize = textureWidth;
        this.ySize = textureHeight;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(@NotNull MatrixStack matrixStack, int x, int y) {
        drawCenteredString(matrixStack, this.font, "dame dane", 82, 20, 0xeb0505);
    }

    protected void drawSlot(MatrixStack matrixStack, ResourceLocation type, int x, int y) {
        this.getMinecraft().getTextureManager().bindTexture(type);
        blit(matrixStack, (width - xSize) / 2 + x - 1, (height - ySize) / 2 + y - 1, 0, 0, 18, 18, 18, 18);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(@NotNull MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.renderBackground(matrixStack);
        this.minecraft.getTextureManager().bindTexture(PROCESSING_STATION_CONTAINER_RESOURCE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        blit(matrixStack, i, j, 0, 0, xSize, ySize, this.textureWidth, textureHeight);

        drawSlot(matrixStack, ScreenParts.SLOT_1, 18, 18);
        drawSlot(matrixStack, ScreenParts.SLOT_1, 38, 18);
        drawSlot(matrixStack, ScreenParts.SLOT_1, 58, 18);
        drawSlot(matrixStack, ScreenParts.SLOT_1, 18, 38);
        drawSlot(matrixStack, ScreenParts.SLOT_1, 38, 38);

        drawSlot(matrixStack, ScreenParts.SLOT_1, 58, 58);
    }
}
