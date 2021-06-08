package com.nmmoc7.kingandkinght.capability.player;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.nmmoc7.kingandkinght.capability.player.interfaces.IPlayerCapability;
import com.nmmoc7.kingandkinght.capability.player.interfaces.IPlayerCapabilityProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

/**
 * @author DustW
 */
public class PlayerCapabilitiesProvider implements IPlayerCapabilityProvider {
    private static final BiMap<String, Supplier<IPlayerCapability>> STRING_TO_CAPABILITY_SUPPLIER = HashBiMap.create();
    private static final BiMap<String, Capability<? extends IPlayerCapability>> PLAYER_CAPABILITIES = HashBiMap.create();

    private static final BiMap<String, IPlayerCapability> STRING_TO_CAPABILITY = HashBiMap.create();

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return PLAYER_CAPABILITIES.containsValue(cap) ?  LazyOptional.of(() -> getOrCreateCapability(cap)).cast() : LazyOptional.empty();
    }

    public static void register(String key, Supplier<IPlayerCapability> supplier, Capability<? extends IPlayerCapability> capability) {
        STRING_TO_CAPABILITY_SUPPLIER.put(key, supplier);
        PLAYER_CAPABILITIES.put(key, capability);
    }

    @NotNull
    @Override
    public IPlayerCapability getOrCreateCapability() {
        return null;
    }

    public <T> T getOrCreateCapability(Capability<T> capability) {
        String key = PLAYER_CAPABILITIES.inverse().get(capability);
        T result = (T) STRING_TO_CAPABILITY.get(key);

        if (result == null) {
            STRING_TO_CAPABILITY.put(key, STRING_TO_CAPABILITY_SUPPLIER.get(key).get());
        }

        return (T) STRING_TO_CAPABILITY.get(key);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT result = new CompoundNBT();

        STRING_TO_CAPABILITY.forEach((key, value) -> {
            result.put(key, value.serializeNBT());
        });

        return result;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        PLAYER_CAPABILITIES.keySet().forEach(key -> {
            if (nbt.get(key) != null) {
                IPlayerCapability capability = STRING_TO_CAPABILITY_SUPPLIER.get(key).get();
                capability.deserializeNBT((CompoundNBT) nbt.get(key));
                STRING_TO_CAPABILITY.put(key, capability);
            }
        });
    }

    public static BiMap<String, Capability<? extends IPlayerCapability>> getPlayerCapabilities() {
        return PLAYER_CAPABILITIES;
    }
}
