package com.nmmoc7.king_and_knight.newgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldVertexBufferUploader;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;

/**
 * @author MaxSeth
 */
@SuppressWarnings("deprecation")
public class ButtonSwitchWithTexture extends Button {

    private final int texUStart;
    private final int texVStart;
    private final int texUOffset;
    private final int texVOffset;
    private final int texWidth;
    private final int texHeight;
    private final int texOnUStart;
    private final int texOnVStart;
    private final int texOnWidth;
    private final int texOnHeight;
    private final ResourceLocation buttonOffTexture;
    private final ResourceLocation buttonOnTexture;
    private final boolean isSimpleBackground;
    private final int backgroundRed;
    private final int backgroundGreen;
    private final int backgroundBlue;

    private boolean state;
    private int toggleAnimationTimer;
    private boolean prevState;
    private boolean needRenderAnimation;

    public ButtonSwitchWithTexture(int x, int y, int width, int height,
                                   int texUStart, int texVStart, int texUOffset, int texVOffset, int texWidth, int texHeight,
                                   int texOnUStart, int texOnVStart, int texOnWidth, int texOnHeight,
                                   ITextComponent title, IPressable pressedAction,
                                   ResourceLocation buttonTexture, ResourceLocation buttonOnTexture,
                                   boolean isSimpleBackground, int backgroundRGB) {
        super(x, y, width, height, title, pressedAction);
        this.texUStart = texUStart;
        this.texVStart = texVStart;
        this.texUOffset = texUOffset;
        this.texVOffset = texVOffset;
        this.texWidth = texWidth;
        this.texHeight = texHeight;
        this.texOnUStart = texOnUStart;
        this.texOnVStart = texOnVStart;
        this.texOnWidth = texOnWidth;
        this.texOnHeight = texOnHeight;
        this.buttonOffTexture = buttonTexture;
        this.buttonOnTexture = buttonOnTexture;
        this.isSimpleBackground = isSimpleBackground;
        this.backgroundRed = backgroundRGB >> 16;
        this.backgroundGreen = backgroundRGB << 16 >>> 24;
        this.backgroundBlue = backgroundRGB << 24 >>> 24;

    }

    public ButtonSwitchWithTexture(int x, int y, int width, int height,
                                   int texXStart, int texYStart, int texWidth, int texHeight,
                                   int texOnXStart, int texOnYStart, int texUOffset, int texVOffset,
                                   ITextComponent title, IPressable pressedAction,
                                   ResourceLocation buttonTexture, ResourceLocation buttonOnTexture) {
        this(x, y, width, height, texXStart, texYStart, texWidth, texHeight,
                texUOffset, texVOffset, texOnXStart, texOnYStart, texWidth,
                texHeight, title, pressedAction, buttonTexture, buttonOnTexture,
                false, 0);

    }

    public ButtonSwitchWithTexture(int x, int y, int width, int height,
                                   int texXStart, int texYStart, int texWidth,
                                   int texHeight, int texUOffset, int texVOffset,
                                   ITextComponent title, IPressable pressedAction,
                                   ResourceLocation buttonTexture, int backgroundRGB) {
        this(x, y, width, height, texXStart, texYStart, texWidth, texHeight,
                texUOffset, texVOffset, texXStart, texYStart, texWidth,
                texHeight, title, pressedAction, buttonTexture, buttonTexture,
                true, backgroundRGB);

    }

