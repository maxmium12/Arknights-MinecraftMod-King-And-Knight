package com.nmmoc7.kingandkinght.machines.tileentity;

import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TileEntityRegistryHandler {
    @SubscribeEvent
    public static void onTileEntityTypeRegister(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(ModTileEntities.TILE_ENTITY_TYPES.toArray(new TileEntityType[]{}));
    }

    @SubscribeEvent
    public static void onContainerTypeRegister(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(ModContainers.PROCESSING_STATION_CONTAINER_TYPE);
    }
}
