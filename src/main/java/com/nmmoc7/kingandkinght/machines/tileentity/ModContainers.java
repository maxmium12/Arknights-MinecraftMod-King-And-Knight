package com.nmmoc7.kingandkinght.machines.tileentity;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.machines.tileentity.container.ProcessingStationContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeContainerType;

/**
 * @author DustW
 */
public class ModContainers {
    public static final ContainerType<ProcessingStationContainer> PROCESSING_STATION_CONTAINER_TYPE =
            IForgeContainerType.create((int windowId, PlayerInventory inv, PacketBuffer data) ->
                    new ProcessingStationContainer(windowId, inv, data.readBlockPos(), Minecraft.getInstance().world));

    static {
        PROCESSING_STATION_CONTAINER_TYPE.setRegistryName(new ResourceLocation(KingAndKnight.MOD_ID, "processing_station_container"));
    }
}
