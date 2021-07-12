package com.nmmoc7.king_and_knight.item.weapon.skills;

import com.nmmoc7.king_and_knight.KingAndKnight;
import com.nmmoc7.king_and_knight.item.weapon.skills.abstracts.AbstractSkill;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * @author DustW
 */
public class SkillData extends CapabilityProvider<SkillData> implements INBTSerializable<CompoundNBT> {
    private AbstractSkill skill;
    int level;
    private int skillPoint;
    private int durationTime;

    private boolean isFinish;

    public static final SkillData EMPTY_SKILL = new SkillData(null);

    public SkillData(AbstractSkill skill) {
        super(SkillData.class);

        this.skill = skill;
    }

    public void init() {
        if (!isEmpty()) {
            if (skill.hasWarmupTime()) {
                if (skillPointCanChange()) {
                    setSkillPoint(skill.getWarmupTime(level));
                    isFinish = true;
                }
            }
        }
    }

    public void reset() {
        if (!isEmpty()) {
            if (skillPointCanChange()) {
                setSkillPoint(0);
                isFinish = true;
            }
        }
    }

    public void tick(ServerPlayerEntity player, ItemStack weapon) {
        if (!isFinish()) {
            if (getDurationType() == AbstractSkill.DurationType.NATURAL) {
                castTick(player, weapon);
            }

            if (getDurationType() != AbstractSkill.DurationType.NULL && getDurationTime() == 0) {
                finish(player, weapon);
            }
        }
    }

    public void cast(ServerPlayerEntity player, ItemStack weapon) {
        if (skillPoint >= getMaxSkillPoint() && castBegin(player, weapon) == AbstractSkill.CastResult.SUCCESS) {
            setDurationTime(skill.getMaxDurationTime(level));
            isFinish = false;
        }
    }

    public void finish(ServerPlayerEntity player, ItemStack weapon) {
        castFinish(player, weapon);
        reset();
        isFinish = true;
    }

    private AbstractSkill.CastResult castBegin(ServerPlayerEntity player, ItemStack weapon) {
        return skill.castBegin(player, weapon, this);
    }

    public void castTick(ServerPlayerEntity player, ItemStack weapon) {
        skill.castTick(player, weapon, this, durationTime);
    }

    public void castAttack(ServerPlayerEntity player, Entity target, ItemStack weapon) {
        skill.castAttack(player, target, weapon, this);
    }

    public void castUnderAttack(ServerPlayerEntity player, Entity attacker, ItemStack weapon) {
        skill.castUnderAttack(player, attacker, weapon, this);
    }

    private void castFinish(ServerPlayerEntity player, ItemStack weapon) {
        skill.castFinish(player, weapon, this);
    }

    public boolean isEmpty() {
        return skill == null;
    }

    public boolean skillPointCanChange() {
        return getSkill().getCoolDownType() != AbstractSkill.SkillPointType.NULL;
    }

    public void addSkillPoint(int amount) {
        if (skillPointCanChange() && skillPoint < getMaxSkillPoint()) {
            if (skillPoint + amount >= 0) {
                skillPoint += amount;
            }
        }
    }

    public void setSkillPoint(int amount) {
        if (amount >= 0 && skillPointCanChange()) {
            this.skillPoint = amount;
        }
    }

    public boolean durationTimeCanChange() {
        return getSkill().getDurationType() != AbstractSkill.DurationType.NULL;
    }

    public void sharkDurationTime(int amount) {
        if (durationTimeCanChange() && durationTime > 0) {
            if (durationTime - amount >= 0) {
                durationTime -= amount;
            } else {
                durationTime = 0;
            }
        }
    }

    public void setDurationTime(int amount) {
        if (amount >= 0 && durationTimeCanChange()) {
            this.durationTime = amount;
        }
    }

    public int getSkillPoint() {
        return skillPoint;
    }

    public int getMaxSkillPoint() {
        if (!isEmpty()) {
            return skill.getMaxCoolDownTime(level);
        }
        else {
            return 0;
        }
    }

    public int getDurationTime() {
        return durationTime;
    }

    public int getMaxDurationTime() {
        if (!isEmpty()) {
            return skill.getMaxDurationTime(level);
        }
        else {
            return 0;
        }
    }

    public AbstractSkill getSkill() {
        return skill;
    }

    public boolean isFinish() {
        return isFinish;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT compoundNBT = new CompoundNBT();
        if (skill != null) {
            compoundNBT.putString("skillRegName", this.skill.getRegistryName());
            compoundNBT.putInt("skillPoint", this.skillPoint);
            compoundNBT.putInt("durationTime", this.durationTime);

            compoundNBT.putBoolean("empty", false);
        }
        else {
            compoundNBT.putBoolean("empty", true);
        }
        return compoundNBT;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        if (!nbt.getBoolean("empty")) {
            this.skill = AbstractSkill.getSkill(nbt.getString("skillRegName"));
            skillPoint = nbt.getInt("skillPoint");
            durationTime = nbt.getInt("durationTime");
        }
        else {
            this.skill = null;
        }
    }

    public SkillData copy() {
        if (this.isEmpty()) {
            return EMPTY_SKILL;
        } else {
            SkillData skillData = new SkillData(this.getSkill());
            skillData.skillPoint = this.skillPoint;
            skillData.durationTime = this.durationTime;

            return skillData;
        }
    }

    public ITextComponent getDisplayName() {
        if (isEmpty()) {
            return new TranslationTextComponent(Util.makeTranslationKey("skill", new ResourceLocation(KingAndKnight.MOD_ID, "empty")));
        }

        return new TranslationTextComponent(skill.getDefaultTranslationKey());
    }

    public ITextComponent getDisplayDoc() {
        if (isEmpty()) {
            return new TranslationTextComponent(Util.makeTranslationKey("skill.doc", new ResourceLocation(KingAndKnight.MOD_ID, "empty")));
        }

        return new TranslationTextComponent(skill.getDefaultDocTranslationKey());
    }

    public AbstractSkill.SkillPointType getSkillPointType() {
        if (isEmpty()) {
            return AbstractSkill.SkillPointType.NULL;
        }
        else {
            return skill.getCoolDownType();
        }
    }

    public AbstractSkill.DurationType getDurationType() {
        if (isEmpty()) {
            return AbstractSkill.DurationType.NULL;
        }
        else {
            return skill.getDurationType();
        }
    }
}
