package com.nmmoc7.kingandkinght.network.server;

import com.nmmoc7.kingandkinght.capability.ModCapabilities;
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

    public WeaponCapabilitySyncServer(CompoundNBT nbt) {
        activeSkillNBT = nbt;
    }

    public static void encode(WeaponCapabilitySyncServer packet, PacketBuffer pb) {
        pb.writeCompoundTag(packet.activeSkillNBT);
    }

    public static WeaponCapabilitySyncServer decode(PacketBuffer pb) {
        // 这个时候字节流已经从发送端传送到接收端了，所以这里的代码是在接收端运行的
        // 我们通过 PacketBuffer::readString 方法读取出刚刚写入的 String 数据，并返回反序列化的结果
        // 如果在序列化阶段向 PacketBuffer 中写入了多个数据，务必用与序列化阶段**相同的顺序**从 PacketBuffer 读取数据！！
        return new WeaponCapabilitySyncServer(pb.readCompoundTag());
    }

    public static void handle(WeaponCapabilitySyncServer packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            // 给主线程的队列添加一个 “任务”
            ctx.get().enqueueWork(() -> {
                if (!Minecraft.getInstance().player.getHeldItemMainhand().isEmpty()) {
                    Minecraft.getInstance().player.getHeldItemMainhand().getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> {
                        theCap.getActiveSkill().deserializeNBT(packet.activeSkillNBT);
                    });
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
