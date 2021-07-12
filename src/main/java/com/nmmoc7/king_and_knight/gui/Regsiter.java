package com.nmmoc7.king_and_knight.gui;

import com.nmmoc7.king_and_knight.KingAndKnight;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeContainerType;

import java.util.ArrayList;
import java.util.function.Supplier;

public class Regsiter {
    public static final ArrayList<TileEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<>();
    public static final ContainerType<TestContainer> TEST_CONTAINER =
                    IForgeContainerType.create((int windowId, PlayerInventory inv, PacketBuffer data) ->
                            new TestContainer(
                                    windowId, inv, data.readBlockPos(),
                                    inv.player.world,
                                    new TestGuiTileEntity.TestIntArray()
                            )
                    );
    static {
        TEST_CONTAINER.setRegistryName(KingAndKnight.MOD_ID, "test_container");
    }

    public static final Block TEST_BLOCK = new TestGuiBlock().setRegistryName(new ResourceLocation(KingAndKnight.MOD_ID, "test_block"));

    public static final TileEntityType<TestGuiTileEntity> TEST_TILE_ENTITY = create("test_tile_entity", TestGuiTileEntity::new, TEST_BLOCK);

    public static <T extends TileEntity> TileEntityType<T> create(String name, Supplier<T> supplier, Block... blocks) {
        TileEntityType<T> result = TileEntityType.Builder.create(supplier, blocks).build(null);

        result.setRegistryName(new ResourceLocation(KingAndKnight.MOD_ID, name));
        TILE_ENTITY_TYPES.add(result);

        return result;
    }
}