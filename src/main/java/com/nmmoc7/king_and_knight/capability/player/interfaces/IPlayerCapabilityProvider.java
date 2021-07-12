package com.nmmoc7.king_and_knight.capability.player.interfaces;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;

/**
 * @author DustW
 */
public interface IPlayerCapabilityProvider extends ICapabilityProvider, INBTSerializable<CompoundNBT> {
    /**
     * 获取或者创建capability
     * @return capability
     */
    @Nonnull
    IPlayerCapability getOrCreateCapability();
}
