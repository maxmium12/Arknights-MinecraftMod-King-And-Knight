package com.nmmoc7.a2kn1gh7.capability;

import net.minecraft.nbt.CompoundNBT;

import java.math.BigInteger;

/**
 * @author DustW
 */
public class LevelCapability implements ILevelCapability {
    private int level = 0;
    private int experience = 0;

    @Override
    public int getLevel() {
        return level;
    }

    public void addExperience(int experience) {
        int experienceTemp = this.experience + experience;

        if (experienceTemp > getMaxExperience()) {
            addLevel(1);
            this.experience = getMaxExperience() - this.experience;
        }
        else {
            this.experience += experience;
        }
    }

    public void addLevel(int level) {
        this.level += level;
    }

    public int getMaxExperience() {
        int maxExperience = 0;

        if (level > 31) {
            maxExperience = level * 9 - 158;
        }
        else if (level > 16) {
            maxExperience = level * 5 - 38;
        }
        else {
            maxExperience = level * 2 + 7;
        }

        return maxExperience * 100;
    }

    public int getExperience() {
        return experience;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putInt("level", level);

        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.level = nbt.getInt("level");
    }
}
