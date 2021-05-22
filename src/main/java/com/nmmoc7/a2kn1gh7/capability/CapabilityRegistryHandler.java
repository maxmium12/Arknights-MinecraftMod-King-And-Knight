package com.nmmoc7.a2kn1gh7.capability;

import com.nmmoc7.a2kn1gh7.capability.cost.CostCapability;
import com.nmmoc7.a2kn1gh7.capability.level.LevelCapability;
import com.nmmoc7.a2kn1gh7.capability.reason.ReasonCapability;
import com.nmmoc7.a2kn1gh7.capability.weapon.WeaponCapability;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import javax.annotation.Nullable;

/**
 * @author DustW
 */

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CapabilityRegistryHandler {
    @SubscribeEvent
    public static void onSetupEvent(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            CapabilityManager.INSTANCE.register(
                    LevelCapability.class,
                    new Capability.IStorage<LevelCapability>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<LevelCapability> capability, LevelCapability instance, Direction side) {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<LevelCapability> capability, LevelCapability instance, Direction side, INBT nbt) {

                        }
                    },
                    () -> null
            );

            CapabilityManager.INSTANCE.register(
                    WeaponCapability.class,
                    new Capability.IStorage<WeaponCapability>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<WeaponCapability> capability, WeaponCapability instance, Direction side) {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<WeaponCapability> capability, WeaponCapability instance, Direction side, INBT nbt) {

                        }
                    },
                    () -> null
            );

            CapabilityManager.INSTANCE.register(
                    ReasonCapability.class,
                    new Capability.IStorage<ReasonCapability>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<ReasonCapability> capability, ReasonCapability instance, Direction side) {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<ReasonCapability> capability, ReasonCapability instance, Direction side, INBT nbt) {

                        }
                    },
                    () -> null
            );

            CapabilityManager.INSTANCE.register(
                    CostCapability.class,
                    new Capability.IStorage<CostCapability>() {
                        @Nullable
                        @Override
                        public INBT writeNBT(Capability<CostCapability> capability, CostCapability instance, Direction side) {
                            return null;
                        }

                        @Override
                        public void readNBT(Capability<CostCapability> capability, CostCapability instance, Direction side, INBT nbt) {

                        }
                    },
                    () -> null
            );
        });

    }
}
