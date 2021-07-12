package com.nmmoc7.king_and_knight.item.weapon.skills;

import com.nmmoc7.king_and_knight.capability.ModCapabilities;
import com.nmmoc7.king_and_knight.capability.weapon.WeaponCapability;
import com.nmmoc7.king_and_knight.item.weapon.skills.base.ModSkillBase;
import com.nmmoc7.king_and_knight.item.weapon.skills.enums.AttackRangeType;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

import static com.nmmoc7.king_and_knight.item.weapon.skills.enums.AttackRangeType.O;
import static com.nmmoc7.king_and_knight.item.weapon.skills.enums.AttackRangeType.Y;

/**
 * @author DustW
 */
public class SkillFairyGuardian extends ModSkillBase {
    public SkillFairyGuardian() {
        super("fairy_guardian",
                10,
                true,
                SkillPointType.NATURAL,
                DurationType.NATURAL, false);
    }

    @Override
    public int getMaxCoolDownTime(int level) {
        int result;

        if (level <= 9) {
            result = 26 - level;
        }
        else {
            result = 15;
        }

        return result;
    }

    @Override
    public int getWarmupTime(int level) {
        return 5;
    }

    @Override
    public int getMaxDurationTime(int level) {
        return 5;
    }

    @Override
    public CastResult castBegin(ServerPlayerEntity player, ItemStack weapon, SkillData skillData) {
        WeaponCapability cap = weapon.getCapability(ModCapabilities.WEAPON_CAPABILITY).orElse(null);
        cap.setCustomAttackRange(new AttackRangeType[][]{{O, Y, Y, Y, Y}});
        cap.setAttackSpeedM(0.2);

        int skillLevel = skillData.getLevel();

        if (skillLevel >= 10) {
            cap.setAttackDamageM(0.5);
        }
        else if (skillLevel >= 7) {
            cap.setAttackSpeedM(0.4 + (skillLevel - 7) * 0.03);
        }
        else if (skillLevel >= 4) {
            cap.setAttackDamageM(0.35 + (skillLevel - 4) * 0.01);
        }
        else {
            cap.setAttackDamageM(0.3 + skillLevel * 0.01);
        }

        return CastResult.SUCCESS;
    }

    @Override
    public void castFinish(ServerPlayerEntity player, ItemStack weapon, SkillData skillData) {
        WeaponCapability cap = weapon.getCapability(ModCapabilities.WEAPON_CAPABILITY).orElse(null);
        cap.setCustomAttackRange(null);
        cap.setAttackSpeedM(1);
        cap.setAttackDamageM(1);
    }
}
