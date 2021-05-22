package com.nmmoc7.a2kn1gh7.item.weapon.skills;

import com.nmmoc7.a2kn1gh7.item.weapon.skills.abstracts.AbstractSkill;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * @author DustW
 */
public class Skill extends AbstractSkill {
    protected Skill(String registerName,
                    boolean hasWarmupTime, int warmupTime,
                    CoolDownType coolDownType, int maxCoolDownTime,
                    DurationType durationType, int maxDurationTime,
                    boolean isAutoCast,
                    int cost) {
        this.setRegistryName(registerName);

        this.hasWarmupTime = hasWarmupTime;
        this.warmupTime = warmupTime;

        this.coolDownType = coolDownType;
        this.maxCoolDownTime = maxCoolDownTime;

        this.durationType = durationType;
        this.maxDurationTime = maxDurationTime;

        this.isAutoCast = isAutoCast;
        this.cost = cost;
    }

    @Override
    public void cast(SkillData skillData, ItemStack weapon, PlayerEntity caster) {

    }
}
