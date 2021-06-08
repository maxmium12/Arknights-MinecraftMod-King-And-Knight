package com.nmmoc7.kingandkinght.model;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.item.weapon.abstracts.AbstractWeapon;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * @author DustW
 */
public class IrisWeaponModel extends AnimatedGeoModel<AbstractWeapon> {
    @Override
    public ResourceLocation getModelLocation(AbstractWeapon object) {
        return new ResourceLocation(KingAndKnight.MOD_ID, "geo/iris_weapon.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractWeapon object) {
        return new ResourceLocation(KingAndKnight.MOD_ID, "textures/item/iris_weapon.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(AbstractWeapon animatable) {
        return new ResourceLocation(KingAndKnight.MOD_ID);
    }
}
