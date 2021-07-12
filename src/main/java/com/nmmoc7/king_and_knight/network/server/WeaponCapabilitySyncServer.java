package com.nmmoc7.king_and_knight.network.server;

import com.nmmoc7.king_and_knight.capability.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author DustW, zomb
 */
public class WeaponCapabilitySyncServer implements IServerMessage {
    CompoundNBT activeSkillNBT;
    int[][] attackRange;

    public WeaponCapabilitySyncServer(CompoundNBT nbt, int[][] attackRange) {
        activeSkillNBT = nbt;
        this.attackRange = attackRange;
    }

    public static void encode(WeaponCapabilitySyncServer packet, PacketBuffer pb) {
        pb.writeCompoundTag(packet.activeSkillNBT);

        if (packet.attackRange != null) {
            pb.writeVarInt(packet.attackRange.length);

            for (int i = 0; i < packet.attackRange.length; i++) {
                pb.writeVarIntArray(packet.attackRange[i]);
            }
        }
        else {
            pb.writeVarInt(0);
        }
    }

    public static WeaponCapabilitySyncServer decode(PacketBuffer pb) {
        CompoundNBT skillNBT = pb.readCompoundTag();

        int[][] result = new int[pb.readVarInt()][];

        for (int i = 0; i < result.length; i++) {
            result[i] = pb.readVarIntArray(9999);
        }

        return new WeaponCapabilitySyncServer(skillNBT, result);
    }

    public static void handle(WeaponCapabilitySyncServer packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            // 给主线程的队列添加一个 “任务”
            ctx.get().enqueueWork(() -> {
                if (!Minecraft.getInstance().player.getHeldItemMainhand().isEmpty()) {
                    Minecraft.getInstance().player.getHeldItemMainhand().getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> {
                        theCap.getActiveSkill().deserializeNBT(packet.activeSkillNBT);

                        if (packet.attackRange.length > 0) {
                            theCap.setCustomAttackRange(theCap.toTypeArray(packet.attackRange));
                        }
                        else {
                            theCap.setCustomAttackRange(null);
                        }
                    });
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
