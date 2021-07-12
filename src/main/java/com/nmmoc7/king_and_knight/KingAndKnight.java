package com.nmmoc7.king_and_knight;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

/**
 * The value here should match an entry in the META-INF/mods.toml file
 * @author DustW
 */
@Mod("king_and_knight")
public class KingAndKnight {
    public static final String MOD_ID = "king_and_knight";
    public static final String NAME = "KingAndKnight";
    public static final String MC_VERSION = "1.16.5";
    public static final String MOD_VERSION = "1.1.0";
    public static final String VERSION = MC_VERSION + "-" + MOD_VERSION;

    public static final Logger LOGGER = LogManager.getLogger();
    public static KingAndKnight INSTANCE = null;

    public KingAndKnight() {
        INSTANCE = this;

        GeckoLib.initialize();
    }
}
