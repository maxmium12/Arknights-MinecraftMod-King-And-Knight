package com.nmmoc7.a2kn1gh7;

import com.nmmoc7.a2kn1gh7.block.ModBlocks;
import com.nmmoc7.a2kn1gh7.item.ModItems;
import com.nmmoc7.a2kn1gh7.item.ModWeapons;
import com.nmmoc7.a2kn1gh7.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.ModSkills;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @author DustW
 */
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {
    @SubscribeEvent
    public static void onBlocksRegistry(final RegistryEvent.Register<Block> event) {
        A2kn1gh7.LOGGER.info("HELLO from Register Block");

        for (Block block: ModBlocks.BLOCK_LIST) {
            event.getRegistry().register(block);
        }
    }

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
        A2kn1gh7.LOGGER.info("HELLO from Register Item");

        ModSkills.registrySkills();

        for (Item item: ModItems.ITEM_LIST) {
            event.getRegistry().register(item);
        }

        for (AbstractWeapon item: ModWeapons.WEAPONS) {
            event.getRegistry().register(item);
        }
    }
}
