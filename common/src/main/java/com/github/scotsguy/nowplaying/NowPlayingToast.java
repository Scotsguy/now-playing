package com.github.scotsguy.nowplaying;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NowPlayingToast implements Toast {
    private static final ResourceLocation BACKGROUND_SPRITE = new ResourceLocation("toast/recipe");

    private final Component description;
    private final ItemStack itemStack;
    private boolean justUpdated;
    private long startTime;

    private static final int TEXT_LEFT_MARGIN = 30;
    private static final int TEXT_RIGHT_MARGIN = 7;

    public NowPlayingToast(Component description) {
        this(description, new ItemStack(Items.MUSIC_DISC_CAT));
    }

    public NowPlayingToast(Component description, ItemStack itemStack) {
        this.description = description;
        this.itemStack = itemStack;
    }

    @Override
    public Visibility render(@NotNull GuiGraphics guiGraphics, @NotNull ToastComponent toastComponent, long startTime) {
        Minecraft game = Minecraft.getInstance();

        int width = this.width();
        int height = this.height();
        Font font = game.gui.getFont();
        List<FormattedCharSequence> textLines = font.split(description, width - TEXT_LEFT_MARGIN - TEXT_RIGHT_MARGIN);

        if (width == 160 && textLines.size() <= 1) {
            // Draw the whole toast from the texture
            guiGraphics.blitSprite(BACKGROUND_SPRITE, 0, 0, width, height);
        } else {
            height = height + Math.max(0, textLines.size() - 1) * 12;
            int bottomHeight = Math.min(4, height - 28);
            // Draw the top border
            this.renderBackgroundRow(guiGraphics, width, 0, 0, 28);

            // Draw plain background
            for (int n = 28; n < height - bottomHeight; n += 10) {
                this.renderBackgroundRow(guiGraphics, width, 16 /* middle */, n, Math.min(16, height - n - bottomHeight));
            }

            // Draw the bottom border
            this.renderBackgroundRow(guiGraphics, width, 32 - bottomHeight, height - bottomHeight, bottomHeight);
        }
        // Draw "Now Playing"
        guiGraphics.drawString(game.font, Component.translatable("now_playing.toast.now_playing"), TEXT_LEFT_MARGIN, 7, -11534256, false);

        // Draw song title
        for (int i = 0; i < textLines.size(); ++i) {
            guiGraphics.drawString(game.font, textLines.get(i), TEXT_LEFT_MARGIN, (18 + i * 12), -16777216, false);
        }

        // Draw icon
        guiGraphics.renderFakeItem(itemStack, 9, (height / 2) - (16 / 2));

        return startTime - this.startTime >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    private void renderBackgroundRow(GuiGraphics guiGraphics, int i, int vOffset, int y, int vHeight) {
        int uWidth = vOffset == 0 ? 20 : 5;
        int n = Math.min(60, i - uWidth);

        guiGraphics.blitSprite(BACKGROUND_SPRITE, 160, 32, 0, vOffset, 0, y, uWidth, vHeight);

        for (int o = uWidth; o < i - n; o += 64) {
            guiGraphics.blitSprite(BACKGROUND_SPRITE, 160, 32, 32, vOffset, o, y, Math.min(64, i - o - n), vHeight);
        }

        guiGraphics.blitSprite(BACKGROUND_SPRITE, 160, 32, 160 - n, vOffset, i - n, y, n, vHeight);
    }
}


