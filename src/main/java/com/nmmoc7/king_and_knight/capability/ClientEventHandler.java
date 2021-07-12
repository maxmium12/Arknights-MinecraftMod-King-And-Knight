package com.nmmoc7.king_and_knight.capability;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.capability.player.level.LevelCapability;
import com.nmmoc7.king_and_knight.item.weapon.abstracts.AbstractWeapon;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT)
@OnlyIn(Dist.CLIENT)
public class ClientEventHandler {
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.getRenderViewEntity();

            int x = event.getWindow().getScaledWidth();
            int y = event.getWindow().getScaledHeight();

            if (entity instanceof PlayerEntity) {
                if (!((PlayerEntity) entity).getHeldItemMainhand().getItem().getRegistryName().getNamespace().equals(KingAndKnight.MOD_ID)) {
                    return;
                }

                LazyOptional<LevelCapability> capability = entity.getCapability(ModCapabilities.LEVEL_CAPABILITY, null);

                capability.ifPresent(theCap -> {
                    int maxExperience = theCap.getMaxExperience();
                    int experience = theCap.getExperience();
                    int level = theCap.getLevel();

                    drawString(event.getMatrixStack(), 15, 15, 0xFFFFFFFF, "人物等级: " + level);
                    drawString(event.getMatrixStack(), 15,  25, 0xFFFFFFFF, "人物经验: " + experience + "/" + maxExperience);
                });

                entity.getCapability(ModCapabilities.REASON_CAPABILITY).ifPresent(theCap -> {
                    int reason = theCap.reason;

                    drawString(event.getMatrixStack(), 15, 45, 0xFFFFFFFF, "剩余理智: " + reason + " / " + theCap.getMaxReason(mc.player));
                });

                entity.getCapability(ModCapabilities.COST_CAPABILITY).ifPresent(theCap -> {
                    int cost = theCap.cost;

                    drawString(event.getMatrixStack(), 15, 65, 0xFFFFFFFF, "剩余cost: " + cost + " / " + theCap.MAX_COST);
                });

                if (((PlayerEntity) entity).getHeldItemMainhand().getItem() instanceof AbstractWeapon) {
                    ItemStack mainHandItem = ((PlayerEntity) entity).getHeldItemMainhand();

                    drawString(event.getMatrixStack(), x - 100, y - 60, 0xFFFFFFFF, mainHandItem.getDisplayName().getString());

                    mainHandItem.getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> {
                        drawString(event.getMatrixStack(), x - 100, y - 45, 0xFFFFFFFF, theCap.getActiveSkill().getDisplayName().getString());
                        drawString(event.getMatrixStack(), x - 100, y - 30, 0xFFFFFFFF,  "技力: " + theCap.getActiveSkill().getSkillPoint() + "/" + theCap.getActiveSkill().getMaxSkillPoint());
                        drawString(event.getMatrixStack(), x - 100, y - 15, 0xFFFFFFFF,  "技能持续时间: " + (theCap.getActiveSkill().getDurationTime()) + "/" + theCap.getActiveSkill().getMaxDurationTime());
                    });
                }
            }
        }
    }

    static void drawString(MatrixStack matrixStack, int x, int y, int color, String string) {
        Minecraft.getInstance().getRenderManager().getFontRenderer().drawString(matrixStack, string, x, y, color);
    }
}
