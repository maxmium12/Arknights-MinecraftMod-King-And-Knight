package com.nmmoc7.a2kn1gh7.multiblock;

import com.mojang.datafixers.types.Type;
import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.multiblock.base.ModMultiBlockTileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

/**
 * @author DustW
 */
public class MultiBlockTileEntities {
    private static final DeferredRegister<TileEntityType<?>> TILES =
            DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, A2kn1gh7.MODID);

    public static final RegistryObject<TileEntityType<ModMultiBlockTileEntityBase>> MULTI_BLOCK_BASE = register("multi_block_test", TestMultiBlockTileEntity::new, null, ModMultiBlockCores.TEST_CORE.get());

    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String name, Supplier<T> tile, Type<?> type, Block... blocks) {
        return TILES.register(name, () -> TileEntityType.Builder.create(tile, blocks).build(type));
    }

    public static void register() {
        TILES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
