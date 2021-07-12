package com.nmmoc7.king_and_knight.capability;

import com.google.common.collect.BiMap;
import com.nmmoc7.king_and_knight.capability.player.PlayerCapabilitiesProvider;
import com.nmmoc7.king_and_knight.capability.player.cost.CostCapability;
import com.nmmoc7.king_and_knight.capability.player.interfaces.IPlayerCapability;
import com.nmmoc7.king_and_knight.capability.player.level.LevelCapability;
import com.nmmoc7.king_and_knight.capability.player.reason.ReasonCapability;
import com.nmmoc7.king_and_knight.capability.weapon.WeaponCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

/**
 * @author DustW
 */
public class ModCapabilities {
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

    public static void playerCapabilitiesInit() {
        if (!isReady) {
            PlayerCapabilitiesProvider.register(LEVEL_KEY, LevelCapability::new, LEVEL_CAPABILITY);
            PlayerCapabilitiesProvider.register(COST_KEY, CostCapability::new, COST_CAPABILITY);
            PlayerCapabilitiesProvider.register(REASON_KEY, ReasonCapability::new, REASON_CAPABILITY);

            isReady = true;
        }
    }

    public static BiMap<String, Capability<? extends IPlayerCapability>> getPlayerCapabilities() {
        return PlayerCapabilitiesProvider.getPlayerCapabilities();
    }

    public static Capability<? extends IPlayerCapability> getPlayerCapability(String key) {
        return getPlayerCapabilities().get(key);
    }

    public static String getPlayerCapabilityKey(Capability<? extends IPlayerCapability> capability) {
        return getPlayerCapabilities().inverse().get(capability);
    }

    public static boolean hasCapability(Capability<? extends IPlayerCapability> capability) {
        return getPlayerCapabilities().inverse().containsKey(capability);
    }
}
