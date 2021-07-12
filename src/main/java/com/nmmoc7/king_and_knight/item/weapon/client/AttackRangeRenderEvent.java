package com.nmmoc7.king_and_knight.item.weapon.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.nmmoc7.king_and_knight.capability.ModCapabilities;
import com.nmmoc7.king_and_knight.capability.weapon.WeaponCapability;
import com.nmmoc7.king_and_knight.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.king_and_knight.item.weapon.skills.enums.AttackRangeType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class AttackRangeRenderEvent {
    static int tick = 0;

    @SubscribeEvent
    public static void onPlayerRender(RenderPlayerEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        if (mc.isGamePaused() || mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
            return;
        }

        draw(player, event.getPartialRenderTick(), mc, event.getMatrixStack());
    }

    @SubscribeEvent
    public static void onWorldRender(RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;

        if (mc.isGamePaused() || mc.gameSettings.getPointOfView() != PointOfView.FIRST_PERSON) {
            return;
        }

        draw(player, event.getPartialTicks(), mc, event.getMatrixStack());
    }

    private static void draw(PlayerEntity player, float partialTicks, Minecraft mc, MatrixStack matrixStack) {
        Vector3d playerPos = player.getEyePosition(partialTicks).add(0, -player.getEyeHeight(player.getPose()), 0);
        BlockPos blockPos = player.getPosition();
        Vector3d projectPos = mc.getRenderManager().info.getProjectedView();

        matrixStack.push();

        /* 旋转角度 */
        matrixStack.rotate(Vector3f.YP.rotationDegrees(-player.getYaw(partialTicks)));
        matrixStack.translate(-projectPos.getX(), -projectPos.getY(), -projectPos.getZ());

        openBlendA();
        drawAttackRange(blockPos, matrixStack.getLast().getMatrix(), playerPos, player, partialTicks);
        closeBlend();
        matrixStack.pop();
    }

    /** 不 要 在 服 务 端 调 用
     *  允许材质半透明
     */
    public static void openBlendA() {
        /* 允许透明 */
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    /** 不 要 在 服 务 端 调 用 */
    public static void closeBlend() {
        GL11.glDisable(GL11.GL_BLEND);
    }

    /** 不 要 在 服 务 端 调 用 */
    public static void drawAttackRange(BlockPos blockPos, Matrix4f matrix4f, Vector3d playerPos, PlayerEntity player, float partialTicks) {
        if (player.getHeldItemMainhand().getItem() instanceof AbstractWeapon) {
            int[] color1 = {102, 102, 255};
            int[] colorC = {0, 0, 0};
            int[] color2 = {255, 153, 204};

            double color = Math.sin(2 * Math.PI / 40 * (tick + partialTicks)) / 2 + 0.5;

            int[] colorToC = {
                    (int) (color1[0] + (colorC[0] - color1[0]) * color),
                    (int) (color1[1] + (colorC[1] - color1[1]) * color),
                    (int) (color1[2] + (colorC[2] - color1[2]) * color)
            };

            int[] colorNow = {
                    (int) (colorToC[0] + (color2[0] - colorToC[0]) * color),
                    (int) (colorToC[1] + (color2[1] - colorToC[1]) * color),
                    (int) (colorToC[2] + (color2[2] - colorToC[2]) * color)
            };

            double y = getBlockMaxY(blockPos);

            double[][][][] attackRange = range(player.getHeldItemMainhand(), playerPos, 1);

            for (double[][][] blocks : attackRange) {
                for (int j = 0; j < attackRange[0].length; j++) {
                    double[][] block = blocks[j];

                    IRenderTypeBuffer.Impl buffers = Minecraft.getInstance().getRenderTypeBuffers().getBufferSource();
                    IVertexBuilder builder = buffers.getBuffer(RenderType.getLeash());

                    addVertex((float) (blockPos.getY() + y), matrix4f, builder, block[0], colorNow[0], colorNow[1], colorNow[2], 128);
                    addVertex((float) (blockPos.getY() + y), matrix4f, builder, block[1], colorNow[0], colorNow[1], colorNow[2], 128);
                    addVertex((float) (blockPos.getY() + y), matrix4f, builder, block[2], colorNow[0], colorNow[1], colorNow[2], 128);
                    addVertex((float) (blockPos.getY() + y), matrix4f, builder, block[3], colorNow[0], colorNow[1], colorNow[2], 128);

                    buffers.finish();
                }
            }
        }
    }

    /** 不 要 在 服 务 端 调 用 */
    public static double getBlockMaxY(BlockPos blockPos) {
        Minecraft mc = Minecraft.getInstance();
        PlayerEntity player = mc.player;
        World world = mc.world;
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        VoxelShape shape = block.getShape(blockState, world, blockPos, ISelectionContext.forEntity(player));

        return shape.isEmpty() ? 0D : shape.getBoundingBox().maxY;
    }

    /**
     * 增加顶点 不要在服务端调用
     * @param y 点的高度
     * @param matrix4f Matrix4f
     * @param builder buffer builder
     * @param point x和z
     * @param r 红
     * @param g 绿
     * @param b 蓝
     * @param alpha 透明度
     */
    public static void addVertex(float y, Matrix4f matrix4f, IVertexBuilder builder, double[] point, int r, int g, int b, int alpha) {
        builder.pos(matrix4f, (float) point[0], y + 1f / 256f, (float) point[1])
                .color(r, g, b, alpha)
                .lightmap(0x00F0_00F0).endVertex();
    }

    public static double[][][][] range(ItemStack weapon, Vector3d playerPos, double blockWidth) {
        WeaponCapability weaponCap = weapon.getCapability(ModCapabilities.WEAPON_CAPABILITY).orElse(null);

        AttackRangeType[][] attackRange = weaponCap.getActiveAttackRange();
        int[] core = weaponCap.getAttackRangeCore();

        int width = attackRange.length;
        int height = attackRange[0].length;

        double[][][][] result = new double[width][height][4][4];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (attackRange[i][j] == AttackRangeType.Y) {
                    result[i][j] = new double[][]{
                            {playerPos.x + i - core[0] + blockWidth / 2f, playerPos.z + j - core[1] + blockWidth / 2f},
                            {playerPos.x + i - core[0] + blockWidth / 2f, playerPos.z + j - core[1] - blockWidth / 2f},
                            {playerPos.x + i - core[0] - blockWidth / 2f, playerPos.z + j - core[1] - blockWidth / 2f},
                            {playerPos.x + i - core[0] - blockWidth / 2f, playerPos.z + j - core[1] + blockWidth / 2f}};
                }
            }
        }

        return result;
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        tick++;
    }
}
