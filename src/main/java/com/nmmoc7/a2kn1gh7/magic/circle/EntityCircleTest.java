package com.nmmoc7.a2kn1gh7.magic.circle;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.Optional;
import java.util.UUID;

/**
 * @author DustW
 */
public class EntityCircleTest extends Entity {
    private final CoreCircle[] CIRCLES;

    private static final DataParameter<Float> COLOR_R = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> COLOR_G = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> COLOR_B = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> COLOR_R2 = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> COLOR_G2 = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> COLOR_B2 = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> COLOR_A = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.FLOAT);
    private static final DataParameter<CompoundNBT> LOOK_VEC = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.COMPOUND_NBT);
    private static final DataParameter<Float> DISTANCE = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.FLOAT);
    private static final DataParameter<Optional<UUID>> CASTER_UUID = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    public EntityCircleTest(EntityType<?> entityTypeIn, World worldIn, CoreCircle[] circles) {
        super(entityTypeIn, worldIn);
        this.CIRCLES = circles;
    }

    @Override
    protected void registerData() {
        this.dataManager.register(COLOR_R, 1.0F);
        this.dataManager.register(COLOR_G, 1.0F);
        this.dataManager.register(COLOR_B, 1.0F);
        this.dataManager.register(COLOR_R2, 1.0F);
        this.dataManager.register(COLOR_G2, 1.0F);
        this.dataManager.register(COLOR_B2, 1.0F);
        this.dataManager.register(COLOR_A, 0.0F);
        this.dataManager.register(DISTANCE, 0.0F);
        this.dataManager.register(LOOK_VEC, new CompoundNBT());
        this.dataManager.register(CASTER_UUID, Optional.empty());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     *
     * @param compound
     */
    @Override
    protected void readAdditional(CompoundNBT compound) {

    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {

    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
