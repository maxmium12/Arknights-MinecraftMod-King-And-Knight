package com.nmmoc7.kingandkinght.item.weapon.abstracts;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.ModServerCounter;
import com.nmmoc7.kingandkinght.capability.ModCapabilities;
import com.nmmoc7.kingandkinght.capability.weapon.WeaponCapability;
import com.nmmoc7.kingandkinght.capability.weapon.WeaponCapabilityProvider;
import com.nmmoc7.kingandkinght.item.InformationHelper;
import com.nmmoc7.kingandkinght.item.ModWeapons;
import com.nmmoc7.kingandkinght.item.weapon.AttackUtil;
import com.nmmoc7.kingandkinght.item.weapon.WeaponUtil;
import com.nmmoc7.kingandkinght.item.weapon.skills.SkillData;
import com.nmmoc7.kingandkinght.item.weapon.skills.abstracts.AbstractSkill;
import com.nmmoc7.kingandkinght.item.weapon.skills.enums.AttackRangeType;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public abstract class AbstractWeapon extends Item implements IAnimatable {
    private final AnimationFactory factory = new AnimationFactory(this);
    private final String controllerName = "controller";

    public int switchSkillCost;
    /** 多少tick能进行一次攻击 */
    private final int attackSpeed;
    private final int baseAttackAmount;

    public final Map<Integer, AttackRangeType[][]> tierToAttackRangeMap = new HashMap<>();

    public AbstractWeapon(String name,
                          int baseAttackAmount,
                          int switchSkillCost,
                          int attackSpeed,
                          ItemGroup itemGroup,
                          Supplier<Callable<ItemStackTileEntityRenderer>> render) {
        super(new Properties().group(itemGroup).setNoRepair().setISTER(render).maxStackSize(1));

        this.setRegistryName(new ResourceLocation(KingAndKnight.MOD_ID, name));
        this.switchSkillCost = switchSkillCost;

        this.attackSpeed = attackSpeed;
        this.baseAttackAmount = baseAttackAmount;

        ModWeapons.WEAPONS.add(this);
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        WeaponCapabilityProvider capability = new WeaponCapabilityProvider();

        capability.getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(this::initCap);
        capability.getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> theCap.bindWeapon(stack));

        return capability;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        stack.getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> {
            InformationHelper.addInformation(tooltip,
                    " ",
                    "技能: ",
                    "    技能1: ",
                    "        " + theCap.SKILLS.get(0).getDisplayName().getString(),
                    "        " + theCap.SKILLS.get(0).getDisplayDoc().getString(),
                    "    技能2: ",
                    "        " + theCap.SKILLS.get(1).getDisplayName().getString(),
                    "        " + theCap.SKILLS.get(1).getDisplayDoc().getString(),
                    "    技能3: ",
                    "        " + theCap.SKILLS.get(2).getDisplayName().getString(),
                    "        " + theCap.SKILLS.get(2).getDisplayDoc().getString(),
                    " ",
                    "天赋: ",
                    "    天赋1: ",
                    "        " + theCap.TALENTS.get(0).getDisplayName().getString(),
                    "        " + theCap.TALENTS.get(0).getDisplayDoc().getString(),
                    "    天赋2: ",
                    "        " + theCap.TALENTS.get(1).getDisplayName().getString(),
                    "        " + theCap.TALENTS.get(1).getDisplayDoc().getString(),
                    " ");
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
        if (entityIn instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) entityIn;
            WeaponCapability cap = stack.getCapability(ModCapabilities.WEAPON_CAPABILITY).orElse(null);
            cap.tick(player);
        }
    }

    public int getBaseAttackAmount() {
        return baseAttackAmount;
    }

    public void setSwitchSkillCost(int switchSkillCost) {
        this.switchSkillCost = switchSkillCost;
    }

    public int getSwitchSkillCost() {
        return switchSkillCost;
    }

    public int getAttackSpeed() {
        return attackSpeed;
    }

    public AttackRangeType[][] getAttackRange(int tier) {
        return tierToAttackRangeMap.get(tier);
    }

    public void addTierToAttackRange(int tier, AttackRangeType[][] attackRangeTypes) {
        tierToAttackRangeMap.put(tier, attackRangeTypes);
    }

    public void onLeftClick(ServerPlayerEntity player, ItemStack weapon) {
        WeaponCapability cap = weapon.getCapability(ModCapabilities.WEAPON_CAPABILITY).orElse(null);

        if (ModServerCounter.count - cap.getLastAttackTick() >= cap.getAttackSpeed()) {
            AttackRangeType[][] attackRange = cap.getActiveAttackRange();

            double exRange = MathHelper.sqrt(
                    (attackRange.length * attackRange.length) +
                            (attackRange[0].length / 2f) * (attackRange[0].length / 2f));
            int rangeUp = 2;
            int rangeBottom = 1;

            List<LivingEntity> entityList = player.world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(
                    player.getPosX() + exRange,
                    player.getPosY() + rangeUp,
                    player.getPosZ() + exRange,
                    player.getPosX() - exRange,
                    player.getPosY() - rangeBottom,
                    player.getPosZ() - exRange
            ));

            for (LivingEntity entity : entityList) {
                if (AttackUtil.canAttack(player, entity) && entity.isAlive()) {
                    entity.attackEntityFrom(DamageSource.MAGIC, cap.getAttackAmount());

                    if (cap.getActiveSkill().isFinish() && cap.getActiveSkill().getDurationType() == AbstractSkill.DurationType.ATTACK) {
                        cap.getActiveSkill().castAttack(player, entity, weapon);
                    }

                    cap.setLastAttackTick(ModServerCounter.count);
                    break;
                }
            }
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            WeaponCapability cap = playerIn.getHeldItem(handIn).getCapability(ModCapabilities.WEAPON_CAPABILITY).orElse(null);

            if (playerIn.isSneaking()) {
                cap.switchSkill((ServerPlayerEntity) playerIn);
            }
            else {
                cap.getActiveSkill().cast((ServerPlayerEntity) playerIn, playerIn.getHeldItem(handIn));
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    /**
     * 初始化Capability
     * @param capability Capability
     */
    public abstract void initCap(WeaponCapability capability);
}
