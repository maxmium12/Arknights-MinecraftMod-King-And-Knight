package com.nmmoc7.kingandkinght.capability.weapon;

import com.nmmoc7.kingandkinght.capability.ModCapabilities;
import com.nmmoc7.kingandkinght.item.weapon.abstracts.AbstractWeapon;
import com.nmmoc7.kingandkinght.item.weapon.skills.SkillData;
import com.nmmoc7.kingandkinght.item.weapon.skills.abstracts.AbstractSkill;
import com.nmmoc7.kingandkinght.item.weapon.skills.enums.AttackRangeType;
import com.nmmoc7.kingandkinght.network.ModNetworkManager;
import com.nmmoc7.kingandkinght.network.server.WeaponCapabilitySyncServer;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;

import static com.nmmoc7.kingandkinght.ModServerCounter.TPS;

/**
 * @author DustW
 */
public class WeaponCapability implements INBTSerializable<CompoundNBT> {
    public ItemStack weapon;

    public void bindWeapon(ItemStack weapon) {this.weapon = weapon;}

    public final NonNullList<SkillData> SKILLS = NonNullList.withSize(3, SkillData.EMPTY_SKILL);
    public final NonNullList<SkillData> TALENTS = NonNullList.withSize(2, SkillData.EMPTY_SKILL);

    public int activeSkillIndex;

    public SkillData getActiveSkill() {
        return SKILLS.get(activeSkillIndex);
    }

    public AttackRangeType[][] customAttackRange;

    public int activeTier = 0;

    public int lastCoreTier = 0;
    private int[] attackRangeCore;

    public int tickCount = 0;

    public void tick(ServerPlayerEntity player) {
        tickCount++;

        if (getActiveSkill().durationTimeCanChange()) {
            if (tickCount % TPS == 0) {
                if (getActiveSkill().getDurationType() == AbstractSkill.DurationType.NATURAL) {
                    getActiveSkill().sharkDurationTime(1);

                    if (getActiveSkill().getDurationTime() == 0) {
                        if (getActiveSkill().getSkillPointType() == AbstractSkill.SkillPointType.NATURAL) {
                            getActiveSkill().addSkillPoint(1);
                        }
                    }

                    if (player.getHeldItemMainhand().getItem() instanceof AbstractWeapon) {
                        sync(player);
                    }
                }

                //TODO 自动施法
            }
        }
    }

    public void switchSkill(ServerPlayerEntity player) {
        if (weapon.getItem() instanceof AbstractWeapon) {
            SkillData originSkill = getActiveSkill().copy();

            if (activeSkillIndex < 2 && SKILLS.get(activeSkillIndex + 1) != SkillData.EMPTY_SKILL) {
                activeSkillIndex++;
            } else {
                activeSkillIndex = 0;
            }

            player.getCapability(ModCapabilities.COST_CAPABILITY).ifPresent(theCap -> {
                theCap.addCost(-((AbstractWeapon) weapon.getItem()).getSwitchSkillCost(), player);
            });

            if (originSkill.getSkill() != getActiveSkill().getSkill()) {
                getActiveSkill().init();

                tickCount = 0;
            }
        }
    }

    public void defaultInit() {
        activeSkillIndex = 0;

        getActiveSkill().init();
    }

    public void setCustomAttackRange(AttackRangeType[][] customAttackRange) {
        this.customAttackRange = customAttackRange;
    }

    public int[] getAttackRangeCore() {
        return attackRangeCore;
    }

    public void setAttackRangeCore(int[] attackRangeCore) {
        this.attackRangeCore = attackRangeCore;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();

        ListNBT skillList = new ListNBT();
        SKILLS.forEach(skill -> {
            CompoundNBT skillNBT = skill.serializeNBT();
            skillList.add(skillNBT);
        });
        compoundNBT.put("skillList", skillList);

        ListNBT talentList = new ListNBT();
        TALENTS.forEach(talent -> {
            CompoundNBT talentNBT = talent.serializeNBT();
            talentList.add(talentNBT);
        });
        compoundNBT.put("talentList", talentList);

        compoundNBT.putInt("activeSkillIndex", activeSkillIndex);

        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        ListNBT skillList = nbt.getList("skillList", 9);
        skillList.forEach(skillNBT -> {
            SkillData skillData = new SkillData(null);
            skillData.deserializeNBT((CompoundNBT) skillNBT);
            SKILLS.add(skillData);
        });

        ListNBT talentList = nbt.getList("talentList", 9);
        talentList.forEach(talentNBT -> {
            SkillData skillData = new SkillData(null);
            skillData.deserializeNBT((CompoundNBT) talentNBT);
            TALENTS.add(skillData);
        });

        activeSkillIndex = nbt.getInt("activeSkillIndex");
    }

    public void sync(ServerPlayerEntity player) {
        ModNetworkManager.serverSendToPlayer(new WeaponCapabilitySyncServer(getActiveSkill().serializeNBT()), player);
    }
}
