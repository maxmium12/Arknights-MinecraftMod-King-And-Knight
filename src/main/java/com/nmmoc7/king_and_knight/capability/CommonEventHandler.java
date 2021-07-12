package com.nmmoc7.king_and_knight.capability;

import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.capability.player.PlayerCapabilitiesProvider;
import com.nmmoc7.king_and_knight.capability.player.level.LevelCapability;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
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
            event.addCapability(new ResourceLocation(KingAndKnight.MOD_ID, "player_capabilities"), new PlayerCapabilitiesProvider());
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) {
            PlayerEntity original = event.getOriginal();
            PlayerEntity target = event.getPlayer();

            ModCapabilities.getPlayerCapabilities().forEach((key, value) -> {
                original.getCapability(value).ifPresent(originalCap -> {
                    target.getCapability(value).ifPresent(targetCap -> {
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
            ModCapabilities.getPlayerCapabilities().values().forEach((value) -> {
                player.getCapability(value).ifPresent(theCap -> {
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

