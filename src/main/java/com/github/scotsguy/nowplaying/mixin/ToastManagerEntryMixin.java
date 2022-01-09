package com.github.scotsguy.nowplaying.mixin;

// Lnet/minecraft/client/toast/Toast$Visibility;playSound(Lnet/minecraft/client/sound/SoundManager;)V

import com.github.scotsguy.nowplaying.NowPlayingConfig;
import com.github.scotsguy.nowplaying.NowPlayingToast;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.toast.Toast;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.toast.ToastManager$Entry")
public class ToastManagerEntryMixin<T extends Toast> {
    @Final
    @Shadow
    private T instance;

    @Redirect(method = "draw", require = 2, at = @At(value = "INVOKE",target = "Lnet/minecraft/client/toast/Toast$Visibility;playSound(Lnet/minecraft/client/sound/SoundManager;)V"))
    void silenceWooshSound(Toast.Visibility visibility, SoundManager soundManager) {
        NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();
        if (instance instanceof NowPlayingToast && config.silenceWoosh) {
            return;
        }

        SoundEvent sound = ((ToastVisibilityAccessor)(Object)visibility).getSound();
        soundManager.play(PositionedSoundInstance.master(sound, 1.0f, 1.0f));
    }
}