    @Override
    public void renderWidget(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        RenderSystem.enableDepthTest();
        if (isSimpleBackground) {
            if (state) {
                if (needRenderAnimation) {
                    renderWithAnimationForSimpleBackground(matrixStack, partialTicks, false);
                } else {
                    renderSimpleBackground(matrixStack.getLast().getMatrix(), 255);
                }

            } else {
                if (needRenderAnimation) {
                    renderWithAnimationForSimpleBackground(matrixStack, partialTicks, true);
                }
            }
            mc.textureManager.bindTexture(buttonOffTexture);
            blit(matrixStack, x, y, width, height, texUStart, texVStart, texUOffset, texVOffset, texWidth, texHeight);

        } else {
            if (state) {
                mc.textureManager.bindTexture(buttonOnTexture);
                if (needRenderAnimation) {
                    renderWithAnimation(matrixStack, texOnUStart, texOnVStart, texUOffset, texVOffset, texOnWidth, texOnHeight, partialTicks, false);
                } else {
                    blit(matrixStack, x, y, width, height, texOnUStart, texOnVStart, texUOffset, texVOffset, texOnWidth, texOnHeight);
                }

            } else {
                mc.textureManager.bindTexture(buttonOffTexture);
                if (needRenderAnimation) {
                    renderWithAnimation(matrixStack, texUStart, texVStart, texUOffset, texVOffset, texWidth, texHeight, partialTicks, true);
                } else {
                    blit(matrixStack, x, y, width, height, texUStart, texVStart, texUOffset, texVOffset, texWidth, texHeight);
                }

            }
        }

        if (this.isHovered()) {
            this.renderToolTip(matrixStack, mouseX, mouseY);
        }
    }

    public void toggleState() {
        prevState = state;
        state = !state;
        needRenderAnimation = true;
        TickTimer.timing(this, 20);
    }

    public boolean getState() {
        return state;
    }

    private void renderWithAnimation(MatrixStack matrixStack, int u, int v, int uOffset, int vOffset, int texWidth, int texHeight, float partialTicks, boolean reverse) {
        RenderSystem.enableBlend();
        RenderSystem.enableAlphaTest();
        int tick = TickTimer.getTiming(this);
        float alpha = (float) (MathHelper.sin((float) ((tick + partialTicks) / 20 * 2 * Math.PI)) * 0.5 + 0.5);
        if (reverse) {
            alpha = 255 - alpha;
        }
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        RenderSystem.color4f(1, 1, 1, alpha);
        blit(matrixStack, x, y, width, height, u, v, uOffset, vOffset, texWidth, texHeight);
        RenderSystem.color4f(1, 1, 1, 1);
        RenderSystem.disableAlphaTest();
        RenderSystem.disableBlend();
        if (tick >= 20) {
            needRenderAnimation = false;
        }
    }

    private void renderWithAnimationForSimpleBackground(MatrixStack matrixStack, float partialTicks, boolean reverse) {
        RenderSystem.enableBlend();
        RenderSystem.enableAlphaTest();
        int tick = TickTimer.getTiming(this);
        double alpha = ((MathHelper.sin((float) ((tick + partialTicks) / 40 * Math.PI))) * 0.5 + 0.5) * 255;
        if (reverse) {
            alpha = 255 - alpha;
        }
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        renderSimpleBackground(matrixStack.getLast().getMatrix(), (int) alpha);
        RenderSystem.disableAlphaTest();
        RenderSystem.disableBlend();
        if (tick >= 20) {
            needRenderAnimation = false;
        }
    }

    private void renderSimpleBackground(Matrix4f matrix, int alpha) {
        int tick = TickTimer.getTiming(this);
        BufferBuilder builder = Tessellator.getInstance().getBuffer();
        builder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        builder.pos(matrix, x, y + height, 0).color(backgroundRed, backgroundGreen, backgroundBlue, alpha).endVertex();
        builder.pos(matrix, x + width, y + height, 0).color(backgroundRed, backgroundGreen, backgroundBlue, alpha).endVertex();
        builder.pos(matrix, x + width, y, 0).color(backgroundRed, backgroundGreen, backgroundBlue, alpha).endVertex();
        builder.pos(matrix, x, y, 0).color(backgroundRed, backgroundGreen, backgroundBlue, alpha).endVertex();
        builder.finishDrawing();
        WorldVertexBufferUploader.draw(builder);
        if (tick >= 20) {
            needRenderAnimation = false;
        }
    }
}
