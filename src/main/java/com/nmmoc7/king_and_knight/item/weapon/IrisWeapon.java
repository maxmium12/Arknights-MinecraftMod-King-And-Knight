package com.nmmoc7.king_and_knight.item.weapon;

import com.nmmoc7.king_and_knight.capability.weapon.WeaponCapability;
import com.nmmoc7.king_and_knight.item.weapon.abstracts.ModLongRangeWeaponBase;
import com.nmmoc7.king_and_knight.item.weapon.skills.SkillData;
import com.nmmoc7.king_and_knight.item.weapon.skills.SkillFairyGuardian;
import com.nmmoc7.king_and_knight.item.weapon.skills.abstracts.AbstractSkill;
import com.nmmoc7.king_and_knight.item.weapon.skills.enums.AttackRangeType;
import com.nmmoc7.king_and_knight.itemgroup.ModItemGroups;
import com.nmmoc7.king_and_knight.model.IrisWeaponRender;

import static com.nmmoc7.king_and_knight.item.weapon.skills.enums.AttackRangeType.*;

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
