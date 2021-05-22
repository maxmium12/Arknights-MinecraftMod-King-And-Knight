package com.nmmoc7.a2kn1gh7.capability.reason;

import com.nmmoc7.a2kn1gh7.capability.ModCapabilities;
import com.nmmoc7.a2kn1gh7.capability.interfaces.IPlayerCapability;
import com.nmmoc7.a2kn1gh7.capability.interfaces.IPlayerCapabilityProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author DustW
 */
public class ReasonCapabilityProvider implements IPlayerCapabilityProvider {
    ReasonCapability reasonCapability;

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ModCapabilities.REASON_CAPABILITY ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    @Override
    public CompoundNBT serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        getOrCreateCapability().deserializeNBT(nbt);
    }

    @NotNull
    @Override
    public IPlayerCapability getOrCreateCapability() {
        if (reasonCapability == null) {
            reasonCapability = new ReasonCapability();
        }
        return reasonCapability;
    }
}
