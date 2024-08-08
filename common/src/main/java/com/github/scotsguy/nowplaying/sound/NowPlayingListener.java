package com.github.scotsguy.nowplaying.sound;

import com.github.scotsguy.nowplaying.NowPlaying;
import com.github.scotsguy.nowplaying.config.Config;
import net.minecraft.client.Minecraft;
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

            if (!Config.get().options.onlyKeybind
                    && Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.MASTER) != 0f) {
                NowPlaying.displayMusic(name);
            }
        }
    }
}
