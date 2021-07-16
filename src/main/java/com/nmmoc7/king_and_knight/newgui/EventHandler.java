package com.nmmoc7.king_and_knight.newgui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import javax.swing.text.JTextComponent;

/**
 * @author MaxSeth
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {

    public static final KeyBinding TEST_KEY = new KeyBinding("test", KeyConflictContext.IN_GAME, KeyModifier.CONTROL, InputMappings.Type.KEYSYM, GLFW.GLFW_KEY_PAGE_UP, "key.category.test");

    public static int tickTimer;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            tickTimer++;

            TickTimer.tick();
        }
    }

    @SubscribeEvent
    public static void onInputEvent(InputEvent.KeyInputEvent event) {
        if (TEST_KEY.isPressed()) {
            assert Minecraft.getInstance().player != null;
            Minecraft.getInstance().displayGuiScreen(new GuiTags(new StringTextComponent("Tags Test")));
        }
    }
}
