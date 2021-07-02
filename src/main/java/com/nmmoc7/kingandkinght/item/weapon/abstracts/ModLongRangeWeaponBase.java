package com.nmmoc7.kingandkinght.item.weapon.abstracts;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public abstract class ModLongRangeWeaponBase extends AbstractWeapon {
    public ModLongRangeWeaponBase(String name, int baseAttackAmount, int switchSkillCost, int attackSpeed, ItemGroup itemGroup, Supplier<Callable<ItemStackTileEntityRenderer>> render) {
        super(name, baseAttackAmount, switchSkillCost, attackSpeed, itemGroup, render);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        return true;
    }
}
