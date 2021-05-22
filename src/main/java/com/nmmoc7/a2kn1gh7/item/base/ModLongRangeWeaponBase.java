package com.nmmoc7.a2kn1gh7.item.base;

import com.nmmoc7.a2kn1gh7.capability.weapon.WeaponCapability;
import com.nmmoc7.a2kn1gh7.item.weapon.abstracts.AbstractWeapon;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.ItemGroup;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public class ModLongRangeWeaponBase extends AbstractWeapon {
    public ModLongRangeWeaponBase(String name, ItemGroup itemGroup, Supplier<Callable<ItemStackTileEntityRenderer>> render) {
        super(name, itemGroup, render);
    }

    @Override
    public void init(WeaponCapability capability) {

    }
}
