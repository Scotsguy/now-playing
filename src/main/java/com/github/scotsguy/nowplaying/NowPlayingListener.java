package com.github.scotsguy.nowplaying;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundInstanceListener;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class NowPlayingListener implements SoundInstanceListener {
    @Override
    public void onSoundPlayed(SoundInstance sound, WeightedSoundSet soundSet) {
        SystemToast.add(MinecraftClient.getInstance().getToastManager(), SystemToast.Type.TUTORIAL_HINT, new TranslatableText("toast.now_playing"), soundSet.getSubtitle());
    }
}
