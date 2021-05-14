package com.nmmoc7.a2kn1gh7.model;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.item.base.ModWeaponBase;
import net.minecraft.util.ResourceLocation;
import software.bernie.example.item.JackInTheBoxItem;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * @author DustW
 */
public class IrisWeaponModel extends AnimatedGeoModel<ModWeaponBase> {
    @Override
    public ResourceLocation getModelLocation(ModWeaponBase object) {
        return new ResourceLocation(A2kn1gh7.MODID, "geo/irisweapon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(ModWeaponBase object) {
        return new ResourceLocation(A2kn1gh7.MODID, "textures/item/irisweapon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(ModWeaponBase animatable) {
        return new ResourceLocation(A2kn1gh7.MODID);
    }
}
