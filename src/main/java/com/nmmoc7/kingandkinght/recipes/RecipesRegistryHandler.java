package com.nmmoc7.kingandkinght.recipes;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RecipesRegistryHandler {
    @SubscribeEvent
    public static void onRecipesRegister(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().registerAll(InfrastructureRecipes.SERIALIZER);
    }
}
