package com.nmmoc7.a2kn1gh7.capability.weapon;

import com.nmmoc7.a2kn1gh7.capability.ModCapabilities;
import com.nmmoc7.a2kn1gh7.capability.level.LevelCapability;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * @author DustW
 */
public class WeaponCapabilityProvider implements ICapabilityProvider, INBTSerializable<CompoundNBT> {
    private WeaponCapability weaponCapability;

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == ModCapabilities.WEAPON_CAPABILITY ? LazyOptional.of(this::getOrCreateCapability).cast() : LazyOptional.empty();
    }

    @Nonnull
    WeaponCapability getOrCreateCapability() {
        if (weaponCapability == null) {
            this.weaponCapability = new WeaponCapability();
        }
        return this.weaponCapability;
    }

    @Override
    public CompoundNBT serializeNBT() {
        return getOrCreateCapability().serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        getOrCreateCapability().deserializeNBT(nbt);
    }
}
