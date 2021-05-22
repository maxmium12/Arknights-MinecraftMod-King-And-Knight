package com.nmmoc7.a2kn1gh7.capability;

import com.nmmoc7.a2kn1gh7.capability.cost.CostCapability;
import com.nmmoc7.a2kn1gh7.capability.interfaces.IPlayerCapability;
import com.nmmoc7.a2kn1gh7.capability.level.LevelCapability;
import com.nmmoc7.a2kn1gh7.capability.reason.ReasonCapability;
import com.nmmoc7.a2kn1gh7.capability.weapon.WeaponCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author DustW
 */
public class ModCapabilities {
    private static final Map<String, Capability<? extends IPlayerCapability>> PLAYER_CAPABILITIES = new HashMap<>();
    private static boolean isReady = false;

    @CapabilityInject(LevelCapability.class)
    public static Capability<LevelCapability> LEVEL_CAPABILITY;
    public static final String LEVEL_KEY = "levelCap";

    @CapabilityInject(WeaponCapability.class)
    public static Capability<WeaponCapability> WEAPON_CAPABILITY;

    @CapabilityInject(CostCapability.class)
    public static Capability<CostCapability> COST_CAPABILITY;
    public static final String COST_KEY = "costCap";

    @CapabilityInject(ReasonCapability.class)
    public static Capability<ReasonCapability> REASON_CAPABILITY;
    public static final String REASON_KEY = "reasonCap";

    public static void init() {
        if (!isReady) {
            PLAYER_CAPABILITIES.put(LEVEL_KEY, LEVEL_CAPABILITY);
            PLAYER_CAPABILITIES.put(COST_KEY, COST_CAPABILITY);
            PLAYER_CAPABILITIES.put(REASON_KEY, REASON_CAPABILITY);

            isReady = true;
        }
    }

    public static Map<String, Capability<? extends IPlayerCapability>> getOrInitPlayerCapabilities() {
        if (PLAYER_CAPABILITIES.isEmpty()) {
            init();
        }

        return PLAYER_CAPABILITIES;
    }

    public static Capability<? extends IPlayerCapability> getPlayerCapability(String key) {
        return getOrInitPlayerCapabilities().get(key);
    }
}
