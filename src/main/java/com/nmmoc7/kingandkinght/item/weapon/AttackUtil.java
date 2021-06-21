package com.nmmoc7.kingandkinght.item.weapon;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.capability.ModCapabilities;
import com.nmmoc7.kingandkinght.capability.weapon.WeaponCapability;
import com.nmmoc7.kingandkinght.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.kingandkinght.item.weapon.skills.enums.AttackRangeType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

/**
 * x = r cos(t)
 * y = r sin(t)
 * @author DustW
 */
public class AttackUtil {
    public static double radians = 180 / Math.PI;

    public static boolean canAttack(ServerPlayerEntity player, Entity target) {
        if (player.getHeldItemMainhand().getItem() instanceof AbstractWeapon) {
            ItemStack weapon = player.getHeldItemMainhand();
            AbstractWeapon weaponItem = (AbstractWeapon) player.getHeldItemMainhand().getItem();

            double[] point = rotatePoint(new double[] {
                    target.getPosX() - player.getPosX(),
                    target.getPosZ() - player.getPosZ()}, player.cameraYaw);

            int[] indexPoint = new int[] {
                    Math.toIntExact(Math.round(point[0])),
                    Math.toIntExact(Math.round(point[1]))};

            WeaponCapability cap = weapon.getCapability(ModCapabilities.WEAPON_CAPABILITY).orElse(null);

            AttackRangeType[][] attackRange = cap.getActiveAttackRange();

            try {
                if (attackRange[indexPoint[0]][indexPoint[1]] == AttackRangeType.Y) {
                    KingAndKnight.LOGGER.warn(target.toString());
                    return true;
                }
            }
            catch (ArrayIndexOutOfBoundsException ignored) {
                KingAndKnight.LOGGER.warn("x: " + indexPoint[0] + "z: " + indexPoint[1]);
            }
        }

        return false;
    }

    public static double[] rotatePoint(double[] point, double angle) {
        return new double[] {
                Math.sqrt(point[0] + point[1]) * Math.cos(angle * radians),
                Math.sqrt(point[0] + point[1]) * Math.sin(angle * radians)
        };
    }
}
