/*
 * Copyright (c) 2022-2025 AppleTheGolden
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.scotsguy.nowplaying.gui.toast;

import com.github.scotsguy.nowplaying.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.scotsguy.nowplaying.util.Localization.localized;

public class NowPlayingToast implements Toast {
    private static final ResourceLocation BACKGROUND_SPRITE_LIGHT = ResourceLocation.withDefaultNamespace("toast/recipe");
    private static final int NOW_PLAYING_COLOR_LIGHT = 0xFF800080;
    private static final int TITLE_COLOR_LIGHT = 0xFF000000;
    private static final ResourceLocation BACKGROUND_SPRITE_DARK = ResourceLocation.withDefaultNamespace("toast/advancement");
    private static final int NOW_PLAYING_COLOR_DARK = 0xFF993299;
    private static final int TITLE_COLOR_DARK = 0xFFD1D1D1;
    
    private final Component description;
    private final ResourceLocation discSprite;
    private final long displayTime;
    private final float scale;
    private final ResourceLocation sprite;
    private final int nowPlayingColor;
    private final int titleColor;
    private Toast.Visibility wantedVisibility;
    private long startTime;

    private static final int TEXT_LEFT_MARGIN = 30;
    private static final int TEXT_RIGHT_MARGIN = 7;

    public NowPlayingToast(Component description, ResourceLocation discSprite, long displayTime, float scale, boolean darkMode) {
        this.description = description;
        this.discSprite = discSprite;
        this.displayTime = displayTime;
        this.scale = scale;
        if (darkMode) {
            sprite = BACKGROUND_SPRITE_DARK;
            nowPlayingColor = NOW_PLAYING_COLOR_DARK;
            titleColor = TITLE_COLOR_DARK;
        } else {
            sprite = BACKGROUND_SPRITE_LIGHT;
            nowPlayingColor = NOW_PLAYING_COLOR_LIGHT;
            titleColor = TITLE_COLOR_LIGHT;
        }
    }

    @Override
    public @NotNull Visibility getWantedVisibility() {
        return wantedVisibility;
    }

    @Override
    public void update(@NotNull ToastManager toastManager, long l) {
        this.wantedVisibility = startTime >= this.displayTime ? Visibility.HIDE : Visibility.SHOW;
    }

    @Override
    public void render(@NotNull GuiGraphics graphics, @NotNull Font font, long startTime) {
        this.startTime = startTime;
        if (scale != 1.0F) {
            graphics.pose().pushMatrix();
            graphics.pose().translate(160 * (1 - scale), 0.0F);
            graphics.pose().scale(scale, scale);
        }

        Minecraft mc = Minecraft.getInstance();
        int width = this.width();
        int height = this.height();
        List<FormattedCharSequence> textLines = mc.gui.getFont().split(description, width - TEXT_LEFT_MARGIN - TEXT_RIGHT_MARGIN);

        if (width == 160 && textLines.size() <= 1) {
            // Text fits, draw the whole toast from the texture
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, 0, 0, width, height);
        } else {
            // Stretch toast by drawing the sprite multiple times
            height = height + Math.max(0, textLines.size() - (Config.options().simpleToast ? 2 : 1)) * 12;
            int bottomHeight = Math.min(4, height - 28);

            // Draw the top border
            this.renderBackgroundRow(graphics, width, 0, 0, 28);

            // Draw plain background
            for (int n = 28; n < height - bottomHeight; n += 10) {
                this.renderBackgroundRow(graphics, width, 16 /* middle */, n, Math.min(16, height - n - bottomHeight));
            }

            // Draw the bottom border
            this.renderBackgroundRow(graphics, width, 32 - bottomHeight, height - bottomHeight, bottomHeight);
        }

        if (Config.options().simpleToast) {
            // Draw song title only
            for (int i = 0; i < textLines.size(); ++i) {
                graphics.drawString(mc.font, textLines.get(i), TEXT_LEFT_MARGIN, (textLines.size() == 1 ? 12 : 6 + i * 12), titleColor, false);
            }
        } else {
            // Draw "Now Playing"
            graphics.drawString(mc.font, localized("message", "nowPlaying"), TEXT_LEFT_MARGIN, 7, nowPlayingColor, false);

            // Draw song title
            for (int i = 0; i < textLines.size(); ++i) {
                graphics.drawString(mc.font, textLines.get(i), TEXT_LEFT_MARGIN, (18 + i * 12), titleColor, false);
            }
        }

        // Draw icon
        graphics.blit(RenderPipelines.GUI_TEXTURED, discSprite, 9, (height / 2) - (16 / 2), 0, 0, 16, 16, 16, 16);

        if (scale != 1.0F) graphics.pose().popMatrix();
    }

    private void renderBackgroundRow(GuiGraphics graphics, int i, int vOffset, int y, int vHeight) {
        int uWidth = vOffset == 0 ? 20 : 5;
        int n = Math.min(60, i - uWidth);

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, 160, 32, 0, vOffset, 0, y, uWidth, vHeight);

        for (int o = uWidth; o < i - n; o += 64) {
            graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, 160, 32, 32, vOffset, o, y, Math.min(64, i - o - n), vHeight);
        }

        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, sprite, 160, 32, 160 - n, vOffset, i - n, y, n, vHeight);
    }
}
