package com.nmmoc7.a2kn1gh7.network;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class NetworkRegistryHandler {
    @SubscribeEvent
    public static void networkRegistryEvent(FMLCommonSetupEvent event) {
        ModNetworkManager.registerPackets();
    }
}
