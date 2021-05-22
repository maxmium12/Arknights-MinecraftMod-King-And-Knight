package com.nmmoc7.a2kn1gh7.item.weapon;

import com.nmmoc7.a2kn1gh7.capability.ModCapabilities;
import com.nmmoc7.a2kn1gh7.capability.weapon.WeaponCapability;
import com.nmmoc7.a2kn1gh7.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.SkillData;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.TestSkill;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.abstracts.AbstractSkill;
import com.nmmoc7.a2kn1gh7.itemgroup.ModItemGroups;
import com.nmmoc7.a2kn1gh7.model.IrisWeaponRender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

/**
 * @author DustW
 */
public class IrisWeapon extends AbstractWeapon {
    public IrisWeapon() {
        super("iris_weapon", ModItemGroups.WEAPON_GROUP, () -> IrisWeaponRender::new);
    }

    @Override
    public boolean onLeftClickEntity(ItemStack stack, PlayerEntity player, Entity entity) {
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {
            if (playerIn.isSneaking()) {
                playerIn.getHeldItem(handIn).getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> {
                    theCap.switchSkill((ServerPlayerEntity) playerIn);
                });
            }
            else {
                playerIn.getHeldItem(handIn).getCapability(ModCapabilities.WEAPON_CAPABILITY).ifPresent(theCap -> {
                    playerIn.sendMessage(new StringTextComponent(playerIn.getHeldItemMainhand().serializeNBT().getString()), playerIn.getUniqueID());
                    theCap.getActiveSkill().defaultCast();
                });
            }
        }

        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Override
    public void init(WeaponCapability capability) {
        capability.defaultInit();
        capability.SKILLS.set(0, new SkillData(AbstractSkill.registerSkill(TestSkill::new)));
        capability.SKILLS.set(1, new SkillData(AbstractSkill.registerSkill(TestSkill::new)));
        capability.SKILLS.set(2, new SkillData(AbstractSkill.registerSkill(TestSkill::new)));
    }
}
