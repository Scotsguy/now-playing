package com.github.scotsguy.nowplaying.forge.mixin;

import com.github.scotsguy.nowplaying.LevelRendererMixinImpl;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LevelRenderer.class)
public class LevelRendererMixinShim {
    @Redirect(method = "Lnet/minecraft/client/renderer/LevelRenderer;playStreamingMusic(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/RecordItem;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;setNowPlaying(Lnet/minecraft/network/chat/Component;)V"))
    private void modifyRecordPlayingOverlay(Gui gui, Component text) {
        LevelRendererMixinImpl.modifyRecordPlayingOverlay(gui, text);
    }
}
