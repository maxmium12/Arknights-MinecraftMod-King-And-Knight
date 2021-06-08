package com.nmmoc7.kingandkinght.machines.tileentity;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.tileentity.TileEntityType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DustW
 */
public class ModTileEntityOpeningHandler {
    public static final Map<ServerPlayerEntity, TileEntityType<?>> PLAYER_OPENING_TILE_ENTITY_MAP = new HashMap<>();

    public static void add(ServerPlayerEntity player, TileEntityType<?> tileEntity) {
        PLAYER_OPENING_TILE_ENTITY_MAP.put(player, tileEntity);
    }

    public static void remove(ServerPlayerEntity player) {
        PLAYER_OPENING_TILE_ENTITY_MAP.remove(player);
    }
}
