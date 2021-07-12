package com.nmmoc7.king_and_knight.network.server;

import com.nmmoc7.king_and_knight.tileentity.abstracts.AbstractTileEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author DustW
 */
public class ItemHandlerSyncServer implements IServerMessage {
    CompoundNBT itemStackNBT;
    int slot;
    BlockPos pos;

    public ItemHandlerSyncServer(CompoundNBT itemStackNBT, int slot, BlockPos pos) {
        this.itemStackNBT = itemStackNBT;
        this.slot = slot;
        this.pos = pos;
    }

    public static void encode(ItemHandlerSyncServer packet, PacketBuffer pb) {
        pb.writeCompoundTag(packet.itemStackNBT);
        pb.writeInt(packet.slot);
        pb.writeBlockPos(packet.pos);
    }

    public static ItemHandlerSyncServer decode(PacketBuffer pb) {
        // 这个时候字节流已经从发送端传送到接收端了，所以这里的代码是在接收端运行的
        // 我们通过 PacketBuffer::readString 方法读取出刚刚写入的 String 数据，并返回反序列化的结果
        // 如果在序列化阶段向 PacketBuffer 中写入了多个数据，务必用与序列化阶段**相同的顺序**从 PacketBuffer 读取数据！！
        return new ItemHandlerSyncServer(pb.readCompoundTag(), pb.readInt(), pb.readBlockPos());
    }

    public static void handle(ItemHandlerSyncServer packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_CLIENT)) {
            // 给主线程的队列添加一个 “任务”
            ctx.get().enqueueWork(() -> {
                TileEntity te = Minecraft.getInstance().player.getEntityWorld().getTileEntity(packet.pos);

                if (te instanceof AbstractTileEntity) {
                    ((AbstractTileEntity) te).getHandler().setStackInSlot(packet.slot, ItemStack.read(packet.itemStackNBT));
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
