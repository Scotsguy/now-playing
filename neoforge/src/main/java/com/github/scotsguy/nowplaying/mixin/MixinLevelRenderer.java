package com.github.scotsguy.nowplaying.mixin;

import com.github.scotsguy.nowplaying.config.Config;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LevelRenderer.class)
public class MixinLevelRenderer {
    @WrapOperation(
            method = "playStreamingMusic(Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/item/RecordItem;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/gui/Gui;setNowPlaying(Lnet/minecraft/network/chat/Component;)V"
            )
    )
    private void modfyRecordPlayingOverlay(Gui instance, Component message, Operation<Void> original) {
        if (Config.get().options.jukeboxStyle == Config.Options.Style.Hotbar) {
            original.call(instance, message);
        }
    }
}

