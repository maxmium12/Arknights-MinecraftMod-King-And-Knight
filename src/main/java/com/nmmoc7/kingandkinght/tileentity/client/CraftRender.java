package com.nmmoc7.kingandkinght.tileentity.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nmmoc7.kingandkinght.ScreenParts;
import com.nmmoc7.kingandkinght.tileentity.InfrastructureTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class CraftRender {
    @SubscribeEvent
    public static void onRender(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.getRenderViewEntity();

            if (entity instanceof PlayerEntity) {
                RayTraceResult result = rayTrace(entity, mc.playerController.getBlockReachDistance(), 0);

                if (result.getType() == RayTraceResult.Type.BLOCK) {
                    BlockRayTraceResult block = (BlockRayTraceResult) result;
                    TileEntity te = entity.world.getTileEntity(block.getPos());

                    if (te instanceof InfrastructureTileEntity) {
                        InfrastructureTileEntity tileEntity = (InfrastructureTileEntity) te;

                        int x = event.getWindow().getScaledWidth();
                        int y = event.getWindow().getScaledHeight();

                        List<ItemStack> items = tileEntity.getHandler().getStacks();

                        for (int i = 0; i < tileEntity.getHandler().getSlots(); i++) {
                            int stackX = x / 2 - 92 + 23 * i;
                            int stackY = y / 2 + 30;

                            renderItem(items.get(i), stackX, stackY);
                            renderSlot(event.getMatrixStack(), stackX - 1, stackY - 1);
                        }

                        int resultX = x / 2 + 69;
                        int resultY = y / 2 + 30;

                        renderItem(tileEntity.getHandler().getResult(), resultX, resultY);
                        renderSlot(event.getMatrixStack(), resultX - 1, resultY - 1);
                    }
                }
            }
        }
    }

    public static void renderItem(ItemStack stack, int x, int y) {
        Minecraft.getInstance().getItemRenderer().renderItemAndEffectIntoGUI(stack, x, y);
    }

    public static void renderSlot(MatrixStack matrixStack, int x, int y) {
        Minecraft.getInstance().getTextureManager().bindTexture(ScreenParts.SLOT_1);
        AbstractGui.blit(matrixStack, x, y, 18f, 18f, 18, 18, 18, 18);
    }

    public static RayTraceResult rayTrace(Entity entity, double playerReach, float partialTicks) {
        Vector3d eyePosition = entity.getEyePosition(partialTicks);
        Vector3d lookVector = entity.getLook(partialTicks);
        Vector3d traceEnd = eyePosition.add(lookVector.x * playerReach, lookVector.y * playerReach, lookVector.z * playerReach);

        RayTraceContext context = new RayTraceContext(eyePosition, traceEnd, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, entity);
        return entity.getEntityWorld().rayTraceBlocks(context);
    }
}
