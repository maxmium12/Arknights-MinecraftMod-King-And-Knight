package com.nmmoc7.king_and_knight.tileentity;

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
}
