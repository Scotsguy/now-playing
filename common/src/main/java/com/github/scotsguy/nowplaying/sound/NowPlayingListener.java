package com.github.scotsguy.nowplaying.sound;

import com.github.scotsguy.nowplaying.NowPlaying;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

public class NowPlayingListener implements SoundEventListener {
    @Override
    public void onPlaySound(@NotNull SoundInstance sound, @NotNull WeighedSoundEvents soundSet, float f) {
        if (sound.getSource() == SoundSource.MUSIC) {
            Component name = Sound.getSoundName(sound);
            NowPlaying.lastMusic = name;

            NowPlaying.display(name, Component.translatable("record.nowPlaying", name));
        }
    }
}
