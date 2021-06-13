package com.nmmoc7.kingandkinght.item.weapon.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.sun.javafx.geom.Matrix3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.*;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;

import javax.swing.*;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class AttackRangeRenderEvent {
    static int tick = 0;

    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;
        Vector3d playerPos = player.getEyePosition(event.getPartialTicks()).add(0, -player.getEyeHeight(player.getPose()), 0);

        MatrixStack transform = event.getMatrixStack();

        transform.push();

        Vector3d projectPos = mc.getRenderManager().info.getProjectedView();
        transform.translate(-projectPos.getX(), -projectPos.getY(), -projectPos.getZ());
        Matrix4f x = transform.getLast().getMatrix();
        IRenderTypeBuffer.Impl buffers = mc.getRenderTypeBuffers().getBufferSource();
        IVertexBuilder builder = buffers.getBuffer(RenderType.getLeash());

        new Matrix3f();

        builder.pos(x, (float) playerPos.x - 0.5f, (float) playerPos.y + 1f / 256f, (float) playerPos.z - 0.5f)
                .color(1, 1, 1, 128)
                .lightmap(0x00F0_00F0).endVertex();

        builder.pos(x, (float) playerPos.x - 0.5f, (float) playerPos.y + 1f / 256f, (float) playerPos.z + 0.5f)
                .color(1, 1, 1, 128)
                .lightmap(0x00F0_00F0).endVertex();

        builder.pos(x, (float) playerPos.x + 0.5f, (float) playerPos.y + 1f / 256f, (float) playerPos.z + 0.5f)
                .color(1, 1, 1, 128)
                .lightmap(0x00F0_00F0).endVertex();

        builder.pos(x, (float) playerPos.x + 0.5f, (float) playerPos.y + 1f / 256f, (float) playerPos.z - 0.5f)
                .color(1, 1, 1, 128)
                .lightmap(0x00F0_00F0).endVertex();

        transform.pop();

        buffers.finish();
    }

//    @SubscribeEvent
//    public static void onWorldRender(RenderWorldLastEvent event) {
//        Minecraft mc = Minecraft.getInstance();
//        MatrixStack transforms = event.getMatrixStack();
//        Vector3d projVec = mc.getRenderManager().info.getProjectedView();
//        Vector3d target = mc.player.getPositionVec();
//
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuffer();
//
//        buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);
//
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//        RenderSystem.disableTexture();
//
//        transforms.push();
//
//        buffer.pos(target.x + 0.5, target.y - 0.5, target.z + 0.2).color(1, 1, 1, 128).endVertex();
//        buffer.pos(target.x + 0.5, target.y + 0.5, target.z + 0.2).color(1, 1, 1, 128).endVertex();
//        buffer.pos(target.x - 0.5, target.y + 0.5, target.z + 0.2).color(1, 1, 1, 128).endVertex();
//        buffer.pos(target.x - 0.5, target.y - 0.5, target.z + 0.2).color(1, 1, 1, 128).endVertex();
//
//        tessellator.draw();
//
//        RenderSystem.disableBlend();
//
//        transforms.pop();
//    }

//    @SubscribeEvent
//    public static void onPlayerRender(RenderPlayerEvent.Post event) {
//        Tessellator tessellator = Tessellator.getInstance();
//        BufferBuilder buffer = tessellator.getBuffer();
//        Minecraft mc = Minecraft.getInstance();
//        ClientPlayerEntity player = mc.player;
//        Vector3d pos = player.getPositionVec();
//
//        buffer.begin(GL11.GL_POLYGON, DefaultVertexFormats.POSITION_COLOR);
//
//        double color = (MathHelper.sin((float) (2 * Math.PI / 40 * (tick + event.getPartialRenderTick()))) / 2 + 0.5) * 255;
//
//        RenderSystem.enableBlend();
//        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
//
//        buffer.pos(pos.x + 0.5, pos.y - 0.5, pos.z + 0.2).color(1, 1, 1, 128).endVertex();
//        buffer.pos(pos.x + 0.5, pos.y + 0.5, pos.z + 0.2).color(1, 1, 1, 128).endVertex();
//        buffer.pos(pos.x - 0.5, pos.y + 0.5, pos.z + 0.2).color(1, 1, 1, 128).endVertex();
//        buffer.pos(pos.x - 0.5, pos.y - 0.5, pos.z + 0.2).color(1, 1, 1, 128).endVertex();
//        tessellator.draw();
//
//        RenderSystem.disableBlend();
//    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        tick++;
    }
}
