package com.nmmoc7.king_and_knight.newgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.Timer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;

import java.util.*;

/**
 * @author MaxSeth
 */
public class GuiTags extends Screen {

    private List<Tag> enableTags;
    private Map<Tag, Float> renderingTag = new HashMap<>();


    protected GuiTags(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    protected void init() {
        //TODO 我不知道Tag究竟从哪里获得
        enableTags = new ArrayList<>();
        for (Tag tag : Tags.getTags()) {
            addButton(new ButtonSwitchWithTexture(300, 200, 32, 32,
                    tag.getTexU(), tag.getTexV(), tag.getTexWidth(), tag.getTexHeight(), 32, 32,
                    new StringTextComponent("test"), button -> {
                ButtonSwitchWithTexture buttonSwitch = (ButtonSwitchWithTexture) button;
                buttonSwitch.toggleState();
                if (buttonSwitch.getState()) {
                    enableTags.add(tag);
                    renderingTag.put(tag, 0f);
                    TickTimer.timing(tag, tag.getTitle().getString().length() * 2);
                } else {
                    enableTags.remove(tag);
                    renderingTag.remove(tag);
                }
            }, tag.getTagOffTexture(), 0x00ffff));
        }
    }

    private void drawTagsString(MatrixStack stack, float partialTicks) {
        int y = 240;
        for (Map.Entry<Tag, Float> entry : renderingTag.entrySet()) {
            Tag tag = entry.getKey();
            drawString(stack, font, entry.getKey().getTitle().getComponent(string -> {
                String title = entry.getKey().getTitle().getString();
                int length = (int) (title.length() * entry.getValue());
                title = title.substring(0, length);
                return Optional.of(new StringTextComponent(title));
            }).get(),  300, y,0xffffff);
            y += 10;
            int currentTick = Math.min((TickTimer.getTiming(tag)),tag.getTitle().getString().length() * 2);
            int maxTick = tag.getTitle().getString().length() * 2;
            entry.setValue((float)currentTick / (float) maxTick);
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        minecraft.ingameGUI.getTicks();
//        renderBackground(matrixStack);
        drawTagsString(matrixStack, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
