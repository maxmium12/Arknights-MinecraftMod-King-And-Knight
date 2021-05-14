package com.nmmoc7.a2kn1gh7.model;

import com.nmmoc7.a2kn1gh7.item.base.ModWeaponBase;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

/**
 * @author DustW
 */
public class IrisWeaponRender extends GeoItemRenderer<ModWeaponBase> {
    public IrisWeaponRender() {
        super(new IrisWeaponModel());
    }
}
