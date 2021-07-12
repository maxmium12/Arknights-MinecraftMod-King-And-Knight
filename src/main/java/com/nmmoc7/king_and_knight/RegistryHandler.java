package com.nmmoc7.king_and_knight;

import com.nmmoc7.king_and_knight.block.ModBlocks;
import com.nmmoc7.king_and_knight.item.ModItems;
import com.nmmoc7.king_and_knight.item.ModWeapons;
import com.nmmoc7.king_and_knight.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.king_and_knight.item.weapon.skills.ModSkills;
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
        KingAndKnight.LOGGER.info("HELLO from Register Block");

        for (Block block: ModBlocks.BLOCK_LIST) {
            event.getRegistry().register(block);
        }
    }

    @SubscribeEvent
    public static void onItemRegistry(final RegistryEvent.Register<Item> event) {
        KingAndKnight.LOGGER.info("HELLO from Register Item");

        ModSkills.registrySkills();

        for (Item item: ModItems.ITEM_LIST) {
            event.getRegistry().register(item);
        }

        for (AbstractWeapon item: ModWeapons.WEAPONS) {
            event.getRegistry().register(item);
        }
    }
}
