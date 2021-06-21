package com.nmmoc7.kingandkinght.mixin;

import com.nmmoc7.kingandkinght.KingAndKnight;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author DustW
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "<init>", at = @At("RETURN"))
    private static void init(GameConfiguration gameConfig, CallbackInfo ci) {
        KingAndKnight.LOGGER.info("！！！！！骑士的注入！！！！！");
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
    }
}
