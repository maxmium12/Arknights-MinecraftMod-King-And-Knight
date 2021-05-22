package com.nmmoc7.a2kn1gh7.capability.cost;

import com.nmmoc7.a2kn1gh7.capability.ModCapabilities;
import com.nmmoc7.a2kn1gh7.capability.interfaces.IPlayerCapability;
import com.nmmoc7.a2kn1gh7.network.ModNetworkManager;
import com.nmmoc7.a2kn1gh7.network.PlayerCapabilitySyncServer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author DustW
 */
public class CostCapability implements IPlayerCapability {
    public int cost = 0;
    public final int MAX_COST = 99;

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("cost", cost);
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        cost = nbt.getInt("cost");
    }

    public void addCost(int amount, ServerPlayerEntity player) {
        if (cost < MAX_COST && cost + amount > 0) {
            cost += amount;

            sync(player);
        }
    }

    @Override
    public void sync(ServerPlayerEntity player) {
        ModNetworkManager.serverSendToPlayer(new PlayerCapabilitySyncServer(ModCapabilities.COST_KEY, serializeNBT()), player);
    }
}
