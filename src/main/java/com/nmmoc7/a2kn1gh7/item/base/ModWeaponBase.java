package com.nmmoc7.a2kn1gh7.item.base;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.item.ModWeapons;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public class ModWeaponBase extends Item implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private final String controllerName = "controller";

    public ModWeaponBase(String name, ItemGroup itemGroup, Supplier<Callable<ItemStackTileEntityRenderer>> render) {
        super(new Properties().group(itemGroup).setNoRepair().setISTER(render).maxStackSize(1));

        this.setRegistryName(new ResourceLocation(A2kn1gh7.MODID, name));

        ModWeapons.WEAPONS.add(this);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, this.controllerName, 1.0F, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        return false;
    }

    public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }
}
