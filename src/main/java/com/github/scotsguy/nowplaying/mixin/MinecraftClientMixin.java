package com.github.scotsguy.nowplaying.mixin;

import com.github.scotsguy.nowplaying.NowPlayingListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow @Final private SoundManager soundManager;

    @Inject(method = "<init>", at = @At("TAIL"))
    void registerSoundInstanceListener(CallbackInfo ci) {
        soundManager.registerListener(new NowPlayingListener());
    }
}
