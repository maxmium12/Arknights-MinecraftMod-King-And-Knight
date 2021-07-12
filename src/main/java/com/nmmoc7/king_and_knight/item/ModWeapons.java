package com.nmmoc7.king_and_knight.item;

import com.nmmoc7.king_and_knight.item.weapon.IrisWeapon;
import com.nmmoc7.king_and_knight.item.weapon.abstracts.AbstractWeapon;

import java.util.ArrayList;

/**
 * @author DustW
 */
public class ModWeapons {
    public static final ArrayList<AbstractWeapon> WEAPONS = new ArrayList<>();

    public static final AbstractWeapon IRIS_WEAPON = new IrisWeapon();
}
