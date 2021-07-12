package com.nmmoc7.king_and_knight.model;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.core.controller.AnimationController;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(modid = GeckoLib.ModID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandler {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderers(final FMLClientSetupEvent event) {
        if (!FMLEnvironment.production) {
            AnimationController.addModelFetcher((object) -> new IrisWeaponRender().getGeoModelProvider());
        }
    }
}