package com.nmmoc7.king_and_knight.item.weapon.abstracts;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemGroup;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public abstract class ModShortRangeWeaponBase extends AbstractWeapon {
    public ModShortRangeWeaponBase(String name, int baseAttackAmount, int switchSkillCost, int attackSpeed, ItemGroup itemGroup, Supplier<Callable<ItemStackTileEntityRenderer>> render) {
        super(name, baseAttackAmount, switchSkillCost, attackSpeed, itemGroup, render);
    }
}
