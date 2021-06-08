package com.nmmoc7.kingandkinght.item.weapon.skills;

import com.nmmoc7.kingandkinght.item.weapon.skills.base.ModSkillBase;

/**
 * @author DustW
 */
public class SkillFairyGuardian extends ModSkillBase {
    public SkillFairyGuardian() {
        super("fairy_guardian",
                10,
                true, 5,
                SkillPointType.NATURAL, 25,
                DurationType.NATURAL, 5, false);
    }
}
