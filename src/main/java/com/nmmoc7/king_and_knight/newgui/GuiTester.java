package com.nmmoc7.king_and_knight.newgui;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author MaxSeth
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)

public class GuiTester {


    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(EventHandler.TEST_KEY);
        Tags.register(new Tag("test", new StringTextComponent("test"), new ResourceLocation("king_and_knight", "textures/gui/test.png"), null, 0, 0, 32, 32, 1));
        Tags.register(new Tag("test1", new StringTextComponent("test1"), new ResourceLocation("king_and_knight", "textures/gui/test.png"), null, 0, 0, 32, 32, 1));
        Tags.register(new Tag("test2", new StringTextComponent("test2"), new ResourceLocation("king_and_knight", "textures/gui/test.png"), null, 0, 0, 32, 32, 1));
        Tags.register(new Tag("test3", new StringTextComponent("test3"), new ResourceLocation("king_and_knight", "textures/gui/test.png"), null, 0, 0, 32, 32, 1));

    }
}
