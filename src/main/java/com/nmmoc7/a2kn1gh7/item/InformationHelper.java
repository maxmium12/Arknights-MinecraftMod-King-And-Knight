package com.nmmoc7.a2kn1gh7.item;

import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;

/**
 * @author q2437
 */
public class InformationHelper {
    public static void addInformation(List<ITextComponent> tooltip, String... tooltips) {
        for (String tooltipString: tooltips) {
            tooltip.add(new StringTextComponent(tooltipString));
        }
    }
}
