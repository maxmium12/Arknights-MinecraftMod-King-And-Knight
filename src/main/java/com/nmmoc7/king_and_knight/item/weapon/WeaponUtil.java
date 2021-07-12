package com.nmmoc7.king_and_knight.item.weapon;

import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.item.weapon.skills.enums.AttackRangeType;

/**
 * @author DustW
 */
public class WeaponUtil {
    public static int[] searchAttackRangeCore(AttackRangeType[][] attackRange) {
        int[] result = new int[]{9999, 9999};

        for (int i = 0; i < attackRange.length; i++) {
            AttackRangeType[] attackRangeLine = attackRange[i];

            for (int j = 0; j < attackRangeLine.length; j++) {
                if (attackRange[i][j] == AttackRangeType.O) {
                    result = new int[]{i, j};
                }
            }
        }

        if (result[0] == 9999) {
            KingAndKnight.LOGGER.warn("查找失败！请确定你有核心！");
        }

        return result;
    }
}
