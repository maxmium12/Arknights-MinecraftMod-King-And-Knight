package com.nmmoc7.king_and_knight.item.weapon.skills.base;

import com.nmmoc7.king_and_knight.item.weapon.skills.SkillData;
import com.nmmoc7.king_and_knight.item.weapon.skills.abstracts.AbstractSkill;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * @author DustW
 */
public class ModSkillBase extends AbstractSkill {
    protected ModSkillBase(String registerName,
                           int maxSkillLevel,
                           boolean hasWarmupTime,
                           SkillPointType skillPointType,
                           DurationType durationType,
                           boolean isAutoCast) {
        this.setRegistryName(registerName);

        this.maxSkillLevel = maxSkillLevel;

        this.hasWarmupTime = hasWarmupTime;

        this.skillPointType = skillPointType;

        this.durationType = durationType;

        this.isAutoCast = isAutoCast;
    }


    @Override
    public int getMaxCoolDownTime(int level) {
        return 0;
    }

    @Override
    public int getMaxDurationTime(int level) {
        return 0;
    }

    @Override
    public int getWarmupTime(int level) {
        return 0;
    }

    @Override
    public CastResult castBegin(ServerPlayerEntity player, ItemStack weapon, SkillData skillData) {
        return CastResult.SUCCESS;
    }

    @Override
    public void castTick(ServerPlayerEntity player, ItemStack weapon, SkillData skillData, int tick) {
    }

    @Override
    public void castAttack(ServerPlayerEntity player, Entity target, ItemStack weapon, SkillData skillData) {

    }

    @Override
    public void castUnderAttack(ServerPlayerEntity player, Entity attacker, ItemStack weapon, SkillData skillData) {

    }

    @Override
    public void castFinish(ServerPlayerEntity player, ItemStack weapon, SkillData skillData) {

    }
}
