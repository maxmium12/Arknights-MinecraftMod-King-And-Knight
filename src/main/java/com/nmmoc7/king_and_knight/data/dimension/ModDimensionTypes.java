package com.nmmoc7.king_and_knight.data.dimension;

import com.nmmoc7.king_and_knight.KingAndKnight;
import net.minecraft.util.ResourceLocation;

/**
 * @author DustW
 */
public class ModDimensionTypes {
    public static final DimensionTypeProvider.DimensionTypeBuilder TEST_WORLD_TYPE =
            new DimensionTypeProvider.DimensionTypeBuilder(new ResourceLocation(KingAndKnight.MOD_ID, "test_world_type"))
                    .hasCeiling(false)
                    .logicalHeight(0)
                    .bedWorks(false)
                    .respawnAnchorWorks(false)
                    .hasRaids(false)
                    .hasSkylight(true)
                    .ambientLight(1)
                    .natural(false)
                    .piglinSafe(true)
                    .coordinateScale(1)
                    .ultrawarm(false)
                    .infiniburn(new ResourceLocation("minecraft", "infiniburn_overworld"));
}
