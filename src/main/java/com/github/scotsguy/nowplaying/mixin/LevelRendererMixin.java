package com.github.scotsguy.nowplaying.mixin;

import com.github.scotsguy.nowplaying.config.Config;
import com.github.scotsguy.nowplaying.config.NowPlayingConfig;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.Component;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Redirect(method = "playStreamingMusic(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/RecordItem;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;setNowPlaying(Lnet/minecraft/network/chat/Component;)V"))
    private void modifyRecordPlayingOverlay(Gui ingameGUI, Component text) {
        if (NowPlayingConfig.jukeboxStyle.get() == Config.Style.Hotbar) {
            ingameGUI.setNowPlaying(text);
        }
    }
}
