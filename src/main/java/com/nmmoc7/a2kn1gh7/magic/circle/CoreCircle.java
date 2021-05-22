package com.nmmoc7.a2kn1gh7.magic.circle;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.math.vector.Vector3d;

/**
 * @author DustW
 */
public class CoreCircle {
    private String registerName;
    private String translationKey;

    protected final DataParameter<CompoundNBT> THIS = EntityDataManager.createKey(EntityCircleTest.class, DataSerializers.COMPOUND_NBT);



    protected double rotationSpeed;

    protected Vector3d axialVector;

    protected Vector3d centerRelativePosition;
    protected double size;

    protected int forward;
    protected int life;

    protected int color;

    public CoreCircle(String regName, Vector3d axialVector, Vector3d centerRelativePosition,
                      double rotationSpeed, double size, int forward, int life, int color) {

        this.rotationSpeed = rotationSpeed;

        this.axialVector = axialVector;
        this.centerRelativePosition = centerRelativePosition;
        this.size = size;

        this.forward = forward;
        this.life = life;

        this.color = color;

        this.registerName = regName;
        this.translationKey = Util.makeTranslationKey("circle", new ResourceLocation(A2kn1gh7.MODID, regName));

        ModCircles.CIRCLES.add(this);
    }

    public String getTranslationKey() {
        return this.translationKey;
    }


    public CompoundNBT toNbt() {
        CompoundNBT result = new CompoundNBT();

        result.putString("registerName", registerName);

        result.putDouble("axialVectorX", axialVector.x);
        result.putDouble("axialVectorY", axialVector.y);
        result.putDouble("axialVectorZ", axialVector.z);

        result.putDouble("centerRelativePositionX", centerRelativePosition.x);
        result.putDouble("centerRelativePositionY", centerRelativePosition.y);
        result.putDouble("centerRelativePositionZ", centerRelativePosition.z);

        result.putDouble("size", size);

        result.putDouble("rotationSpeed", rotationSpeed);

        result.putInt("forward", forward);
        result.putInt("life", life);
        result.putInt("color", color);

        return result;
    }

    public static CoreCircle fromNbt(CompoundNBT nbt) {
        String registerName = nbt.getString("registerName");

        double axialVectorX = nbt.getDouble("axialVectorX");
        double axialVectorY = nbt.getDouble("axialVectorY");
        double axialVectorZ = nbt.getDouble("axialVectorZ");
        Vector3d axialVector = new Vector3d(axialVectorX, axialVectorY, axialVectorZ);

        double centerRelativePositionX = nbt.getDouble("centerRelativePositionX");
        double centerRelativePositionY = nbt.getDouble("centerRelativePositionY");
        double centerRelativePositionZ = nbt.getDouble("centerRelativePositionZ");
        Vector3d centerRelativePosition = new Vector3d(centerRelativePositionX, centerRelativePositionY, centerRelativePositionZ);

        double size = nbt.getDouble("size");

        double rotationSpeed = nbt.getDouble("rotationSpeed");


        int forward = nbt.getInt("forward");
        int life = nbt.getInt("life");
        int color = nbt.getInt("color");

        return new CoreCircle(registerName, axialVector, centerRelativePosition, rotationSpeed, size, forward, life, color);
    }

    public void registerData(Entity entity) {
        entity.getDataManager().register(THIS, toNbt());
    }
}
