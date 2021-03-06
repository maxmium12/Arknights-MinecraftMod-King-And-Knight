package com.nmmoc7.king_and_knight.network;

import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.network.client.IClientMessage;
import com.nmmoc7.king_and_knight.network.client.LeftClickClient;
import com.nmmoc7.king_and_knight.network.server.IServerMessage;
import com.nmmoc7.king_and_knight.network.server.ItemHandlerSyncServer;
import com.nmmoc7.king_and_knight.network.server.PlayerCapabilitySyncServer;
import com.nmmoc7.king_and_knight.network.server.WeaponCapabilitySyncServer;
import com.nmmoc7.king_and_knight.newgui.PacketWeaponUpgrade;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;

/**
 * @author DustW
 */
public class ModNetworkManager {
    private static final String PROTOCOL_VERSION = "1.0";
    private static int id = 0;

    /**
    注册自己的 SimpleChannel
    四个参数的意义分别是：
    频道的名字
    返回当前 “网络协议版本” 的 Supplier<String>
    检测 “客户端是否兼容某网络协议版本” 的谓词
    检测 “服务端是否兼容某网络协议版本” 的谓词

    在这里，我们强制要求服务端和客户端的协议版本（PROTOCOL_VERSION）必须一致，所以后两个参数传入的都是 PROTOCOL_VERSION::equals
    */
    public static SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel (
            new ResourceLocation(KingAndKnight.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    /** 不允许产生该类的实例 */
    private ModNetworkManager() {
        throw new UnsupportedOperationException("No instance");
    }

    public static <T extends IServerMessage> void serverSendToPlayer(T packet, ServerPlayerEntity player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    /** 不 要 在 服 务 端 调 用 */
    public static <T extends IClientMessage> void clientSendToServer(T packet) {
        INSTANCE.sendToServer(packet);
    }

    public static void registerPackets() {
        // INSTANCE 是之前注册的 SimpleChannel 实例
        // registerMessage 的第一个参数是要注册数据包的 unique id，请确保每个数据包的 unique id 不同
        // 第二个参数的意义是 “要注册数据包的类” ，这里当然是 MyPacket.class
        // 后面几个参数依次是数据包的序列化器、反序列化器以及处理器，这里直接用方法引用传入之前写好的三个函数
        INSTANCE.registerMessage(id++,
                PlayerCapabilitySyncServer.class,
                PlayerCapabilitySyncServer::encode,
                PlayerCapabilitySyncServer::decode,
                PlayerCapabilitySyncServer::handle);

        INSTANCE.registerMessage(id++,
                WeaponCapabilitySyncServer.class,
                WeaponCapabilitySyncServer::encode,
                WeaponCapabilitySyncServer::decode,
                WeaponCapabilitySyncServer::handle);

        INSTANCE.registerMessage(id++,
                ItemHandlerSyncServer.class,
                ItemHandlerSyncServer::encode,
                ItemHandlerSyncServer::decode,
                ItemHandlerSyncServer::handle);

        INSTANCE.registerMessage(id++,
                LeftClickClient.class,
                LeftClickClient::encode,
                LeftClickClient::decode,
                LeftClickClient::handle);

        INSTANCE.registerMessage(id++,
                PacketWeaponUpgrade.class,
                PacketWeaponUpgrade::encode,
                PacketWeaponUpgrade::decode,
                PacketWeaponUpgrade::handle);
    }
}