package com.nmmoc7.king_and_knight.data.dimension;

import com.nmmoc7.king_and_knight.KingAndKnight;
import net.minecraft.util.ResourceLocation;

/**
 * @author DustW
 */
public class ModFlatDimensions {
    public static final FlatDimensionProvider.FlatDimensionBuilder EMPTY_WORLD =
            new FlatDimensionProvider.FlatDimensionBuilder(new ResourceLocation(KingAndKnight.MOD_ID, "test_world"), ModDimensionTypes.TEST_WORLD_TYPE.getRegName())
                .setBiome(new ResourceLocation("minecraft", "plains"))
                .addLayer(1, new ResourceLocation("minecraft", "bedrock"))
                .finish();
}
