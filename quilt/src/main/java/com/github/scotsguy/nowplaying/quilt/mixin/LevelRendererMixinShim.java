package com.github.scotsguy.nowplaying.quilt.mixin;

import com.github.scotsguy.nowplaying.LevelRendererMixinImpl;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class LevelRendererMixinShim {
    @Redirect(method = "playStreamingMusic", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;setNowPlaying(Lnet/minecraft/network/chat/Component;)V"))
    private void modifyRecordPlayingOverlay(Gui gui, Component text) {
        LevelRendererMixinImpl.modifyRecordPlayingOverlay(gui, text);
    }
}
