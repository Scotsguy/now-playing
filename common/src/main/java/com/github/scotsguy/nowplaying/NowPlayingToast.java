package com.github.scotsguy.nowplaying;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class NowPlayingToast implements Toast {
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
    public Visibility render(@NotNull PoseStack poseStack, @NotNull ToastComponent manager, long startTime) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        int width = this.width();
        int height = this.height();
        Font font = Minecraft.getInstance().gui.getFont();
        List<FormattedCharSequence> textLines = font.split(description, width - TEXT_LEFT_MARGIN - TEXT_RIGHT_MARGIN);

        if (width == 160 && textLines.size() <= 1) {
            // Draw the whole toast from the texture
            manager.blit(poseStack, 0, 0, 0, 32, width, height);
        } else {
            height = height + Math.max(0, textLines.size() - 1) * 12;
            int m = Math.min(4, height - 28);
            // Draw the top border
            this.renderBackgroundRow(poseStack, manager, width, 0, 0, 28);

            // Draw plain background
            for (int n = 28; n < height - m; n += 10) {
                this.renderBackgroundRow(poseStack, manager, width, 16 /* middle */, n, Math.min(16, height - n - m));
            }

            // Draw the bottom border
            this.renderBackgroundRow(poseStack, manager, width, 32 - m, height - m, m);
        }
        // Draw "Now Playing"
        manager.getMinecraft().font.draw(poseStack, new TranslatableComponent("now_playing.toast.now_playing"), TEXT_LEFT_MARGIN, 7.0F, -11534256);

        // Draw song title
        for (int i = 0; i < textLines.size(); ++i) {
            manager.getMinecraft().font.draw(poseStack, textLines.get(i), TEXT_LEFT_MARGIN, (float) (18 + i * 12), -16777216);
        }

        // Draw icon
        poseStack.pushPose();
        manager.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(itemStack, 9, (height / 2) - (16 / 2));
        poseStack.popPose();

        return startTime - this.startTime >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }

    private void renderBackgroundRow(PoseStack poseStack, ToastComponent toastComponent, int i, int vOffset, int y, int vHeight) {
        int uWidth = vOffset == 0 ? 20 : 5;
        int n = Math.min(60, i - uWidth);
        toastComponent.blit(poseStack, 0, y, 0, 32 + vOffset, uWidth, vHeight);

        for (int o = uWidth; o < i - n; o += 64) {
            toastComponent.blit(poseStack, o, y, 32, 32 + vOffset, Math.min(64, i - o - n), vHeight);
        }

        toastComponent.blit(poseStack, i - n, y, 160 - n, 32 + vOffset, n, vHeight);
    }

}


