package com.nmmoc7.king_and_knight.newgui;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.system.CallbackI;

import java.util.Map;

/**
 * @author MaxSeth
 */
public class TickTimer {

    private static final Map<Object, Pair<Integer, Integer>> TIMING_OBJECTS = new Object2ObjectOpenHashMap<>();

    public static void timing(Object object, int timingTicks) {
        TIMING_OBJECTS.put(object, MutablePair.of(timingTicks, 0));
    }

    public static void tick() {
        for (Map.Entry<Object, Pair<Integer, Integer>> entry : TIMING_OBJECTS.entrySet()) {
            MutablePair<Integer, Integer> currentPair = (MutablePair<Integer, Integer>) entry.getValue();
            if (currentPair.getRight() < currentPair.getLeft()) {
                currentPair.right++;
                entry.setValue(currentPair);
            }
        }
    }

    public static int getTiming(Object object) {
        return TIMING_OBJECTS.get(object).getRight();
    }
}
