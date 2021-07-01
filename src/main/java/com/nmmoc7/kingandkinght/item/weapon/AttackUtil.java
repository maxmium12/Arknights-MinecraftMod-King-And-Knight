package com.nmmoc7.kingandkinght.item.weapon;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.capability.ModCapabilities;
import com.nmmoc7.kingandkinght.capability.weapon.WeaponCapability;
import com.nmmoc7.kingandkinght.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.kingandkinght.item.weapon.skills.enums.AttackRangeType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class AttackUtil {
    public static boolean canAttack(ServerPlayerEntity player, Entity target) {
        if (target != player && player.getHeldItemMainhand().getItem() instanceof AbstractWeapon) {
            ItemStack weapon = player.getHeldItemMainhand();
            WeaponCapability cap = weapon.getCapability(ModCapabilities.WEAPON_CAPABILITY).orElse(null);

            double pointX = target.getPosX() - player.getPosX();
            double pointY = target.getPosZ() - player.getPosZ();
            double[] pointF = rotatePoint(pointX, pointY, -player.rotationYaw, true);

            AttackRangeType[][] attackRange = cap.getActiveAttackRange();
            int[] attackCore = cap.getAttackRangeCore();

            int indexX = (int) pointF[0] + attackCore[0];
            int indexY = (int) pointF[1] + attackCore[1];

            return indexX < attackRange.length && indexY < attackRange[0].length &&
                    indexX >= 0 && indexY >= 0 &&
                    attackRange[indexX][indexY] == AttackRangeType.Y;
        }

        return false;
    }

    public static double[] rotatePoint(double x, double y, double angle, boolean isDegrees) {
        if (isDegrees) {
            angle = Math.toRadians(angle);
        }

        double cos = MathHelper.cos((float) (angle));
        double sin = MathHelper.sin((float) (angle));

        return new double[]{
                x * cos - y * sin,
                x * sin + y * cos
        };
    }
}
