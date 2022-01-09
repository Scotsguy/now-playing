package com.github.scotsguy.nowplaying.mixin;

// Lnet/minecraft/client/toast/Toast$Visibility;playSound(Lnet/minecraft/client/sound/SoundManager;)V

import com.github.scotsguy.nowplaying.NowPlayingConfig;
import com.github.scotsguy.nowplaying.NowPlayingToast;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.components.toasts.ToastComponent$ToastInstance")
public class ToastInstanceMixin<T extends Toast> {
    @Final
    @Shadow
    private T toast;

    @Redirect(method = "render", require = 2, at = @At(value = "INVOKE",target = "Lnet/minecraft/client/gui/components/toasts/Toast$Visibility;playSound(Lnet/minecraft/client/sounds/SoundManager;)V"))
    void silenceWooshSound(Toast.Visibility visibility, SoundManager soundManager) {
        NowPlayingConfig config = AutoConfig.getConfigHolder(NowPlayingConfig.class).getConfig();
        if (toast instanceof NowPlayingToast && config.silenceWoosh) {
            return;
        }

        SoundEvent sound = ((ToastVisibilityAccessor)(Object)visibility).getSoundEvent();
        soundManager.play(SimpleSoundInstance.forUI(sound, 1.0f, 1.0f));
    }
}
