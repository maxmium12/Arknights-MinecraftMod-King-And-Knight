package com.nmmoc7.king_and_knight.newgui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.nmmoc7.king_and_knight.KingAndKnight;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
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
    private final Map<Tag, Float> renderingTag = new LinkedHashMap<>();
    private int x = 0;
    private int y = 0;


    protected GuiTags(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    protected void init() {
        //TODO 我不知道Tag究竟从哪里获得
        enableTags = new ArrayList<>();
        x = (width - 370) / 2;
        y = (height - 290) / 2;
        Tag[] tags = Tags.getTags().toArray(new Tag[0]);
        int length = tags.length;
        int line = length / 4;
        int column = length % 4;
        for (int i = 0; i < line; i++) {
            for (int j = 0; j < 4; j++) {
                addTagButton(tags[i * 4 + j], x + 50 * 2 + 33 * j, y + 33 * i);
            }
        }

        for (int i = 0; i < column; i++) {
            addTagButton(tags[line * 4 + i], x + 50 * 2 + 33 * i, y + 33 * line);
        }
    }

    private void addTagButton(Tag tag, int x, int y) {
        addButton(new ButtonSwitchWithTexture(x, y, 32, 32,
                tag.getTexU(), tag.getTexV(), tag.getTexWidth(), tag.getTexHeight(), 32, 32,
                tag.getTitle(), button -> {
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

    private void drawTagsString(MatrixStack stack, float partialTicks) {
        int y = this.y + 74 * 2;
        int x = this.x + 52 * 2;
        int i = 0;
        int j = 0;
        int totalLevel = 0;
        for (Map.Entry<Tag, Float> entry : renderingTag.entrySet()) {
            if (i >= 6) {
                j++;
                i = 0;
            }
            Tag tag = entry.getKey();
            drawString(stack, font, entry.getKey().getTitle().getComponent(string -> {
                String title = entry.getKey().getTitle().getString();
                int length = (int) (title.length() * entry.getValue());
                title = title.substring(0, length);
                return Optional.of(new StringTextComponent(title));
            }).get(), x, y, 0xffffff);
            y += 10;
            int currentTick = Math.min((TickTimer.getTiming(tag)), tag.getTitle().getString().length() * 2);
            int maxTick = tag.getTitle().getString().length() * 2;
            entry.setValue((float) currentTick / (float) maxTick);
            totalLevel += tag.getLevel();
            i++;
        }

        stack.push();
        stack.scale(2, 2, 2);
        drawString(stack, font, String.valueOf(totalLevel), this.x / 2 + 10, this.y / 2 + 100, 0xffffff);
        stack.pop();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {

        minecraft.ingameGUI.getTicks();
        renderBackground(matrixStack);

        minecraft.textureManager.bindTexture(new ResourceLocation(KingAndKnight.MOD_ID, "textures/gui/gui_back.png"));
        blit(matrixStack, x, y, 370, 290, 0, 0, 185, 145, 256, 256);
        drawTagsString(matrixStack, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
