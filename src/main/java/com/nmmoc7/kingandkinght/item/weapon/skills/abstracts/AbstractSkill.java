package com.nmmoc7.kingandkinght.item.weapon.skills.abstracts;

import com.nmmoc7.kingandkinght.KingAndKnight;
import com.nmmoc7.kingandkinght.item.weapon.skills.SkillData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public abstract class AbstractSkill {
    /** 如果是false，切换技能后从零开始计数 */
    protected boolean hasWarmupTime;

    protected DurationType durationType;

    protected SkillPointType skillPointType;
    /** 如果是true，在冷却时间结束后自动开启 */
    protected boolean isAutoCast;

    protected int maxSkillLevel;

    private String registryName;
    private String translationKey;
    private String docTranslationKey;

    private static final Map<String, AbstractSkill> SKILLS = new HashMap<>();

    public static AbstractSkill registerSkill(Supplier<AbstractSkill> skillSupplier) {
        AbstractSkill skill = skillSupplier.get();

        if (!SKILLS.containsValue(skill)) {
            if (skill.getRegistryName() != null) {
                SKILLS.put(skill.registryName, skill);

                return getSkill(skill.registryName);
            }
            else {
                KingAndKnight.LOGGER.warn("注册名不能为空");

                return null;
            }
        }
        else {
            KingAndKnight.LOGGER.warn("已经注册过了");

            return getSkill(skill.registryName);
        }
    }

    public static AbstractSkill getSkill(String registryName) {
        if (SKILLS.containsKey(registryName)) {
            return SKILLS.get(registryName);
        } else {
            KingAndKnight.LOGGER.warn("没有这个技能！");
            return null;
        }
    }

    public String getDefaultTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("skill", new ResourceLocation(KingAndKnight.MOD_ID, this.registryName));
        }

        return this.translationKey;
    }

    public String getDefaultDocTranslationKey() {
        if (this.docTranslationKey == null) {
            this.docTranslationKey = Util.makeTranslationKey("skill.doc", new ResourceLocation(KingAndKnight.MOD_ID, this.registryName));
        }

        return this.docTranslationKey;
    }

    public SkillPointType getCoolDownType() {
        return skillPointType;
    }

    /**
     * 最大冷却
     * @param level 等级
     * @return maxCoolDown
     */
    public abstract int getMaxCoolDownTime(int level);

    public DurationType getDurationType() {
        return durationType;
    }

    /**
     * 最大持续时间
     * @param level 等级
     * @return maxDurationTime
     */
    public abstract int getMaxDurationTime(int level);

    public void setRegistryName(String registryName) {
        this.registryName = registryName;
    }

    public String getRegistryName() {
        return registryName;
    }

    public boolean hasWarmupTime() {
        return hasWarmupTime;
    }

    /**
     * 预热时间
     * @param level 等级
     * @return warmupTime
     */
    public abstract int getWarmupTime(int level);

    public int getMaxSkillLevel() {
        return maxSkillLevel;
    }

    /**
     * 技能开始释放
     * @param player 释放者
     * @param weapon 武器
     * @param skillData 技能
     * @return 如果是SUCCESS则继续执行， FAIL就不继续执行
     */
    public abstract CastResult castBegin(ServerPlayerEntity player, ItemStack weapon, SkillData skillData);

    /**
     * 在NATURAL的情况下激活这个
     * @param player 施法者
     * @param weapon 武器
     * @param skillData 技能
     * @param tick 从技能释放开始经过的tick
     */
    public abstract void castTick(ServerPlayerEntity player, ItemStack weapon, SkillData skillData, int tick);

    /**
     * 在ATTACK的时候激活这个
     * @param player 施法者
     * @param target 攻击的目标
     * @param weapon 武器
     * @param skillData 技能
     */
    public abstract void castAttack(ServerPlayerEntity player, Entity target, ItemStack weapon, SkillData skillData);

    /**
     * 在UNDER_ATTACK的情况下激活这个
     * @param player 施法者
     * @param attacker 攻击的生物
     * @param weapon 武器
     * @param skillData 技能
     */
    public abstract void castUnderAttack(ServerPlayerEntity player, Entity attacker, ItemStack weapon, SkillData skillData);

    /**
     * 技能结束的时候激活
     * @param player 施法者
     * @param weapon 武器
     * @param skillData 技能
     */
    public abstract void castFinish(ServerPlayerEntity player, ItemStack weapon, SkillData skillData);

    public enum CastResult {
        /** 成功 */
        SUCCESS,
        /** 失败 */
        FAIL
    }

    public enum SkillPointType {
        /** 攻击回复 */
        ATTACK,
        /** 受击回复 */
        UNDER_ATTACK,
        /** 自然回复 */
        NATURAL,
        /** 无冷却 */
        NULL
    }

    public enum DurationType {
        /** 攻击减少 */
        ATTACK,
        /** 受击减少 */
        UNDER_ATTACK,
        /** 自然减少 */
        NATURAL,
        /** 不减少 */
        NULL
    }
}
