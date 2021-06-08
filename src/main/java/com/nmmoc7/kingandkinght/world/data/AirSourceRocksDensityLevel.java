package com.nmmoc7.kingandkinght.world.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;

/**
 * @author DustW
 */
public class AirSourceRocksDensityLevel extends WorldSavedData {
    private static final String NAME = "AirSourceRocksDensityLevel";

    public AirSourceRocksDensityLevel() {
        super(NAME);
    }

    public static AirSourceRocksDensityLevel get(World worldIn) {
        if (!(worldIn instanceof ServerWorld)) {
            throw new RuntimeException("Attempted to get the data from a client world. This is wrong.");
        }

        ServerWorld world = worldIn.getServer().getWorld(World.OVERWORLD);
        /*
            如果你需要每个纬度都有一个自己的World Saved Data。
           用 ServerWorld world = (ServerWorld)world; 代替上面那句。
         */
        DimensionSavedDataManager storage = world.getSavedData();
        return storage.getOrCreate(AirSourceRocksDensityLevel::new, NAME);
    }

    @Override
    public void read(CompoundNBT nbt) {

    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        return null;
    }
}