/*
 * Copyright (c) 2022-2025 AppleTheGolden
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.github.scotsguy.nowplaying.util;

import com.github.scotsguy.nowplaying.NowPlaying;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import org.jetbrains.annotations.NotNull;

import static com.github.scotsguy.nowplaying.config.Config.options;

public class NowPlayingListener implements SoundEventListener {
    @Override
    public void onPlaySound(@NotNull SoundInstance soundInstance, 
                            @NotNull WeighedSoundEvents soundSet, float f) {
        if (soundInstance.getSource() == SoundSource.MUSIC) {
            Identifier location = soundInstance.getSound().getLocation();
            NowPlaying.lastMusic = location;

            if (!options().onlyKeybind
                    && Minecraft.getInstance().options.getSoundSourceVolume(SoundSource.MASTER) != 0f) {
                NowPlaying.displayMusic(location);
            }
        }
    }
}
