package com.nmmoc7.kingandkinght.item.weapon.skills.base;

import com.nmmoc7.kingandkinght.item.weapon.skills.SkillData;
import com.nmmoc7.kingandkinght.item.weapon.skills.abstracts.AbstractSkill;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.StringTextComponent;

/**
 * @author DustW
 */
public class ModSkillBase extends AbstractSkill {
    protected ModSkillBase(String registerName,
                           int maxSkillLevel,
                           boolean hasWarmupTime, int warmupTime,
                           SkillPointType skillPointType, int maxCoolDownTime,
                           DurationType durationType, int maxDurationTime,
                           boolean isAutoCast) {
        this.setRegistryName(registerName);

        this.maxSkillLevel = maxSkillLevel;

        this.hasWarmupTime = hasWarmupTime;
        this.warmupTime = warmupTime;

        this.skillPointType = skillPointType;
        this.maxCoolDownTime = maxCoolDownTime;

        this.durationType = durationType;
        this.maxDurationTime = maxDurationTime;

        this.isAutoCast = isAutoCast;
    }

    @Override
    public void cast(SkillData skillData, ItemStack weapon, PlayerEntity caster) {
        caster.sendMessage(new StringTextComponent("1"), caster.getUniqueID());
        skillData.setSkillPoint(0);
    }
}
