package com.nmmoc7.kingandkinght.capability;

import com.nmmoc7.kingandkinght.capability.player.cost.CostCapability;
import com.nmmoc7.kingandkinght.capability.player.level.LevelCapability;
import com.nmmoc7.kingandkinght.capability.player.reason.ReasonCapability;
import com.nmmoc7.kingandkinght.capability.weapon.WeaponCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * @author DustW
 */

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistryHandler {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            capabilityRegisterAll(
                    CostCapability.class,
                    ReasonCapability.class,
                    WeaponCapability.class,
                    LevelCapability.class
            );

            ModCapabilities.playerCapabilitiesInit();
        });

    }

    private static <T> Capability.IStorage<T> defaultStorage() {
        return new Capability.IStorage<T>() {
            @org.jetbrains.annotations.Nullable
            @Override
            public INBT writeNBT(Capability<T> capability, T instance, Direction side) {
                return null;
            }

            @Override
            public void readNBT(Capability<T> capability, T instance, Direction side, INBT nbt) {

            }
        };
    }


    private static void capabilityRegisterAll(Class<?>... capabilities) {
        for (Class capability : capabilities) {
            CapabilityManager.INSTANCE.register(capability, defaultStorage(), () -> null);
        }
    }
}
