package com.github.scotsguy.nowplaying.gui.toast;

import com.github.scotsguy.nowplaying.config.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.github.scotsguy.nowplaying.util.Localization.localized;

public class NowPlayingToast implements Toast {
    private static final ResourceLocation BACKGROUND_SPRITE = ResourceLocation.withDefaultNamespace("toast/recipe");

    private final Component description;
    private final ItemStack itemStack;
    private final long displayTime;
    private final float scale;

    private static final int TEXT_LEFT_MARGIN = 30;
    private static final int TEXT_RIGHT_MARGIN = 7;

    public NowPlayingToast(Component description, ItemStack itemStack, long displayTime, float scale) {
        this.description = description;
        this.itemStack = itemStack;
        this.displayTime = displayTime;
        this.scale = scale;
    }

    @Override
    public @NotNull Visibility render(@NotNull GuiGraphics graphics, @NotNull ToastComponent toast, long startTime) {
        if (scale != 1.0F) {
            graphics.pose().pushPose();
            graphics.pose().translate(160 * (1 - scale), 0.0F, 0.0F);
            graphics.pose().scale(scale, scale, 1.0F);
        }

        Minecraft mc = Minecraft.getInstance();
        int width = this.width();
        int height = this.height();
        List<FormattedCharSequence> textLines = mc.gui.getFont().split(description, width - TEXT_LEFT_MARGIN - TEXT_RIGHT_MARGIN);

        if (width == 160 && textLines.size() <= 1) {
            // Text fits, draw the whole toast from the texture
            graphics.blitSprite(BACKGROUND_SPRITE, 0, 0, width, height);
        } else {
            // Stretch toast by drawing the sprite multiple times
            height = height + Math.max(0, textLines.size() - (Config.get().options.simpleToast ? 2 : 1)) * 12;
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

        if (Config.get().options.simpleToast) {
            // Draw song title only
            for (int i = 0; i < textLines.size(); ++i) {
                graphics.drawString(mc.font, textLines.get(i), TEXT_LEFT_MARGIN, (textLines.size() == 1 ? 12 : 6 + i * 12), -16777216, false);
            }
        } else {
            // Draw "Now Playing"
            graphics.drawString(mc.font, localized("message", "now_playing"), TEXT_LEFT_MARGIN, 7, -11534256, false);

            // Draw song title
            for (int i = 0; i < textLines.size(); ++i) {
                graphics.drawString(mc.font, textLines.get(i), TEXT_LEFT_MARGIN, (18 + i * 12), -16777216, false);
            }
        }

        // Draw icon
        graphics.renderFakeItem(itemStack, 9, (height / 2) - (16 / 2));

        if (scale != 1.0F) graphics.pose().popPose();
        return startTime >= this.displayTime ? Visibility.HIDE : Visibility.SHOW;
    }

    private void renderBackgroundRow(GuiGraphics graphics, int i, int vOffset, int y, int vHeight) {
        int uWidth = vOffset == 0 ? 20 : 5;
        int n = Math.min(60, i - uWidth);

        graphics.blitSprite(BACKGROUND_SPRITE, 160, 32, 0, vOffset, 0, y, uWidth, vHeight);

        for (int o = uWidth; o < i - n; o += 64) {
            graphics.blitSprite(BACKGROUND_SPRITE, 160, 32, 32, vOffset, o, y, Math.min(64, i - o - n), vHeight);
        }

        graphics.blitSprite(BACKGROUND_SPRITE, 160, 32, 160 - n, vOffset, i - n, y, n, vHeight);
    }
}


