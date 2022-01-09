package com.github.scotsguy.nowplaying;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class NowPlayingToast implements Toast {
    private final Component description;
    private final ItemStack itemStack;
    private boolean justUpdated;
    private long startTime;

    public NowPlayingToast(Component description) {
        this(description, new ItemStack(Items.MUSIC_DISC_CAT));
    }
    public NowPlayingToast(Component description, ItemStack itemStack) {
        this.description = description;
        this.itemStack = itemStack;
    }

    @Override
    public Visibility render(PoseStack matrices, ToastComponent manager, long startTime) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, TEXTURE);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

        manager.blit(matrices, 0, 0, 0, 32, this.width(), this.height());
        manager.getMinecraft().font.draw(matrices, new TranslatableComponent("now_playing.toast.now_playing"), 30.0F, 7.0F, -11534256);
        manager.getMinecraft().font.draw(matrices, this.description, 30.0F, 18.0F, -16777216);
        matrices.pushPose();
        manager.getMinecraft().getItemRenderer().renderAndDecorateFakeItem(itemStack, 9, 8);
        matrices.popPose();

        return startTime - this.startTime >= 5000L ? Toast.Visibility.HIDE : Toast.Visibility.SHOW;
    }
}

