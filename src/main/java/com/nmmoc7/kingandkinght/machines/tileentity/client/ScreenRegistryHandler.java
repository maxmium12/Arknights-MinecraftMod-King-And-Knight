package com.nmmoc7.kingandkinght.machines.tileentity.client;

import com.nmmoc7.kingandkinght.machines.tileentity.ModContainers;
import com.nmmoc7.kingandkinght.machines.tileentity.container.ProcessingStationContainerScreen;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ScreenRegistryHandler {
    @SubscribeEvent
    public static void onClientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ScreenManager.registerFactory(ModContainers.PROCESSING_STATION_CONTAINER_TYPE, ProcessingStationContainerScreen::new);
        });
    }
}
