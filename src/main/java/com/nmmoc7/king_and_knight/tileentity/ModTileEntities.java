package com.nmmoc7.king_and_knight.tileentity;

import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.block.ModBlocks;
import com.nmmoc7.king_and_knight.newgui.WeaponUpgraderBlock;
import com.nmmoc7.king_and_knight.newgui.WeaponUpgraderTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public class ModTileEntities {
    public static final ArrayList<TileEntityType<?>> TILE_ENTITY_TYPES = new ArrayList<>();

    public static final TileEntityType<InfrastructureTileEntity> INFRASTRUCTURE_TILE_ENTITY =
            create("infrastructure_tile_entity", InfrastructureTileEntity::new,
                    ModBlocks.INFRASTRUCTURE_ONE,
                    ModBlocks.INFRASTRUCTURE_TWO,
                    ModBlocks.INFRASTRUCTURE_THREE
            );
    public static final TileEntityType<WeaponUpgraderTileEntity> WEAPON_UPGRADER_TILE_ENTITY =
            create("weapon_upgrader_tile_entity", WeaponUpgraderTileEntity::new,
                    ModBlocks.WEAPON_UPGRADER);

    public static <T extends TileEntity> TileEntityType<T> create(String name, Supplier<T> supplier, Block... blocks) {
        TileEntityType<T> result = TileEntityType.Builder.create(supplier, blocks).build(null);

        result.setRegistryName(new ResourceLocation(KingAndKnight.MOD_ID, name));
        TILE_ENTITY_TYPES.add(result);

        return result;
    }
}
