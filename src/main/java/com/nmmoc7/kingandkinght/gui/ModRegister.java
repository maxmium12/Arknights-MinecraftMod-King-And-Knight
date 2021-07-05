package com.nmmoc7.kingandkinght.gui;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.itemgroup.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegister {
    @SubscribeEvent
    public static void onBlockSetupEvent(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(Regsiter.TEST_BLOCK);
    }

    @SubscribeEvent
    public static void onItemSetupEvent(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(new BlockItem(Regsiter.TEST_BLOCK, new Item.Properties().group(ModItemGroups.BLOCK_GROUP)).setRegistryName(new ResourceLocation(KingAndKnight.MOD_ID, "test_block")));
    }

    @SubscribeEvent
    public static void onContainerTypeRegister(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(Regsiter.TEST_CONTAINER);
    }

    @SubscribeEvent
    public static void onTileEntityTypeRegister(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(Regsiter.TEST_TILE_ENTITY);
    }
}
