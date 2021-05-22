package com.nmmoc7.a2kn1gh7.capability.interfaces;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author DustW
 */
public interface IPlayerCapability extends INBTSerializable<CompoundNBT> {
    /**
     * 调用此方法同步客户端
     * @param player 同步的目标玩家
     */
    void sync(ServerPlayerEntity player);
}
