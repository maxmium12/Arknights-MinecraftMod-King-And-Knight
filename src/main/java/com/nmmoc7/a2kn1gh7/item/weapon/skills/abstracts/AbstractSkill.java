package com.nmmoc7.a2kn1gh7.item.weapon.skills;

import com.nmmoc7.a2kn1gh7.A2kn1gh7;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author DustW
 */
public abstract class AbstractSkill {
    /** 如果是false，切换技能后从零开始计数 */
    protected boolean hasWarmupTime;
    protected int warmupTime;

    protected DurationType durationType;
    protected int maxDurationTime;

    protected CoolDownType coolDownType;
    protected int maxCoolDownTime;
    /** 如果是true，在冷却时间结束后自动开启 */
    protected boolean isAutoCast;

    /** 如果不到cost不能切换技能 */
    protected int cost;

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
                A2kn1gh7.LOGGER.warn("注册名不能为空");

                return null;
            }
        }
        else {
            A2kn1gh7.LOGGER.warn("已经注册过了");

            return getSkill(skill.registryName);
        }
    }

    public static AbstractSkill getSkill(String registryName) {
        if (SKILLS.containsKey(registryName)) {
            return SKILLS.get(registryName);
        }
        else {
            A2kn1gh7.LOGGER.warn("没有这个技能！");
            return null;
        }
    }

    public int getCost() {
        return cost;
    }

    public String getDefaultTranslationKey() {
        if (this.translationKey == null) {
            this.translationKey = Util.makeTranslationKey("skill", new ResourceLocation(A2kn1gh7.MODID, this.registryName));
        }

        return this.translationKey;
    }

    public String getDefaultDocTranslationKey() {
        if (this.docTranslationKey == null) {
            this.docTranslationKey = Util.makeTranslationKey("skill.doc", new ResourceLocation(A2kn1gh7.MODID, this.registryName));
        }

        return this.docTranslationKey;
    }

    public CoolDownType getCoolDownType() {
        return coolDownType;
    }

    public DurationType getDurationType() {
        return durationType;
    }

    public void setRegistryName(String registryName) {
        this.registryName = registryName;
    }

    public String getRegistryName() {
        return registryName;
    }

    /**
     * 施法
     * @param skillData 施法的技能
     * @param weapon 施法的武器
     * @param caster 施法者
     */
    public abstract void cast(SkillData skillData, ItemStack weapon, PlayerEntity caster);

    public enum CoolDownType {
        /** 攻击回复 */
        ATTACK,
        /** 受击回复 */
        BE_HIT,
        /** 自然回复 */
        NATURAL,
        /** 无冷却 */
        NULL
    }

    public enum DurationType {
        /** 攻击减少 */
        ATTACK,
        /** 受击减少 */
        BE_HIT,
        /** 自然减少 */
        NATURAL,
        /** 不减少 */
        NULL
    }
}
