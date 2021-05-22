package com.nmmoc7.a2kn1gh7.capability;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.math.BigInteger;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class LevelCapabilityEventHandler {
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            Minecraft mc = Minecraft.getInstance();
            Entity entity = mc.getRenderViewEntity();

            int x = event.getWindow().getScaledWidth();
            int y = event.getWindow().getScaledHeight();

            if (entity instanceof PlayerEntity) {
                LazyOptional<LevelCapability> capability = entity.getCapability(ModCapabilities.LEVEL_CAPABILITY, null);

                int level = capability.orElse(null).getLevel();

                mc.getRenderManager().getFontRenderer().drawString(event.getMatrixStack(), "人物等级: " + level, x + 15, y + 15, 0xFFFFFFFF);
                mc.getRenderManager().getFontRenderer().drawString(event.getMatrixStack(), "人物经验: ", x + 15, y + 25, 0xFFFFFFFF);
            }
        }
    }
}
