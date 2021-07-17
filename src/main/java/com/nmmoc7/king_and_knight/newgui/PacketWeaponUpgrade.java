package com.nmmoc7.king_and_knight.newgui;

import com.nmmoc7.king_and_knight.capability.ModCapabilities;
import com.nmmoc7.king_and_knight.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.king_and_knight.network.client.IClientMessage;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

/**
 * @author MaxSeth
 */
public class PacketWeaponUpgrade implements IClientMessage {

    public static void encode(PacketWeaponUpgrade packet, PacketBuffer buffer){}

    public static PacketWeaponUpgrade decode(PacketBuffer buffer){
        return new PacketWeaponUpgrade();
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER &&
                    ctx.get().getSender().openContainer instanceof ContainerWeapon){
                ContainerWeapon containerWeapon = (ContainerWeapon) ctx.get().getSender().openContainer;
                ItemStack stack = containerWeapon.getSlot(1).getStack();
                ItemStack weapon = containerWeapon.getSlot(0).getStack();
                if(!(weapon.getItem() instanceof AbstractWeapon)){
                    ctx.get().getSender().sendMessage(new TranslationTextComponent("king_and_knights.weapon_upgrade.noweapon"), Util.DUMMY_UUID);
                }
                if(!(stack.getItem() instanceof ItemExperienceCard)){
                    ctx.get().getNetworkManager().closeChannel(new StringTextComponent("What did you do?"));
                }
                int consumeExperienceCards = calculateExperience(stack, weapon);
                if((consumeExperienceCards != 0)){
                    weapon.getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(cap -> {
                        cap.setLevel(cap.getLevel() + 1);
                        cap.sync(ctx.get().getSender());
                    });
                }
                containerWeapon.getSlot(1).decrStackSize(consumeExperienceCards);
            }
        });
        ctx.get().setPacketHandled(true);
    }
    /**
    *TODO 在这里计算所需经验卡, 等于0为不足*/
    private int calculateExperience(ItemStack stack, ItemStack weapon){
        return 0;
    }


}