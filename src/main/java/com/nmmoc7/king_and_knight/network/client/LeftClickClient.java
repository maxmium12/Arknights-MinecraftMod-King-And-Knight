package com.nmmoc7.king_and_knight.network.client;

import com.nmmoc7.king_and_knight.item.weapon.abstracts.AbstractWeapon;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author DustW
 */
public class LeftClickClient implements IClientMessage {
    public static void encode(LeftClickClient packet, PacketBuffer pb) {
    }

    public static LeftClickClient decode(PacketBuffer pb) {
        return new LeftClickClient();
    }

    public static void handle(LeftClickClient packet, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection().equals(NetworkDirection.PLAY_TO_SERVER)) {
            // 给主线程的队列添加一个 “任务”
            ctx.get().enqueueWork(() -> {
                ServerPlayerEntity sender = ctx.get().getSender();

                if (sender.getHeldItemMainhand().getItem() instanceof AbstractWeapon) {
                    ItemStack weapon = sender.getHeldItemMainhand();
                    AbstractWeapon weaponItem = (AbstractWeapon) sender.getHeldItemMainhand().getItem();

                    weaponItem.onLeftClick(sender, weapon);
                }
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
