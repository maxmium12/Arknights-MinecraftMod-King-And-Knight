package com.nmmoc7.a2kn1gh7.capability;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.capability.cost.CostCapabilityProvider;
import com.nmmoc7.a2kn1gh7.capability.level.LevelCapability;
import com.nmmoc7.a2kn1gh7.capability.level.LevelCapabilityProvider;
import com.nmmoc7.a2kn1gh7.capability.reason.ReasonCapabilityProvider;
import com.nmmoc7.a2kn1gh7.network.PlayerCapabilitySyncServer;
import com.nmmoc7.a2kn1gh7.network.ModNetworkManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber
public class CommonEventHandler {
    @SubscribeEvent
    public static void onAttachEntityCapabilityEvent(AttachCapabilitiesEvent<Entity> event) {
        Entity entity = event.getObject();
        if (entity instanceof PlayerEntity) {
            event.addCapability(new ResourceLocation(A2kn1gh7.MODID, "level_cap"), new LevelCapabilityProvider());
            event.addCapability(new ResourceLocation(A2kn1gh7.MODID, "cost_cap"), new CostCapabilityProvider());
            event.addCapability(new ResourceLocation(A2kn1gh7.MODID, "reason_cap"), new ReasonCapabilityProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            PlayerEntity original = event.getOriginal();
            PlayerEntity target = event.getPlayer();

            ModCapabilities.getOrInitPlayerCapabilities().entrySet().forEach(capability -> {
                original.getCapability(capability.getValue()).ifPresent(originalCap -> {
                    target.getCapability(capability.getValue()).ifPresent(targetCap -> {
                        targetCap.deserializeNBT(originalCap.serializeNBT());
                    });
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        PlayerEntity player = event.getPlayer();

        if (!player.world.isRemote) {
            ModCapabilities.getOrInitPlayerCapabilities().entrySet().forEach(capability -> {
                player.getCapability(capability.getValue()).ifPresent(theCap -> {
                    theCap.sync((ServerPlayerEntity) player);
                });
            });
        }
    }

    @SubscribeEvent
    public static void onEntityDeathEvent(LivingDeathEvent event) {
        if (!event.getEntity().world.isRemote) {
            Entity attacker = event.getSource().getTrueSource();

            if (attacker instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) attacker;

                LazyOptional<LevelCapability> capability = player.getCapability(ModCapabilities.LEVEL_CAPABILITY);

                capability.ifPresent(theCap ->
                        theCap.addExperience(((int) event.getEntityLiving().getMaxHealth()) / 5 + 1, (ServerPlayerEntity) player)
                );
            }
        }
    }
}

