package com.nmmoc7.king_and_knight.capability.player.level;

import com.nmmoc7.king_and_knight.capability.ModCapabilities;
import com.nmmoc7.king_and_knight.capability.player.interfaces.IPlayerCapability;
import com.nmmoc7.king_and_knight.network.ModNetworkManager;
import com.nmmoc7.king_and_knight.network.server.PlayerCapabilitySyncServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;

/**
 * @author DustW
 */
public class LevelCapability implements IPlayerCapability {
    private int level = 0;
    private int experience = 0;

    public int getLevel() {
        return level;
    }

    public void addExperience(int experience, ServerPlayerEntity player) {
        int experienceTemp = this.experience + experience;

        if (experienceTemp > getMaxExperience()) {
            this.experience = getMaxExperience() - this.experience;
            addLevel(1);
        }
        else {
            this.experience += experience;
        }

        sync(player);
    }

    public void addLevel(int level, ServerPlayerEntity player) {
        addLevel(level);
        sync(player);
    }

    private void addLevel(int amount) {
        if (level + amount >= 0) {
            level += amount;
        }
    }

    public int getMaxExperience() {
        int maxExperience;

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
        compoundNBT.putInt("experience", experience);

        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        this.level = nbt.getInt("level");
        this.experience = nbt.getInt("experience");
    }

    @Override
    public void sync(ServerPlayerEntity player) {
        ModNetworkManager.serverSendToPlayer(new PlayerCapabilitySyncServer(ModCapabilities.LEVEL_KEY, serializeNBT()), player);
    }
}
