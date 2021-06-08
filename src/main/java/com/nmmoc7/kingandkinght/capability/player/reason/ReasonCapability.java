package com.nmmoc7.kingandkinght.capability.player.reason;

import com.nmmoc7.kingandkinght.capability.ModCapabilities;
import com.nmmoc7.kingandkinght.capability.player.interfaces.IPlayerCapability;
import com.nmmoc7.kingandkinght.network.ModNetworkManager;
import com.nmmoc7.kingandkinght.network.server.PlayerCapabilitySyncServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author DustW
 */
public class ReasonCapability implements IPlayerCapability {
    public int reason = 0;

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("reason", reason);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        reason = nbt.getInt("reason");
    }

    public void addReason(int amount, ServerPlayerEntity player) {
        if (reason < getMaxReason(player) && reason + amount >= 0) {
            reason += amount;

            sync(player);
        }
    }

    public int getMaxReason(PlayerEntity player) {
        AtomicInteger result = new AtomicInteger(25);

        player.getCapability(ModCapabilities.LEVEL_CAPABILITY).ifPresent(theCap -> {
            result.addAndGet(theCap.getLevel() / 2);
        });

        return result.get();
    }

    @Override
    public void sync(ServerPlayerEntity player) {
        ModNetworkManager.serverSendToPlayer(new PlayerCapabilitySyncServer(ModCapabilities.REASON_KEY, serializeNBT()), player);
    }
}
