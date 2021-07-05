package com.nmmoc7.kingandkinght.item.weapon;

import com.nmmoc7.kingandkinght.capability.weapon.WeaponCapability;
import com.nmmoc7.kingandkinght.item.weapon.abstracts.ModLongRangeWeaponBase;
import com.nmmoc7.kingandkinght.item.weapon.skills.SkillData;
import com.nmmoc7.kingandkinght.item.weapon.skills.SkillFairyGuardian;
import com.nmmoc7.kingandkinght.item.weapon.skills.abstracts.AbstractSkill;
import com.nmmoc7.kingandkinght.item.weapon.skills.enums.AttackRangeType;
import com.nmmoc7.kingandkinght.itemgroup.ModItemGroups;
import com.nmmoc7.kingandkinght.model.IrisWeaponRender;

import static com.nmmoc7.kingandkinght.item.weapon.skills.enums.AttackRangeType.*;

/**
 * @author DustW
 */
public class IrisWeapon extends ModLongRangeWeaponBase {
    public IrisWeapon() {
        super("iris_weapon", 548, 22, 20, ModItemGroups.WEAPON_GROUP, () -> IrisWeaponRender::new);

        addTierToAttackRange(0, new AttackRangeType[][]{
                {N, Y, Y, N},
                {O, Y, Y, Y},
                {N, Y, Y, N}
        });
        addTierToAttackRange(1, new AttackRangeType[][]{
                {N, Y, Y, Y},
                {O, Y, Y, Y},
                {N, Y, Y, Y}
        });
        addTierToAttackRange(2, new AttackRangeType[][]{
                {N, Y, Y, Y},
                {O, Y, Y, Y},
                {N, Y, Y, Y}
        });
    }

    @Override
    public void initCap(WeaponCapability capability) {
        capability.defaultInit();
        capability.SKILLS.set(0, new SkillData(AbstractSkill.registerSkill(SkillFairyGuardian::new)));
    }
}
