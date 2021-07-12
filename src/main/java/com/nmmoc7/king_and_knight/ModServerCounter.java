package com.nmmoc7.king_and_knight;

import com.nmmoc7.king_and_knight.capability.ModCapabilities;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;

import java.util.List;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class ModServerCounter {
    public static int count = 0;

    public static List<ServerPlayerEntity> players;

    public static final int TPS = 20;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        count++;
        cast();
    }

    public static void cast() {
        players.forEach(player -> {
            if (count % (TPS * 60) == 0) {
                player.getCapability(ModCapabilities.REASON_CAPABILITY).ifPresent(theCap -> {
                    theCap.addReason(1, player);
                });
            }

            if (count % (TPS * 5) == 0) {
                player.getCapability(ModCapabilities.COST_CAPABILITY).ifPresent(theCap -> {
                    theCap.addCost(1, player);
                });
            }
        });
    }

    @SubscribeEvent
    public static void onServerStarted(FMLServerStartedEvent event) {
        players = event.getServer().getPlayerList().getPlayers();
    }
}
