package com.nmmoc7.king_and_knight.newgui;

import com.nmmoc7.king_and_knight.KingAndKnight;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * @author MaxSeth
 */
public class ContainerRegister {
    public static final DeferredRegister<ContainerType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, KingAndKnight.MOD_ID);
    public static final RegistryObject<ContainerType<ContainerWeapon>> WEAPON_UPGRADER_CONTAINER = CONTAINERS.register("weapon_upgrader_container",
            ()-> IForgeContainerType.create(
                    (int windowId, PlayerInventory inv, PacketBuffer data)-> new ContainerWeapon(windowId, inv, Minecraft.getInstance().world, data.readBlockPos()))
            );
}
