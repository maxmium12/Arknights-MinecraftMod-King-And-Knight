package com.nmmoc7.a2kn1gh7.item;

import com.nmmoc7.a2kn1gh7.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.a2kn1gh7.item.weapon.IrisWeapon;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ModWeapons {
    public static final ArrayList<AbstractWeapon> WEAPONS = new ArrayList<>();

    public static final AbstractWeapon IRIS_WEAPON = new IrisWeapon();
}
