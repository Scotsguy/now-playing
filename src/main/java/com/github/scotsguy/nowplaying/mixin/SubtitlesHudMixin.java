package com.github.scotsguy.nowplaying.mixin;

import com.github.scotsguy.nowplaying.NowPlayingToast;
import com.github.scotsguy.nowplaying.Util;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.SubtitlesHud;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// registering a SoundInstanceListener NPEs because...? I don't know
@Mixin(SubtitlesHud.class)
@Environment(EnvType.CLIENT)
public class SubtitlesHudMixin {
    @Inject(method = "onSoundPlayed", at = @At("HEAD"))
    private void poorMansSoundInstanceListener(SoundInstance sound, WeightedSoundSet soundSet, CallbackInfo ci) {
        if (sound.getCategory() == SoundCategory.MUSIC) {
            Text name = Util.getSoundName(soundSet);
            if (name == null) return;

            MinecraftClient.getInstance().getToastManager().add(new NowPlayingToast(name));
        }
    }
}
