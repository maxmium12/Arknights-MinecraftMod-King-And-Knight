package com.nmmoc7.kingandkinght.model;

import com.nmmoc7.kingandkinght.item.weapon.abstracts.AbstractWeapon;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

/**
 * @author DustW
 */
public class IrisWeaponRender extends GeoItemRenderer<AbstractWeapon> {
    public IrisWeaponRender() {
        super(new IrisWeaponModel());
    }
}
