package com.nmmoc7.a2kn1gh7;

import com.nmmoc7.a2kn1gh7.capability.ModCapabilities;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.abstracts.AbstractSkill;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.SkillData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class ModServerCounter {
    public static int count = 0;

    public static final ArrayList<ServerPlayerEntity> PLAYERS = new ArrayList<>();

    public static final int TPS = 20;

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {
        count++;
        cast();
    }

    public static void cast() {
        PLAYERS.forEach(player -> {
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
    public static void onPlayerJoinWorld(EntityJoinWorldEvent event) {
        if (!event.getWorld().isRemote) {
            if (event.getEntity() instanceof PlayerEntity) {
                PLAYERS.add(((ServerPlayerEntity) event.getEntity()));
            }
        }
    }
}
