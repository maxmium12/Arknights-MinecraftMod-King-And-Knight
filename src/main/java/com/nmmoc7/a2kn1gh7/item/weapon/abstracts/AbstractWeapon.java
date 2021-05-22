package com.nmmoc7.a2kn1gh7.item.base;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import com.nmmoc7.a2kn1gh7.capability.ModCapabilities;
import com.nmmoc7.a2kn1gh7.capability.weapon.WeaponCapability;
import com.nmmoc7.a2kn1gh7.capability.weapon.WeaponCapabilityProvider;
import com.nmmoc7.a2kn1gh7.item.InformationHelper;
import com.nmmoc7.a2kn1gh7.item.ModWeapons;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.SkillData;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.abstracts.AbstractSkill;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

import static com.nmmoc7.a2kn1gh7.ModServerCounter.TPS;

/**
 * @author DustW
 */
public abstract class ModWeaponBase extends Item implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private final String controllerName = "controller";

    public ModWeaponBase(String name, ItemGroup itemGroup, Supplier<Callable<ItemStackTileEntityRenderer>> render) {
        super(new Properties().group(itemGroup).setNoRepair().setISTER(render).maxStackSize(1));

        this.setRegistryName(new ResourceLocation(A2kn1gh7.MODID, name));

        ModWeapons.WEAPONS.add(this);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        WeaponCapabilityProvider capability = new WeaponCapabilityProvider();

        capability.getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(this::init);

        return capability;
    }

    @Nullable
    @Override
    public CompoundNBT getShareTag(ItemStack stack) {
        CompoundNBT nbt = Optional.ofNullable(super.getShareTag(stack)).orElseGet(CompoundNBT::new);

        stack.getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> {
            nbt.put("skillUpdate", theCap.activeSkill.serializeNBT());
        });

        return nbt;
    }

    @Override
    public void readShareTag(ItemStack stack, @Nullable CompoundNBT nbt) {
        super.readShareTag(stack, nbt);

        stack.getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> {
            theCap.activeSkill.deserializeNBT(nbt.getCompound("skillUpdate"));
        });
    }

    @Override
    public boolean shouldSyncTag() {
        return true;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        LazyOptional<WeaponCapability> capability = stack.getCapability(ModCapabilities.WEAPON_CAPABILITY);

        capability.ifPresent(theCap -> {
            InformationHelper.addInformation(tooltip,
                    "当前技能: ",
                    "    " + theCap.activeSkill.getDisplayName().getString(),
                    "    技力: " + theCap.activeSkill.getSkillPoint() + "/" + theCap.activeSkill.getMaxCoolDownTime(),
                    "    技能持续时间: " + (theCap.activeSkill.getDurationTime()) + "/" + theCap.activeSkill.getMaxDurationTime(),
                    " ",
                    "所有技能: ",
                    "    技能1: ",
                    "        " + theCap.SKILLS.get(0).getDisplayName().getString(),
                    "        " + theCap.SKILLS.get(0).getDisplayDoc().getString(),
                    "    技能2: ",
                    "        " + theCap.SKILLS.get(1).getDisplayName().getString(),
                    "        " + theCap.SKILLS.get(1).getDisplayDoc().getString(),
                    "    技能3: ",
                    "        " + theCap.SKILLS.get(2).getDisplayName().getString(),
                    "        " + theCap.SKILLS.get(2).getDisplayDoc().getString(),
                    "天赋: ",
                    "    天赋1: ",
                    "        " + theCap.TALENTS.get(0).getDisplayName().getString(),
                    "        " + theCap.TALENTS.get(0).getDisplayDoc().getString(),
                    "    天赋2: ",
                    "        " + theCap.TALENTS.get(1).getDisplayName().getString(),
                    "        " + theCap.TALENTS.get(1).getDisplayDoc().getString());
        });
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

    @Override
    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return false;
    }

    public <P extends Item & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote) {
            stack.getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> {
                theCap.tick();
            });
        }
    }

    /**
     * 初始化Capability
     * @param capability Capability
     */
    public abstract void init(WeaponCapability capability);
}
