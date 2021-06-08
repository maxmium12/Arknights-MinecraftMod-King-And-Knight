package com.nmmoc7.kingandkinght.network.server;

import com.nmmoc7.kingandkinght.capability.ModCapabilities;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author DustW
 */
public class PlayerCapabilitySyncServer implements IServerMessage {
    public CompoundNBT capabilityNBT;
    public String key;

    public PlayerCapabilitySyncServer(String key, CompoundNBT capabilityNBT) {
        this.capabilityNBT = capabilityNBT;
        this.key = key;
    }

    /** 负责序列化的函数 */
    public static void encode(PlayerCapabilitySyncServer packet, PacketBuffer pb) {
        pb.writeString(packet.key);
        // PacketBuffer 为我们提供了 writeString 方法，用于将 String 转化为一串字节
        // 该方法的第二个参数是最大的字节长度，这里我们指定为 114514 个字节
        pb.writeCompoundTag(packet.capabilityNBT);
    }

    /** 负责反序列化的函数 */
    public static PlayerCapabilitySyncServer decode(PacketBuffer pb) {
        // 这个时候字节流已经从发送端传送到接收端了，所以这里的代码是在接收端运行的
        // 我们通过 PacketBuffer::readString 方法读取出刚刚写入的 String 数据，并返回反序列化的结果
        // 如果在序列化阶段向 PacketBuffer 中写入了多个数据，务必用与序列化阶段**相同的顺序**从 PacketBuffer 读取数据！！
        return new PlayerCapabilitySyncServer(pb.readString(), pb.readCompoundTag());
    }

    /** 负责处理数据包的函数
     * 第一个参数当然是自己的数据包
     * 第二个参数提供了一个 NetworkEvent.Context 实例，它包括了许多信息，如这个数据包的发送方向，发送这个数据包的玩家等等
     */
    public static void handle(PlayerCapabilitySyncServer packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            // 给主线程的队列添加一个 “任务”
            ctx.get().enqueueWork(() -> {
                // 这里的代码会在主线程上被执行，所以在这里可以安全的访问游戏对象
                // 因为这些代码会在主线程上执行，一定要考虑到执行效率的问题，否则会造成严重的 tps 降低

                Minecraft.getInstance().player.getCapability(ModCapabilities.getPlayerCapability(packet.key)).ifPresent(theCap ->
                        theCap.deserializeNBT(packet.capabilityNBT)
                );
            });
            // 一定不要忘了标记这个数据包已经被处理了
            ctx.get().setPacketHandled(true);
        }
    }
}
