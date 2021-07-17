package com.nmmoc7.king_and_knight.newgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.network.ModNetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

/**
 * @author MaxSeth
 */
public class ScreenWeapon extends ContainerScreen<ContainerWeapon> {
    public ScreenWeapon(ContainerWeapon screenContainer, PlayerInventory inv, ITextComponent title) {
        super(screenContainer, inv, title);
        TickTimer.timing(this, 400);
    }

    @Override
    protected void init() {
        super.init();
        addButton(new Button(104, 58, 47, 16,
                new TranslationTextComponent("king_and_knights.weapon_upgrade.confirm"),
                button -> ModNetworkManager.clientSendToServer(new PacketWeaponUpgrade())));
        addButton(new ImageButton(17, 10, 47, 65, 0, 0, 0,
                new ResourceLocation(KingAndKnight.MOD_ID, "textures/gui/empty.png"),
                button -> {
                   handleMouseClick(container.getSlot(0), 0,0, ClickType.SWAP);
                }));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        renderBackground(matrixStack);
        int i = this.guiLeft;
        int j = this.guiTop;
        minecraft.textureManager.bindTexture(new ResourceLocation(KingAndKnight.MOD_ID, "textures/gui/weapon_upgrader.png"));
        blit(matrixStack, i, j, 0, 0, xSize, ySize);
        drawWeapon(matrixStack, partialTicks);
    }

    private void drawWeapon(MatrixStack matrixStack, float partialTicks){
        matrixStack.push();
        matrixStack.rotate(Vector3f.YP.rotationDegrees((float) ((TickTimer.getTiming(this) + partialTicks) / 400 * 2 * Math.PI)));
        matrixStack.scale(3,3,3);
        IRenderTypeBuffer.Impl renderTypeBuffer = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
        minecraft.getItemRenderer().renderItem(container.getSlot(0).getStack(), ItemCameraTransforms.TransformType.GROUND, 15728880, OverlayTexture.NO_OVERLAY, matrixStack, renderTypeBuffer);
        matrixStack.pop();
    }
}
