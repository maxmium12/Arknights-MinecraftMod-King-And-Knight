package com.nmmoc7.a2kn1gh7.model;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.item.weapon.abstracts.AbstractWeapon;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * @author DustW
 */
public class IrisWeaponModel extends AnimatedGeoModel<AbstractWeapon> {
    @Override
    public ResourceLocation getModelLocation(AbstractWeapon object) {
        return new ResourceLocation(A2kn1gh7.MODID, "geo/iris_weapon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractWeapon object) {
        return new ResourceLocation(A2kn1gh7.MODID, "textures/item/iris_weapon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AbstractWeapon animatable) {
        return new ResourceLocation(A2kn1gh7.MODID);
    }
}
