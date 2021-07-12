package com.nmmoc7.king_and_knight.item.weapon.client;

import com.nmmoc7.king_and_knight.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.king_and_knight.network.ModNetworkManager;
import com.nmmoc7.king_and_knight.network.client.LeftClickClient;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;


/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class LeftClickEvent {
    @SubscribeEvent
    public static void onMouseLeftClick(InputEvent.MouseInputEvent event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.currentScreen == null && event.getButton() == GLFW.GLFW_MOUSE_BUTTON_LEFT && event.getAction() == GLFW.GLFW_PRESS) {
            if (mc.player.getHeldItemMainhand().getItem() instanceof AbstractWeapon) {
                ModNetworkManager.clientSendToServer(new LeftClickClient());
            }
        }
    }
}
