package com.nmmoc7.a2kn1gh7.capability.weapon;

import com.nmmoc7.a2kn1gh7.capability.ModCapabilities;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.SkillData;
import com.nmmoc7.a2kn1gh7.item.weapon.skills.abstracts.AbstractSkill;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;

import static com.nmmoc7.a2kn1gh7.ModServerCounter.TPS;

/**
 * @author DustW
 */
public class WeaponCapability implements INBTSerializable<CompoundNBT> {
    public final NonNullList<SkillData> SKILLS = NonNullList.withSize(3, SkillData.EMPTY_SKILL);
    public final NonNullList<SkillData> TALENTS = NonNullList.withSize(2, SkillData.EMPTY_SKILL);

    public int activeSkillIndex;

    public SkillData getActiveSkill() {
        return SKILLS.get(activeSkillIndex);
    }

    public int tickCount = 0;

    public void tick() {
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
                }

                //TODO 自动施法
            }
        }
    }

    public void switchSkill(ServerPlayerEntity player) {
        if (activeSkillIndex < 2 && SKILLS.get(activeSkillIndex + 1) != SkillData.EMPTY_SKILL) {
            SkillData nextSkill = SKILLS.get(activeSkillIndex + 1);

            player.getCapability(ModCapabilities.COST_CAPABILITY).ifPresent(theCap -> {
                if (theCap.cost > nextSkill.getSkill().getCost()) {
                    theCap.addCost(-nextSkill.getSkill().getCost(), player);
                    activeSkillIndex++;
                }
            });
        }
        else {
            player.getCapability(ModCapabilities.COST_CAPABILITY).ifPresent(theCap -> {
                if (theCap.cost > SKILLS.get(0).getSkill().getCost()) {
                    theCap.addCost(-SKILLS.get(0).getSkill().getCost(), player);
                    activeSkillIndex = 0;
                }
            });
        }

        getActiveSkill().init();

        tickCount = 0;
    }

    public void init(NonNullList<SkillData> skills, NonNullList<SkillData> talents) {
        SKILLS.set(0, skills.get(0));
        SKILLS.set(1, skills.get(1));
        SKILLS.set(2, skills.get(2));

        TALENTS.set(0, talents.get(0));
        TALENTS.set(1, talents.get(1));

        activeSkillIndex = 0;

        getActiveSkill().init();
    }

    public void defaultInit() {
        activeSkillIndex = 0;

        getActiveSkill().init();
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
}
