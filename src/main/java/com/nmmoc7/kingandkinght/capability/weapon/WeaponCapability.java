package com.nmmoc7.kingandkinght.capability.weapon;

import com.nmmoc7.kingandkinght.capability.ModCapabilities;
import com.nmmoc7.kingandkinght.item.weapon.WeaponUtil;
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

import java.util.Optional;

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

    private AttackRangeType[][] customAttackRange;

    private int level = 0;
    private int activeTier = 0;

    public int lastCoreTier = 0;
    private int[] attackRangeCore;
    private boolean attackRangeChanged;

    private double attackDamageM = 1;
    private int attackDamageP = 0;

    private double attackSpeedM = 1;
    private int attackSpeedP = 0;

    private int lastAttackTick = 0;

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

                    getActiveSkill().tick(player, weapon);

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

    public int getAttackSpeed() {
        return (int) (((AbstractWeapon) weapon.getItem()).getAttackSpeed() * getAttackSpeedM() + getAttackSpeedP());
    }

    public double getAttackSpeedM() {
        return attackSpeedM;
    }

    public void setAttackSpeedM(double attackSpeedM) {
        this.attackSpeedM = attackSpeedM;
    }

    public int getAttackSpeedP() {
        return attackSpeedP;
    }

    public void setAttackSpeedP(int attackSpeedP) {
        this.attackSpeedP = attackSpeedP;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDefaultAttackAmount() {
        return ((AbstractWeapon) weapon.getItem()).getBaseAttackAmount() + this.getLevel() * 5;
    }

    public int getAttackAmount() {
        return (int) (getDefaultAttackAmount() * attackDamageM + attackDamageP);
    }

    public void setCustomAttackRange(AttackRangeType[][] customAttackRange) {
        this.customAttackRange = customAttackRange;
        attackRangeChanged = true;
    }

    public AttackRangeType[][] getActiveAttackRange() {
        return Optional.ofNullable(customAttackRange).orElse(((AbstractWeapon) weapon.getItem()).getAttackRange(activeTier));
    }

    public int[] getAttackRangeCore() {
        if (attackRangeChanged || attackRangeCore == null) {
            setAttackRangeCore(WeaponUtil.searchAttackRangeCore(getActiveAttackRange()));
        }

        return attackRangeCore;
    }

    public void setAttackRangeCore(int[] attackRangeCore) {
        this.attackRangeCore = attackRangeCore;
    }

    public void setTier(int tier) {
        this.activeTier = tier;
        attackRangeChanged = true;
    }

    public boolean isAttackRangeChanged() {
        return attackRangeChanged;
    }

    public int getLastAttackTick() {
        return lastAttackTick;
    }

    public void setLastAttackTick(int lastAttackTick) {
        this.lastAttackTick = lastAttackTick;
    }

    public double getAttackDamageM() {
        return attackDamageM;
    }

    public void setAttackDamageM(double attackDamageM) {
        this.attackDamageM = attackDamageM;
    }

    public int getAttackDamageP() {
        return attackDamageP;
    }

    public void setAttackDamageP(int attackDamageP) {
        this.attackDamageP = attackDamageP;
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

    public AttackRangeType[][] toTypeArray(int[][] range) {
        if (range == null) {
            return null;
        }

        AttackRangeType[][] result = new AttackRangeType[range.length][range[0].length];

        for (int i = 0; i < range.length; i++) {
            for (int j = 0; j < range[0].length; j++) {
                result[i][j] = range[i][j] == 1 ? AttackRangeType.Y : range[i][j] == 2 ? AttackRangeType.O : AttackRangeType.N;
            }
        }

        return result;
    }

    public int[][] toIntArray(AttackRangeType[][] range) {
        if (range == null) {
            return null;
        }

        int[][] result = new int[range.length][range[0].length];

        for (int i = 0; i < range.length; i++) {
            for (int j = 0; j < range[0].length; j++) {
                result[i][j] = range[i][j] == AttackRangeType.N ? 0 : range[i][j] == AttackRangeType.O ? 2 : 1;
            }
        }

        return result;
    }

    public void sync(ServerPlayerEntity player) {
        ModNetworkManager.serverSendToPlayer(new WeaponCapabilitySyncServer(getActiveSkill().serializeNBT(), toIntArray(customAttackRange)), player);
    }
}
