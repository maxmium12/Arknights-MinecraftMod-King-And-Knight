package com.nmmoc7.a2kn1gh7.item;

import com.nmmoc7.a2kn1gh7.item.base.ModWeaponBase;
import com.nmmoc7.a2kn1gh7.itemgroup.ModItemGroups;
import com.nmmoc7.a2kn1gh7.model.IrisWeaponRender;
import net.minecraft.item.Item;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ModWeapons {
    public static final ArrayList<ModWeaponBase> WEAPONS = new ArrayList<>();

    public static final ModWeaponBase TEST_WEAPON = new ModWeaponBase("irisweapon", ModItemGroups.WEAPON_GROUP, () -> IrisWeaponRender::new);
}
