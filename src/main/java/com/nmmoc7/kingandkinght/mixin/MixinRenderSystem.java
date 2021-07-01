package com.nmmoc7.kingandkinght.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import com.nmmoc7.kingandkinght.KingAndKnight;
import net.minecraft.client.GameConfiguration;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ScreenSize;
import net.minecraft.client.renderer.VirtualScreen;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.LongSupplier;

/**
 * @author DustW
 */
@Mixin(RenderSystem.class)
public class MixinRenderSystem {
    @Inject(method = "initBackendSystem", at = @At(value = "HEAD"))
    private static void initBackendSystemKAK(CallbackInfoReturnable<LongSupplier> cir) {
        KingAndKnight.LOGGER.error("！！！！！骑士的注入！！！！！");
        GLFW.glfwWindowHint(GLFW.GLFW_SAMPLES, 4);
    }
}